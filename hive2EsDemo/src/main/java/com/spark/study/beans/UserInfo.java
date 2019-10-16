package com.spark.study.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo implements Serializable {
    Integer user_id;//用户id
    Integer age;//用户年龄
    String name;//用户姓名
    String dt;//日期
}
