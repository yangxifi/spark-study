package com.spark.study.utils;

import com.spark.study.config.AppConfig;
import com.spark.study.serializer.MyKryoRegistrator;
import org.apache.spark.sql.SparkSession;

public class SparkEnvUtil {

    public static SparkSession getSparkEnvWithHiveAndEsSup(AppConfig appConfig) {
        SparkSession spark = SparkSession.builder()
                // 注意本地测试完通过后把这行注释掉 不然服务器上启动
                // 会报java.lang.IllegalStateException: User did not initialize spark context!
//                .master("local[*]")
                .appName("hive2esDemo").config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .config("spark.kryo.registrator", MyKryoRegistrator.class.getName())
                .config("hive.metastore.uris", appConfig.getHivemetastoreUris())//指定hive的metastore的地址
                .config("spark.sql.warehouse.dir", appConfig.getHiveWarehouseDir())//指定hive的warehouse目录
                .config("es.nodes", appConfig.getEsNodes())//es的nodes
                .config("es.index.auto.create", "true")//配置es自动创建索引
                .enableHiveSupport()
                .getOrCreate();
        return spark;
    }

}
