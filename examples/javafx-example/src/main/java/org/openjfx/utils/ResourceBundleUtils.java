package org.openjfx.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author humbinal
 */
public class ResourceBundleUtils {

    private static final ResourceBundle resource;

    static {
        resource = ResourceBundle.getBundle("i18n/message", Locale.getDefault());
    }

    private ResourceBundleUtils() {
    }

    public static ResourceBundle getResource() {
        return resource;
    }

    public static String getStringValue(String key) {
        return resource.getString(key);
    }
}
