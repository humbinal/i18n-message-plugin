package io.github.humbinal.i18n.message.plugin.core;

/**
 * @author humbianl
 */
public class MessageEntity {

    /**
     * message文件的语言, 默认中文
     */
    private String language = "zh_CN";

    /**
     * 属性标签
     */
    private String label;

    /**
     * message的值, 与language一致的语言
     */
    private String message;

    /**
     * message处理建议的翻译
     */
    private String solution;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
