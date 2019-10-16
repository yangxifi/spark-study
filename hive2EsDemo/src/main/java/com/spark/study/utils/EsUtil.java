package com.spark.study.utils;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.elasticsearch.spark.sql.api.java.JavaEsSparkSQL;

@Slf4j
public class EsUtil {

    /**
     * 从hive加载到es
     */
    public static void loadDataFromHive2Es( Dataset ds, String resource, String mappingIdField) {
        JavaEsSparkSQL.saveToEs(ds, resource, ImmutableMap.of("es.mapping.id", mappingIdField));
    }

    /**
     * 检查hive和es的数据条数一致与否
     *
     * @param hiveDataSet hive中的数据
     * @param esDataSet   同步到es中的数据
     * @param date        分区（数据所属的日期）
     */
    public static void checkCountBetweenHiveAndEs(Dataset hiveDataSet, Dataset esDataSet, String date){
        long countInHive = hiveDataSet.count();
        long countInEs = esDataSet.count();
        log.info("count in hive:{},count in es:{}", countInHive, countInEs);
        if (countInHive != countInEs) {
            String message = "DataCountCheck error,count in hive:" + countInHive + ",but count in es:" + countInEs
                    + ",and its dt:" + date;
            log.error(message);
        }
    }
}
