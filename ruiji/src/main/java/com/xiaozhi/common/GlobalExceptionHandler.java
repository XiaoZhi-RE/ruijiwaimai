package com.xiaozhi.common;


import com.xiaozhi.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;


/**
 * @author 20232
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     *
     * @return Result
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> handleException(SQLIntegrityConstraintViolationException ex) {

        log.error(ex.getMessage());
        //        判断异常的类型
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已经存在";
            return Result.error(msg);
        }
        return Result.error("未知错误");
    }


    @ExceptionHandler(CustomException.class)
    public Result<String> handleException(CustomException ex){
        log.error(ex.getMessage());
        return Result.error(ex.getMessage());

    }
}
