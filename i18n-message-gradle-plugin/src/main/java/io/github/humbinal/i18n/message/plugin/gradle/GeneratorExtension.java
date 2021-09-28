package io.github.humbinal.i18n.message.plugin.gradle;

import org.gradle.api.Project;

import java.util.Arrays;

public class GeneratorExtension {

    private final Project project;

    /**
     * 需要生成message文件的java源代码目录
     */
    private String sourceDirectory;

    /**
     * 生成的message文件输出目录
     */
    private String outputDirectory;

    /**
     * 输出message文件的文件名前缀, 默认值: 'message'
     */
    private String outputFileNamePrefix = "message";

    /**
     * 配置仅扫描指定后缀的文件, 如: Error.java, 默认不配置
     */
    private String sourceFileEndsWith;

    /**
     * 需要扫描的文件的类型, 默认为枚举(enum)和接口(interface), 当只有一种时, 可以仅配置一种, 加快扫描速度.
     */
    private String[] sourceTypes;

    /**
     * 生成配置文件的语言，当配置的language与源代码中注释的language不一致时将生成失败
     */
    private String language = "zh_CN";

    /**
     * 生成message文件的默认语言
     * 默认语言message文件为： message.properties
     * 其他语言的message文件为：message_XXX.properties, XXX为对应语言
     * 即当language和defaultLanguage配置一致时，输出两个不同名的文件，但内容一致
     * language和defaultLanguage配置不一致时，仅输出一个文件
     */
    private String defaultLanguage = "zh_CN";

    /**
     * message提示对应的解决方式的label后缀，默认: '.solution'
     */
    private String solutionLabelSuffix = ".solution";

    public GeneratorExtension(Project project) {
        this.project = project;
    }

    public String getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getOutputFileNamePrefix() {
        return outputFileNamePrefix;
    }

    public void setOutputFileNamePrefix(String outputFileNamePrefix) {
        this.outputFileNamePrefix = outputFileNamePrefix;
    }

    public String getSourceFileEndsWith() {
        return sourceFileEndsWith;
    }

    public void setSourceFileEndsWith(String sourceFileEndsWith) {
        this.sourceFileEndsWith = sourceFileEndsWith;
    }

    public String[] getSourceTypes() {
        return sourceTypes;
    }

    public void setSourceTypes(String[] sourceTypes) {
        this.sourceTypes = sourceTypes;
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

    public String getSolutionLabelSuffix() {
        return solutionLabelSuffix;
    }

    public void setSolutionLabelSuffix(String solutionLabelSuffix) {
        this.solutionLabelSuffix = solutionLabelSuffix;
    }

    public Project getProject() {
        return project;
    }

    @Override
    public String toString() {
        return "[" +
                "sourceDirectory='" + sourceDirectory + '\'' +
                ", outputDirectory='" + outputDirectory + '\'' +
                ", outputFileNamePrefix='" + outputFileNamePrefix + '\'' +
                ", sourceFileEndsWith='" + sourceFileEndsWith + '\'' +
                ", sourceTypes=" + Arrays.toString(sourceTypes) +
                ", language='" + language + '\'' +
                ", defaultLanguage='" + defaultLanguage + '\'' +
                ", solutionLabelSuffix='" + solutionLabelSuffix + '\'' +
                ']';
    }
}
