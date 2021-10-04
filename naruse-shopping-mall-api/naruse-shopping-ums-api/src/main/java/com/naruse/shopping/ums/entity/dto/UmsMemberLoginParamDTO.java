package com.naruse.shopping.ums.entity.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UmsMemberLoginParamDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
