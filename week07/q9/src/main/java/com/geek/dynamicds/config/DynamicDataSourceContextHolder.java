package com.geek.dynamicds.config;

import javax.xml.ws.Holder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author itguoy
 * @date 2021-11-06 18:48
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> HOLDER = new ThreadLocal<String>();
    public static List<String> dataSourceIds = new ArrayList<>();


    public static void setDataSourceType(String dataSourceType) {
        HOLDER.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return HOLDER.get();
    }

    public static void clearDataSourceType() {
        HOLDER.remove();
    }

    public static boolean containDataSourceId(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }

}
