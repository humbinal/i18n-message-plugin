package io.github.humbinal.i18n.message.plugin.core;

/**
 * @author humbianl
 */
public class UnicodeUtils {

    private static final String PREFIX = "\\u";

    /**
     * native2ascii
     *
     * @param text native
     * @return ascii
     */
    public static String native2ascii(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            String ascii = char2ascii(ch);
            sb.append(ascii);
        }
        return sb.toString();
    }

    /**
     * char to ascii
     *
     * @param ch char
     * @return ascii
     */
    private static String char2ascii(char ch) {
        if (ch < 256) {
            return Character.toString(ch);
        }
        String hex = Integer.toHexString(ch);
        if (hex.length() < 4) {
            hex = "0" + hex;
        }
        return PREFIX + hex;
    }

}
