package com.dzz.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.dzz.support.common.druid.BaseDruidDataSourceFactory;
import com.dzz.support.common.druid.DefaultDruidProperties;
import com.dzz.support.profile.DruidEnv;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * druid datasource config(support multi-datasource)
 *
 * @author Jeffrey
 * @since 2017/1/9 15:44
 */
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class, DefaultDruidProperties.class})
@DruidEnv
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = DefaultDruidProperties.DRUID_PREFIX)
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public BaseDruidDataSourceFactory druidDataSourceFactory(DefaultDruidProperties properties) {
        return new BaseDruidDataSourceFactory(properties);
    }

    /**
     * 注册一个StatViewServlet
     */
    @Bean
    public ServletRegistrationBean druidStatViewServle() {
        ServletRegistrationBean servletRegistrationBean =
            new ServletRegistrationBean(new StatViewServlet(), "/admin/druid/*");
        servletRegistrationBean.addInitParameter("resetEnable", "true");
        return servletRegistrationBean;
    }

    /**
     * 注册一个filterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
            new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter(
            "exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/admin/druid/*");
        return filterRegistrationBean;
    }
}
