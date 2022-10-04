package com.xiaozhi.common;

import lombok.Getter;

/**
 * @author 20232
 */

@Getter
public enum Code {
    RESULT_SUCCESS(200,"success")

    ;

    private Integer code;

    private String message;

    private Code(Integer code,String message){
        this.code = code;
        this.message = message;
    }





}





