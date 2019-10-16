package com.spark.study.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

@Slf4j
public class ConfigLoader implements Serializable {

    public static Properties loadProps(String resourceName) {
        Properties props = new Properties();
        try {
            props.load(ConfigLoader.class.getResourceAsStream(resourceName));
        } catch (IOException e) {
            log.error("load properties failed,resourceName:{}", resourceName, e);
        }
        return props;
    }
}
