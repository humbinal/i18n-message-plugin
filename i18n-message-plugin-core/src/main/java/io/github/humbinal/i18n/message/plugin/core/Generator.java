package io.github.humbinal.i18n.message.plugin.core;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author humbianl
 */
public class Generator {

    /**
     * 解析参数
     */
    private final GeneratorParams generatorParams;

    /**
     * 解析结果列表
     */
    private final List<MessageEntity> messageEntities;

    public Generator(GeneratorParams generatorParams) {
        this.generatorParams = generatorParams;
        this.messageEntities = new ArrayList<>();
    }

    public void generate(File sourceDirectory, File outputDirectory) throws Exception {
        parseFromSource(sourceDirectory);
        if (!outputDirectory.exists()) {
            boolean make = outputDirectory.mkdirs();
            if (!make) {
                throw new IllegalArgumentException(String.format("Make outputDirectory %s failed!", outputDirectory.getPath()));
            }
        }
        if (outputDirectory.exists() && outputDirectory.isFile()) {
            throw new IllegalArgumentException("The expected outputDirectory is folder, but a file was found !");
        }
        //获取所有的label及其message、solution对应关系组成的map集合
        List<Map.Entry<String, String>> allLabelAndValues = allLabelAndValues();
        //输出到指定文件
        if (outputDirectory.isDirectory()) {
            if (generatorParams.getDefaultLanguage().equalsIgnoreCase(generatorParams.getLanguage())) {
                String filePathDefault = outputDirectory.getPath() + "/" + generatorParams.getOutputFileNamePrefix() + "." + generatorParams.getOutputFileFormat();
                saveToFile(filePathDefault, allLabelAndValues);
            }
            String filePathLanguage = outputDirectory.getPath() + "/" + generatorParams.getOutputFileNamePrefix() + "_" + generatorParams.getLanguage() + "." + generatorParams.getOutputFileFormat();
            saveToFile(filePathLanguage, allLabelAndValues);
        } else {
            throw new IllegalArgumentException(String.format("outputDirectory=%s is not directory", outputDirectory.getPath()));
        }
    }

    private void parseFromSource(File source) throws Exception {
        if (source.isDirectory()) {
            for (File file : Objects.requireNonNull(source.listFiles())) {
                parseFromSource(file);
            }
        }
        this.messageEntities.addAll(JavaDocParser.parse(source, this.generatorParams));
    }

    private List<Map.Entry<String, String>> allLabelAndValues() {
        List<MessageEntity> message = this.messageEntities.stream()
                .filter(x -> StringUtils.isNotEmpty(x.getMessage())).collect(Collectors.toList());
        //去重, 遇到重复的key抛出异常
        Map<String, String> distinctResult = new HashMap<>();
        for (MessageEntity result : message) {
            String label = result.getLabel();
            String value = result.getMessage();
            if (distinctResult.containsKey(label)) {
                throw new IllegalArgumentException("find duplicate i18n label: " + label);
            }
            distinctResult.put(label, value);
        }
        List<MessageEntity> solution = this.messageEntities.stream()
                .filter(x -> StringUtils.isNotEmpty(x.getSolution())).collect(Collectors.toList());
        for (MessageEntity result : solution) {
            String label = result.getLabel() + generatorParams.getSolutionLabelSuffix();
            String value = result.getSolution();
            if (distinctResult.containsKey(label)) {
                throw new IllegalArgumentException("find duplicate i18n label: " + label);
            }
            distinctResult.put(label, value);
        }
        //按key排序
        List<Map.Entry<String, String>> allLabelAndValues = new ArrayList<>(distinctResult.entrySet());
        allLabelAndValues.sort(Map.Entry.comparingByKey());
        return allLabelAndValues;
    }

    /**
     * 将数据保存到文件中
     *
     * @param filePath       文件路径
     * @param labelAndValues 所有message信息
     * @throws IOException
     */
    private void saveToFile(String filePath, List<Map.Entry<String, String>> labelAndValues) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (Map.Entry<String, String> entry : labelAndValues) {
                bufferedWriter.write(entry.getKey() + "=" + UnicodeUtils.native2ascii(entry.getValue()) + "\n");
            }
            bufferedWriter.flush();
        }
    }

}
