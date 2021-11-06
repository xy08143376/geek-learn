package com.geek.dynamicds.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author itguoy
 * @date 2021-11-06 18:47
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String dsKey = DynamicDataSourceContextHolder.getDataSourceType();
        dsKey = (dsKey == null) ? "one" : dsKey;
        log.info("user datasource {}", dsKey);
        return dsKey;
    }
}
