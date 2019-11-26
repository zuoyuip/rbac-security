package com.supergenius.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : zuoyu
 * @project : management-security
 * @description : 目录结构
 * @date : 2019-11-26 14:25
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ContentStructure {

    private String contentName;

    private String menuName;

    private String url;
}
