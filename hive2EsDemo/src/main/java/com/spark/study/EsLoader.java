package com.spark.study;

import com.spark.study.beans.UserInfo;
import com.spark.study.config.AppConfig;
import com.spark.study.dao.UserInfoDao;
import com.spark.study.utils.EsUtil;
import com.spark.study.utils.SparkEnvUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.elasticsearch.spark.sql.api.java.JavaEsSparkSQL;

@Slf4j
public class EsLoader {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        SparkSession spark = SparkEnvUtil.getSparkEnvWithHiveAndEsSup(appConfig);
        loadUserDataFromHive2Es(spark,appConfig);
        spark.close();
    }

    public static void loadUserDataFromHive2Es(SparkSession spark,AppConfig appConfig) {
        Dataset<UserInfo> userDataset = new UserInfoDao().getUserDataset(spark,appConfig).persist();
        String date = userDataset.first().getDt().replace("-", "");
        //效果：es每天生成一个index
        String resource = "user_info" + "_" + date + "/docs";
        String mappingIdField = "user_id";

        EsUtil.loadDataFromHive2Es(userDataset, resource, mappingIdField);

        //休息10s后开始进行count检查
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error("thread sleep failed", e);
        }

        Dataset<Row> userDataset_Es = JavaEsSparkSQL.esDF(spark, resource);
        EsUtil.checkCountBetweenHiveAndEs(userDataset, userDataset_Es, date);

    }

}
