package com.dzz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * spring相关配置
 *
 * @author Jeffrey
 * @since 2017/05/06 15:10
 */
@Configuration
public class SpringConfig {

    /**
     * mesthod param validate
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
