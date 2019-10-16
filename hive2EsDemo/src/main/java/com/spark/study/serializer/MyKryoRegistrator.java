package com.spark.study.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.spark.study.beans.UserInfo;
import org.apache.spark.serializer.KryoRegistrator;

import java.io.Serializable;

public class MyKryoRegistrator implements KryoRegistrator, Serializable {
    @Override
    public void registerClasses(Kryo kryo) {
        kryo.register(UserInfo.class);
    }
}
