package com.supergenius.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author : zuoyu
 * @project : management-security
 * @description : 返回集封装
 * @date : 2019-11-26 15:20
 **/
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(value = "Result", description = "响应数据和信息的封装")
public class Result {

    @NonNull
    @ApiModelProperty(value = "响应信息")
    private String message;

    @ApiModelProperty(value = "响应数据")
    private Object data;


    /**
     * 响应信息
     *
     * @param message - 信息
     * @return - 结果
     */
    public static Result message(String message) {
        return new Result(message);
    }

    /**
     * 响应信息和数据
     *
     * @param message - 信息
     * @param data - 数据
     * @return - 结果
     */
    public static Result detail(String message, Object data) {
        return new Result(message, data);
    }
}
