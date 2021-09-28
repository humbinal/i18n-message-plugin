package io.github.humbinal.i18n.message.plugin.maven;

import io.github.humbinal.i18n.message.plugin.core.Generator;
import io.github.humbinal.i18n.message.plugin.core.GeneratorParams;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Arrays;

/**
 * @author humbianl
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE, threadSafe = true)
public class GeneratorMojo extends AbstractMojo {

    /**
     * 需要生成message文件的java源代码目录
     */
    @Parameter(property = "sourceDirectory", defaultValue = "${project.build.sourceDirectory}", required = true)
    private File sourceDirectory;

    /**
     * 生成的message文件输出目录
     */
    @Parameter(property = "outputDirectory", defaultValue = "${project.build.outputDirectory}", required = true)
    private File outputDirectory;

    /**
     * 输出message文件的文件名前缀
     */
    @Parameter(property = "outputDirectory")
    private String outputFileNamePrefix;

    /**
     * 配置仅扫描指定后缀结尾的文件, 如: Error.java, 默认不配置
     */
    @Parameter(property = "sourceFileEndsWith")
    private String sourceFileEndsWith;

    /**
     * 需要扫描的文件的类型, 默认为枚举(enum)和接口(interface), 当只有一种时, 可以仅配置一种, 加快扫描速度.
     */
    @Parameter(property = "sourceTypes")
    private String[] sourceTypes;

    /**
     * 生成配置文件的语言，当配置的language与源代码中注释的language不一致时将生成失败
     */
    @Parameter(property = "language", defaultValue = "zh_CN")
    private String language;

    /**
     * 生成message文件的默认语言
     * 默认语言message文件为： message.properties
     * 其他语言的message文件为：message_XXX.properties, XXX为对应语言
     * 即当language和defaultLanguage配置一致时，输出两个不同名的文件，但内容一致
     * language和defaultLanguage配置不一致时，仅输出一个文件
     */
    @Parameter(property = "defaultLanguage", defaultValue = "zh_CN")
    private String defaultLanguage;

    /**
     * message消息对应解决方式的label后缀，默认: '.solution'
     */
    @Parameter(property = "solutionLabelSuffix", defaultValue = ".solution")
    private String solutionLabelSuffix;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("i18n-message-maven-plugin working....");
        getLog().info("sourceDirectory:" + sourceDirectory);
        getLog().info("outputDirectory:" + outputDirectory);
        GeneratorParams generatorParams = new GeneratorParams();
        if (sourceFileEndsWith != null) {
            generatorParams.setSourceFileEndsWith(sourceFileEndsWith);
        }
        if (sourceTypes.length != 0 && sourceTypes.length <= 2) {
            generatorParams.setSourceTypes(Arrays.asList(sourceTypes));
        } else {
            getLog().error("i18n-message-maven-plugin: sourceTypes illegal, only can be enum/interface.");
            throw new MojoExecutionException("i18n-message-maven-plugin: sourceTypes illegal, only can be enum/interface.");
        }
        generatorParams.setOutputFileNamePrefix(outputFileNamePrefix);
        generatorParams.setLanguage(language);
        generatorParams.setDefaultLanguage(defaultLanguage);
        generatorParams.setSolutionLabelSuffix(solutionLabelSuffix);
        getLog().info("i18n-message-maven-plugin generatorParams: " + generatorParams);
        Generator generator = new Generator(generatorParams);
        try {
            generator.generate(sourceDirectory, outputDirectory);
        } catch (Exception e) {
            getLog().error("i18n-message-maven-plugin generate failed, ", e);
            throw new MojoExecutionException("i18n-message-maven-plugin generate failed, ", e);
        }
    }
}
