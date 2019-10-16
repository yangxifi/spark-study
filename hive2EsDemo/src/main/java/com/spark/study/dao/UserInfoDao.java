package com.spark.study.dao;

import com.spark.study.beans.UserInfo;
import com.spark.study.config.AppConfig;
import lombok.NoArgsConstructor;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;

@NoArgsConstructor
public class UserInfoDao implements Serializable {

    public Dataset<UserInfo> getUserDataset(SparkSession spark, AppConfig appConfig) {
        String tableName = appConfig.getUserDbName() + "." + appConfig.getUserTableName();
        //get最新日期的数据
        String sql = "select *" +
                " from " + tableName +
                " where dt in" +
                " (select dt from " + tableName + " order by dt desc limit 1)";
        Dataset<UserInfo> userDataset = spark.sql(sql).map((MapFunction<Row, UserInfo>) row -> {
            UserInfo userInfo = UserInfo.builder()
                    .user_id(row.getAs("user_id"))
                    .age(row.getAs("age"))
                    .name(row.getAs("name"))
                    .dt(row.getAs("dt"))
                    .build();
            return userInfo;
        }, Encoders.bean(UserInfo.class));

        return userDataset;
    }

}
