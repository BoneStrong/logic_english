package com.dzz.support.common.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import java.util.HashMap;
import javax.sql.DataSource;

/**
 * Druid DataSource 工厂
 *
 * @author Jeffrey
 * @since 2017/01/20 9:29
 */
public class BaseDruidDataSourceFactory {

    private DefaultDruidProperties properties;

    public BaseDruidDataSourceFactory(DefaultDruidProperties properties) {
        this.properties = properties;
    }

    public DefaultDruidProperties getProperties() {
        return properties;
    }

    public void setProperties(DefaultDruidProperties properties) {
        this.properties = properties;
    }

    public DataSource createDataSource() throws Exception {
        HashMap<String, Object> map = new HashMap<>(27);
        map.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, properties.getDriverClassName());
        map.put(DruidDataSourceFactory.PROP_URL, properties.getUrl());
        map.put(DruidDataSourceFactory.PROP_USERNAME, properties.getUsername());
        map.put(DruidDataSourceFactory.PROP_PASSWORD, properties.getPassword());
        map.put(DruidDataSourceFactory.PROP_INITIALSIZE, properties.getInitialSize());
        map.put(DruidDataSourceFactory.PROP_MINIDLE, properties.getMinIdle());
        map.put(DruidDataSourceFactory.PROP_MAXACTIVE, properties.getMaxActive());
        map.put(DruidDataSourceFactory.PROP_MAXWAIT, properties.getMaxWait());
        map.put(
            DruidDataSourceFactory.PROP_TIMEBETWEENEVICTIONRUNSMILLIS,
            properties.getTimeBetweenEvictionRunsMillis());
        map.put(
            DruidDataSourceFactory.PROP_MINEVICTABLEIDLETIMEMILLIS,
            properties.getMinEvictableIdleTimeMillis());
        map.put(DruidDataSourceFactory.PROP_VALIDATIONQUERY, properties.getValidationQuery());
        map.put(DruidDataSourceFactory.PROP_TESTWHILEIDLE, properties.getTestWhileIdle());
        map.put(DruidDataSourceFactory.PROP_TESTONBORROW, properties.getTestOnBorrow());
        map.put(DruidDataSourceFactory.PROP_TESTONRETURN, properties.getTestOnReturn());
        map.put(
            DruidDataSourceFactory.PROP_POOLPREPAREDSTATEMENTS,
            properties.getPoolPreparedStatements());
        map.put(DruidDataSourceFactory.PROP_FILTERS, properties.getFilters());
        map.put(DruidDataSourceFactory.PROP_CONNECTIONPROPERTIES,
            properties.getConnectionProperties());
        map.put(DruidDataSourceFactory.PROP_REMOVEABANDONED, properties.getRemoveAbandoned());
        map.put(DruidDataSourceFactory.PROP_REMOVEABANDONEDTIMEOUT,
            properties.getRemoveAbandonedTimeout());
        map.put(DruidDataSourceFactory.PROP_LOGABANDONED, properties.getLogAbandoned());
        try {
            return DruidDataSourceFactory.createDataSource(map);
        } catch (Exception e) {
            throw new Exception("create druid datasource error", e);
        }
    }
}
