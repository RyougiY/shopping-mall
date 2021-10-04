package com.naruse.shopping.ums.entity.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 注册功能的DTO
 * 接收前端传过来的参数
 */
@Data
@ToString
public class UmsMemberRegisterParamDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String icon;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;
}
