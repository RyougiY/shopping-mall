package com.naruse.shopping.ums.entity.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
    @NotEmpty(message = "用户名不能为空！")
    @Size(min = 1, max = 64, message = "用户名长度只能在1~64之间！")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空！")
    @Size(min = 8, max = 16, message = "密码长度须在8~16之间！")
    private String password;

    /**
     * 头像
     */
    private String icon;

    /**
     * 邮箱
     */
    @Email
    private String email;

    /**
     * 昵称
     */
    private String nickName;
}
