package io.github.humbinal.i18n.message.plugin.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author humbianl
 */
public class GeneratorParams {

    /**
     * 解析某种规则结尾的java文件
     */
    private String sourceFileEndsWith;

    /**
     * 解析需要支持的规则
     * 仅支持enum和interface中的一种或两种
     * 默认同时支持两种
     */
    private List<String> sourceTypes = new ArrayList<String>() {{
        add("enum");
        add("interface");
    }};

    /**
     * 语言定义字面量
     */
    private String languageLiteral = "language";

    /**
     * message消息定义字面量
     */
    private String messageLiteral = "message";

    /**
     * 问题处理方案定义字面量
     */
    private String solutionLiteral = "solution";

    /**
     * 扫描文档注释中使用的语言
     */
    private String language;

    /**
     * 生成message文件的默认语言
     * 默认语言message文件为： message.properties
     * 其他语言的message文件为：message_XXX.properties, XXX为对应语言
     * 即当language和defaultLanguage配置一致时，输出两个不同名的文件，但内容一致
     * language和defaultLanguage配置不一致时，仅输出一个文件
     * 当配置的language与源代码中注释的language不一致时将生成失败
     */
    private String defaultLanguage = "zh_CN";

    /**
     * 输出的i18n文件名前缀
     */
    private String outputFileNamePrefix;

    /**
     * 输出的i18n文件格式
     */
    private String outputFileFormat = "properties";

    /**
     * message消息对应解决方式的label后缀，默认: '.solution'
     */
    private String solutionLabelSuffix = ".solution";

    public String getSourceFileEndsWith() {
        return sourceFileEndsWith;
    }

    public void setSourceFileEndsWith(String sourceFileEndsWith) {
        this.sourceFileEndsWith = sourceFileEndsWith;
    }

    public List<String> getSourceTypes() {
        return sourceTypes;
    }

    public void setSourceTypes(List<String> sourceTypes) {
        this.sourceTypes = sourceTypes;
    }

    public String getLanguageLiteral() {
        return languageLiteral;
    }

    public void setLanguageLiteral(String languageLiteral) {
        this.languageLiteral = languageLiteral;
    }

    public String getMessageLiteral() {
        return messageLiteral;
    }

    public void setMessageLiteral(String messageLiteral) {
        this.messageLiteral = messageLiteral;
    }

    public String getSolutionLiteral() {
        return solutionLiteral;
    }

    public void setSolutionLiteral(String solutionLiteral) {
        this.solutionLiteral = solutionLiteral;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getOutputFileNamePrefix() {
        return outputFileNamePrefix;
    }

    public void setOutputFileNamePrefix(String outputFileNamePrefix) {
        this.outputFileNamePrefix = outputFileNamePrefix;
    }

    public String getOutputFileFormat() {
        return outputFileFormat;
    }

    public void setOutputFileFormat(String outputFileFormat) {
        this.outputFileFormat = outputFileFormat;
    }

    public String getSolutionLabelSuffix() {
        return solutionLabelSuffix;
    }

    public void setSolutionLabelSuffix(String solutionLabelSuffix) {
        this.solutionLabelSuffix = solutionLabelSuffix;
    }

    @Override
    public String toString() {
        return "GeneratorParams{" +
                "sourceFileEndsWith='" + sourceFileEndsWith + '\'' +
                ", sourceTypes=" + sourceTypes +
                ", languageLiteral='" + languageLiteral + '\'' +
                ", messageLiteral='" + messageLiteral + '\'' +
                ", solutionLiteral='" + solutionLiteral + '\'' +
                ", language='" + language + '\'' +
                ", defaultLanguage='" + defaultLanguage + '\'' +
                ", outputFileNamePrefix='" + outputFileNamePrefix + '\'' +
                ", outputFileFormat='" + outputFileFormat + '\'' +
                ", solutionLabelSuffix='" + solutionLabelSuffix + '\'' +
                '}';
    }
}
