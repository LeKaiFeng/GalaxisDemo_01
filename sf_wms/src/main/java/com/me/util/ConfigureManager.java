package com.me.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class ConfigureManager {
    private static final Logger log = LoggerFactory.getLogger(ConfigureManager.class);

    private static Map<String, String> conf = new HashMap<>();

    static {
        InputStream is = null;
        try {
            is = new FileInputStream(new File("conf.properties"));
            Properties properties = new Properties();
            properties.load(is);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                conf.put(entry.getKey().toString(), entry.getValue().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(is)) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String get(String key) {
        return conf.get(key);
    }

    public static Integer getNumber(String key) {
        String v = get(key);
        return Integer.valueOf(v);
    }

}
