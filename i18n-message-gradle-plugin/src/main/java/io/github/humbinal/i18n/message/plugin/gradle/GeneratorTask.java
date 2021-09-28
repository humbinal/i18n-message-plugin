package io.github.humbinal.i18n.message.plugin.gradle;

import io.github.humbinal.i18n.message.plugin.core.Generator;
import io.github.humbinal.i18n.message.plugin.core.GeneratorParams;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;

public abstract class GeneratorTask extends DefaultTask {

    private static final Logger log = LoggerFactory.getLogger(GeneratorTask.class);

    private final GeneratorExtension generator;

    @Inject
    public GeneratorTask(GeneratorExtension generator) {
        this.generator = generator;
    }

    @TaskAction
    public void generate() {
        log.warn("i18n-message-plugin config: " + generator);
        Project project = generator.getProject();
        generator.setSourceDirectory(project.getProjectDir().getPath() + "/" + generator.getSourceDirectory());
        generator.setOutputDirectory(project.getProjectDir().getPath() + "/" + generator.getOutputDirectory());
        log.warn("i18n-message-plugin sourceDirectory: " + generator.getSourceDirectory());
        log.warn("i18n-message-plugin outputDirectory: " + generator.getOutputDirectory());
        GeneratorParams generatorParams = new GeneratorParams();
        if (generator.getSourceFileEndsWith() != null) {
            generatorParams.setSourceFileEndsWith(generator.getSourceFileEndsWith());
        }
        if (generator.getSourceTypes().length != 0 && generator.getSourceTypes().length <= 2) {
            generatorParams.setSourceTypes(Arrays.asList(generator.getSourceTypes()));
        } else {
            log.error("i18n-message-plugin: sourceTypes illegal, only can be enum/interface.");
            throw new InvalidUserDataException("i18n-message-plugin: sourceTypes illegal, only can be enum/interface.");
        }
        generatorParams.setOutputFileNamePrefix(generator.getOutputFileNamePrefix());
        generatorParams.setLanguage(generator.getLanguage());
        generatorParams.setDefaultLanguage(generator.getDefaultLanguage());
        generatorParams.setSolutionLabelSuffix(generator.getSolutionLabelSuffix());
        log.info("i18n-message-plugin generatorParams: " + generatorParams);
        Generator internalGenerator = new Generator(generatorParams);
        try {
            internalGenerator.generate(new File(generator.getSourceDirectory()), new File(generator.getOutputDirectory()));
        } catch (Exception e) {
            log.error("i18n-message-plugin generate failed, ", e);
            throw new GradleException("i18n-message-plugin generate failed, ", e);
        }
    }
}
