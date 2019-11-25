package com.supergenius.exception;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 自定义异常拦截.
 *
 * @author zuoyu
 **/
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<R> handlerCustomException(CustomException e) {
        log.error("errorCode is : " + e.getCode() + "\t" + "errorMessage is : " + e.getMsg());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.failed(e.getMsg()));
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<R> handlerNullPointerException(NullPointerException e) {
        return exceptionFormat(e.getMessage());
    }

    /**
     * sql异常
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<R> handlerSqlException(SQLException e) {
        return exceptionFormat(e.getErrorCode(), e.getMessage());
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<R> handlerClassCastException(ClassCastException e) {
        return exceptionFormat(e.getMessage());
    }

    /**
     * IO异常
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<R> handlerIoException(IOException e) {
        return exceptionFormat(e.getMessage());
    }

    /**
     * 未知方法异常
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseEntity<R> handlerNoSuchMethodException(NoSuchMethodException e) {
        return exceptionFormat(e.getMessage());
    }

    /**
     * 数组越界异常
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<R> handlerIndexOutOfBoundsException(IndexOutOfBoundsException e) {
        return exceptionFormat(e.getMessage());
    }

    /**
     * 栈溢出异常
     */
    @ExceptionHandler(StackOverflowError.class)
    public ResponseEntity<R> handlerStackOverflowError(StackOverflowError e) {
        return exceptionFormat(e.getMessage());
    }


    /**
     * 格式化
     */
    private ResponseEntity<R> exceptionFormat(String message) {
        log.error("errorMessage is : " + message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.failed("服务器异常"));
    }

    private ResponseEntity<R> exceptionFormat(int code, String message) {
        log.error("errorCode is : " + code + "\t" + "errorMessage is : " + message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.failed("服务器异常"));
    }
}
