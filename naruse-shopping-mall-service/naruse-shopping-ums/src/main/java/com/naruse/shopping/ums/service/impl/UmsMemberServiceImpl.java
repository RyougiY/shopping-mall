package com.naruse.shopping.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.naruse.shopping.common.base.result.ResultObject;
import com.naruse.shopping.common.util.util.JwtUtil;
import com.naruse.shopping.ums.entity.UmsMember;
import com.naruse.shopping.ums.entity.dto.UmsMemberLoginParamDTO;
import com.naruse.shopping.ums.entity.dto.UmsMemberRegisterParamDTO;
import com.naruse.shopping.ums.entity.dto.UmsMemberUpdateParamDTO;
import com.naruse.shopping.ums.entity.response.UserMemberLoginResponse;
import com.naruse.shopping.ums.mapper.UmsMemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naruse.shopping.ums.service.UmsMemberService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Naruse Shinji
 * @since 2021-10-04
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements IService<UmsMember>, UmsMemberService {

    @Autowired
    private UmsMemberMapper umsMemberMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResultObject register(UmsMemberRegisterParamDTO umsMemberRegisterParamDTO) {
        long count = umsMemberMapper.countUmsMemberByUsername(umsMemberRegisterParamDTO.getUsername());
        if (count > 0) {
            throw new RuntimeException("用户名重复！无法注册！");
        }
        UmsMember umsMember = new UmsMember();
        //将DTO转换成entity
        BeanUtils.copyProperties(umsMemberRegisterParamDTO, umsMember);

        //关键信息脱敏
        String encode = passwordEncoder.encode(umsMember.getPassword());
        umsMember.setPassword(encode);

        umsMemberMapper.insert(umsMember);
        return ResultObject.getSuccessBuilder().build();
    }

    @Override
    public ResultObject login(UmsMemberLoginParamDTO umsMemberLoginParamDTO) {
        UmsMember umsMember = umsMemberMapper.selectByUsername(umsMemberLoginParamDTO.getUsername());
        if (ObjectUtils.isEmpty(umsMember) ||
                (!passwordEncoder.matches(umsMemberLoginParamDTO.getPassword(), umsMember.getPassword()))) {
            throw new RuntimeException("用户名或密码错误！");
        }

        String token = JwtUtil.createToken(umsMemberLoginParamDTO.getUsername());
        UserMemberLoginResponse userMemberLoginResponse = new UserMemberLoginResponse();
        userMemberLoginResponse.setToken(token);
        userMemberLoginResponse.setUmsMember(umsMember);

        return ResultObject.getSuccessBuilder().data(userMemberLoginResponse).build();
    }

    @Override
    public ResultObject updateUserMember(UmsMemberUpdateParamDTO umsMemberUpdateParamDTO) {
        UmsMember umsMember = umsMemberMapper.selectById(umsMemberUpdateParamDTO.getId());
        if (ObjectUtils.isEmpty(umsMember) || !umsMember.getStatus()) {
            throw new RuntimeException("系统中没有该用户！无法更新信息");
        }

        BeanUtils.copyProperties(umsMemberUpdateParamDTO, umsMember);

        String encode = passwordEncoder.encode(umsMember.getPassword());
        umsMember.setPassword(encode);

        umsMemberMapper.updateById(umsMember);

        return ResultObject.getSuccessBuilder().data(umsMember).build();
    }
}
