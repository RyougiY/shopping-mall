package com.naruse.shopping.ums.entity.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@ToString
public class UmsMemberUpdateParamDTO {

    /** 主键id **/
    private Long id;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空！")
    @Size(min = 1, max = 64, message = "用户名长度只能在1~64之间！")
    private String username;

    /**
     * 密码
     */
    private String password;

    /** 新密码 **/
    private String newPassword;

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
