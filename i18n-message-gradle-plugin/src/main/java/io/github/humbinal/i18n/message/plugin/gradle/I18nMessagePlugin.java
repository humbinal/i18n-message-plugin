package io.github.humbinal.i18n.message.plugin.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I18nMessagePlugin implements Plugin<Project> {

    private static final Logger log = LoggerFactory.getLogger(I18nMessagePlugin.class);

    @Override
    public void apply(Project project) {
        project.getPlugins().apply(JavaLibraryPlugin.class);
        project.getPlugins().apply(MavenPublishPlugin.class);
        GeneratorExtension generator = project.getExtensions().create("i18nMessageGenerator", GeneratorExtension.class, project);
        GeneratorTask generatorTask = project.getTasks().create("generateI18nMessage", GeneratorTask.class, generator);
        Task classTask = project.getTasks().getByName(JavaPlugin.CLASSES_TASK_NAME);
        Task jarTask = project.getTasks().getByName(JavaPlugin.JAR_TASK_NAME);
        generatorTask.dependsOn(classTask);
        jarTask.dependsOn(generatorTask);
        log.warn("i18n message plugin configured");
    }
}
