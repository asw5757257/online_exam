package com.zyw.online_exam.graduation_design.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @name PropertiesUtil
 * @description
 * @create 2017-10-16 9:41
 **/
public class PropertiesUtil {

    private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

    private static java.util.Properties props;

    static {
        String fileName = "bs.properties";
        props = new java.util.Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
        } catch (IOException e) {
            log.error("配置文件读取异常", e);
        }
    }

    static String getProperty(String key) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key, String defaultValue) {

        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value.trim();
    }
}