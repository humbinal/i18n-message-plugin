package io.github.humbinal.i18n.message.plugin.core;

import org.junit.jupiter.api.Test;

import java.io.File;

class GeneratorTest {

    @Test
    void generate() throws Exception {
        GeneratorParams generatorParams = new GeneratorParams();
        generatorParams.setLanguage("zh_CN");
        generatorParams.setOutputFileNamePrefix("message");
        Generator generator = new Generator(generatorParams);
        File file = new File("");
        String canonicalPath = file.getCanonicalPath();
        generator.generate(new File(canonicalPath + "/src/test/java"),
                new File(canonicalPath + "/build/tmp/i18nMessagePlugin"));
    }

}