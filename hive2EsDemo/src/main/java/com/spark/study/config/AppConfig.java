package com.spark.study.config;

import lombok.Getter;

import java.util.Properties;

@Getter
public class AppConfig {
    private String hiveWarehouseDir;
    private String hivemetastoreUris;
    private String esNodes;
    private String userDbName;
    private String userTableName;

    public AppConfig() {
        Properties props = ConfigLoader.loadProps("/application.properties");
        this.hiveWarehouseDir = props.getProperty("hive.warehouse.dir");
        this.hivemetastoreUris = props.getProperty("hive.metastore.uris");
        this.esNodes = props.getProperty("es.nodes");
        this.userDbName = props.getProperty("user.db.name");
        this.userTableName = props.getProperty("user.table.name");
    }

}
