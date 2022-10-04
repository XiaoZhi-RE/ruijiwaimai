package com.xiaozhi.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果
 * @author 20232
 */


@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
    private Map<String, Object> map = new HashMap<String, Object>();


    public static <T> Result<T> success(T object) {

        Result<T> result = new Result<>();
        result.data = object;
        result.code = 1;

        return result;

    }




    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code = 0;

        return result;
    }


    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
