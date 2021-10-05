package com.naruse.shopping.ums.entity.response;

import com.naruse.shopping.ums.entity.UmsMember;
import lombok.Data;

@Data
public class UserMemberLoginResponse {
    private String token;
    private UmsMember umsMember;
}
