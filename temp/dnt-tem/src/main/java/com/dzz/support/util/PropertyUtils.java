package com.dzz.support.util;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * property util
 *
 * @author Jeffrey
 * @since 2017/4/28 14:54
 */
@Component
public class PropertyUtils implements EnvironmentAware {

    private static RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        propertyResolver = new RelaxedPropertyResolver(environment);
    }

    /**
     * get property from application settings
     *
     * @param key key
     * @return property value
     */
    public static String getValue(String key) {
        return propertyResolver.getProperty(key);
    }

    /**
     * get property from application settings
     *
     * @param key key
     * @param clazz class type
     * @return property value
     */
    public static <T> T getValue(String key, Class<T> clazz) {
        return propertyResolver.getProperty(key, clazz);
    }
}
