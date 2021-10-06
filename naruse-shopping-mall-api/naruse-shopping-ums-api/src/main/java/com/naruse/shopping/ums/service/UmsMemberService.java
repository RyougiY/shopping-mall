package com.naruse.shopping.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.naruse.shopping.common.base.result.ResultObject;
import com.naruse.shopping.ums.entity.UmsMember;
import com.naruse.shopping.ums.entity.dto.UmsMemberLoginParamDTO;
import com.naruse.shopping.ums.entity.dto.UmsMemberRegisterParamDTO;
import com.naruse.shopping.ums.entity.dto.UmsMemberUpdateParamDTO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Naruse Shinji
 * @since 2021-10-04
 */
public interface UmsMemberService extends IService<UmsMember> {

    ResultObject register(UmsMemberRegisterParamDTO umsMemberRegisterParamDTO);

    ResultObject login(UmsMemberLoginParamDTO umsMemberLoginParamDTO, HttpServletRequest request);

    ResultObject updateUserMember(UmsMemberUpdateParamDTO umsMemberUpdateParamDTO);

    Map<String, String> generateCode(HttpServletRequest request) throws IOException;

    ResultObject verify(String uuid, String verifyCode);
}
