package com.supergenius.model.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author : zuoyu
 * @project : management-security
 * @description : 目录
 * @date : 2019-11-26 14:15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Content对象", description="目录")
public class Content {

    @ApiModelProperty(value = "目录名称")
    private String name;

    @ApiModelProperty(value = "菜单列表")
    private List<Menu> menus;

    public Content(String name) {
        this.name = name;
    }
}
