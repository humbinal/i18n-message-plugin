package io.github.humbinal.i18n.message.plugin.core;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.javadoc.JavadocBlockTag;
import com.github.javaparser.javadoc.description.JavadocDescriptionElement;
import com.github.javaparser.symbolsolver.javaparser.Navigator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author humbianl
 */
public class JavaDocParser {

    /**
     * 解析.java源代码文件中的文档注释, 获取符合规范的信息作为message信息
     *
     * @param javaSourceFile  .java源代码文件
     * @param generatorParams 源代码解析参数
     * @return MessageEntityList
     * @throws Exception exception
     */
    public static List<MessageEntity> parse(File javaSourceFile, GeneratorParams generatorParams) throws Exception {
        List<MessageEntity> messageEntities = new ArrayList<>();
        if (javaSourceFile.isFile() && endsWithOrNot(javaSourceFile, generatorParams)) {
            //将一个.java文件的文本解析为CompilationUnit树
            CompilationUnit compilationUnit = StaticJavaParser.parse(javaSourceFile);
            //一个j.java文件中有多个类或接口时, types的长度大于1
            NodeList<TypeDeclaration<?>> types = compilationUnit.getTypes();
            //一个type就是一个java类, 遍历解析
            for (TypeDeclaration<?> type : types) {
                //根据配置参数判断需要解析的java源代码类型
                if (type instanceof EnumDeclaration && generatorParams.getSourceTypes().contains("enum")) {
                    List<MessageEntity> parsedEntities = resolveEnum((EnumDeclaration) type, generatorParams);
                    messageEntities.addAll(parsedEntities);
                }
                if (type instanceof ClassOrInterfaceDeclaration &&
                        ((ClassOrInterfaceDeclaration) type).isInterface() &&
                        generatorParams.getSourceTypes().contains("interface")) {
                    List<MessageEntity> parsedEntities = resolveInterface((ClassOrInterfaceDeclaration) type, generatorParams);
                    messageEntities.addAll(parsedEntities);
                }
            }
        }
        return messageEntities;
    }

    private static boolean endsWithOrNot(File javaSourceFile, GeneratorParams generatorParams) {
        if (generatorParams.getSourceFileEndsWith() == null || generatorParams.getSourceFileEndsWith().trim().length() == 0) {
            return true;
        }
        return javaSourceFile.getName().endsWith(generatorParams.getSourceFileEndsWith().trim());
    }

    private static List<MessageEntity> resolveEnum(EnumDeclaration enumDeclaration, GeneratorParams generatorParams) throws Exception {
        List<MessageEntity> result = new ArrayList<>();
        for (Node child : enumDeclaration.getChildNodes()) {
            //仅获取枚举类型的源码并解析
            if (child instanceof EnumConstantDeclaration) {
                Optional<JavadocComment> javadocCommentOptional = ((EnumConstantDeclaration) child).getJavadocComment();
                if (javadocCommentOptional.isPresent()) {
                    JavadocComment javadocComment = javadocCommentOptional.get();
                    MessageEntity messageEntity = parseJavadocComment(javadocComment, generatorParams);
                    String label;
                    List<Node> childNodes = child.getChildNodes();
                    if (childNodes.size() == 3) {
                        Node node = childNodes.get(1);
                        if (node instanceof StringLiteralExpr) {
                            label = ((StringLiteralExpr) node).getValue();
                        } else {
                            throw new Exception("enumeration error label parsing exception!");
                        }
                    } else {
                        throw new Exception("enumeration error label definition is invalid!");
                    }
                    messageEntity.setLabel(label);
                    result.add(messageEntity);
                }
            }
        }
        return result;
    }

    private static List<MessageEntity> resolveInterface(ClassOrInterfaceDeclaration interfaceDeclaration, GeneratorParams generatorParams) throws Exception {
        List<MessageEntity> result = new ArrayList<>();
        for (Node child : interfaceDeclaration.getChildNodes()) {
            //仅获取接口类型的源码并解析
            if (child instanceof FieldDeclaration) {
                Optional<JavadocComment> javadocCommentOptional = ((FieldDeclaration) child).getJavadocComment();
                if (javadocCommentOptional.isPresent()) {
                    JavadocComment javadocComment = javadocCommentOptional.get();
                    MessageEntity messageEntity = parseJavadocComment(javadocComment, generatorParams);
                    //支持label为 PREFIX + SUFFIX 形式,需要对字段取值, 目前只支持两个字符串相加
                    NodeList<VariableDeclarator> variables = ((FieldDeclaration) child).getVariables();
                    if (variables.size() != 1) {
                        throw new Exception("the field property is abnormal and cannot be parsed!");
                    }
                    VariableDeclarator variable = variables.get(0);
                    List<Node> childNodes = variable.getChildNodes();
                    String label;
                    List<Node> binaryExprCollect = childNodes.stream().filter(node -> node instanceof BinaryExpr).collect(Collectors.toList());
                    if (binaryExprCollect.size() == 1) {
                        Node node = binaryExprCollect.get(0);
                        List<NameExpr> exprList = node.findAll(NameExpr.class);
                        String left = "";
                        if (exprList.size() == 1) {
                            //获取当前变量定义中的变量部分 如: USER_NOT_EXIT = PREFIX + "001", 则需要去取 PREFIX 的值, 当前取值仅支持字符串
                            VariableDeclarator variableDeclarator = Navigator.demandField(interfaceDeclaration, exprList.get(0).getNameAsString());
                            List<Node> childNodes1 = variableDeclarator.getChildNodes();
                            //left 即为 PREFIX 的值
                            left = childNodes1.stream()
                                    .filter(childNode -> childNode instanceof StringLiteralExpr).collect(Collectors.toList())
                                    .get(0).findAll(StringLiteralExpr.class).get(0).getValue();
                        }
                        String right = node.findAll(StringLiteralExpr.class).get(0).getValue();
                        label = left + right;
                    } else {
                        label = childNodes.stream().filter(node -> node instanceof StringLiteralExpr)
                                .collect(Collectors.toList()).get(0).findAll(StringLiteralExpr.class).get(0).getValue();
                    }
                    messageEntity.setLabel(label);
                    result.add(messageEntity);
                }
            }
        }
        return result;
    }

    private static MessageEntity parseJavadocComment(JavadocComment javadocComment, GeneratorParams generatorParams) throws Exception {
        List<JavadocBlockTag> blockTags = javadocComment.parse().getBlockTags();
        MessageEntity messageEntity = new MessageEntity();
        for (JavadocBlockTag blockTag : blockTags) {
            //tagValue如果有换行将其合并为一行
            String tagValue = blockTag.getContent().getElements().stream()
                    .map(JavadocDescriptionElement::toText).collect(Collectors.joining());
            if (blockTag.getTagName().equals(generatorParams.getLanguageLiteral())) {
                //判断tagValue是否为用户配置的语言，不一致则报错
                if (tagValue.trim().equalsIgnoreCase(generatorParams.getLanguage())) {
                    messageEntity.setLanguage(tagValue);
                } else {
                    throw new Exception(String.format("parsed comment language value '%s' not equals configured language '%s'", tagValue, generatorParams.getLanguage()));
                }
            }
            if (blockTag.getTagName().equals(generatorParams.getMessageLiteral())) {
                messageEntity.setMessage(tagValue);
            }
            if (blockTag.getTagName().equals(generatorParams.getSolutionLiteral())) {
                messageEntity.setSolution(tagValue);
            }
        }
        return messageEntity;
    }
}
