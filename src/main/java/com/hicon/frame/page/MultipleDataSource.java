package com.hicon.frame.page;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源的支持类
 *
 * @作者： lichao
 * @时间： 2019-10-2019/10/31 11:24
 * @版本：
 * @说明：
 */
public class MultipleDataSource extends AbstractRoutingDataSource {
    private static final Logger logger = Logger.getLogger(MultipleDataSource.class);
    private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("dataSourceKey="+dataSourceKey.get());
        return dataSourceKey.get();
    }
}
