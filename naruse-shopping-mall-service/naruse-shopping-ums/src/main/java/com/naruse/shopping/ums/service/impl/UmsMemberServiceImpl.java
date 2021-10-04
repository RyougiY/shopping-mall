package com.naruse.shopping.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.naruse.shopping.ums.entity.UmsMember;
import com.naruse.shopping.ums.entity.dto.UmsMemberLoginParamDTO;
import com.naruse.shopping.ums.entity.dto.UmsMemberRegisterParamDTO;
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
    public String register(UmsMemberRegisterParamDTO umsMemberRegisterParamDTO) {
        long count = umsMemberMapper.countUmsMemberByUsername(umsMemberRegisterParamDTO.getUsername());
        if (count > 0) {
            return "用户名重复！无法注册！";
        }
        UmsMember umsMember = new UmsMember();
        //将DTO转换成entity
        BeanUtils.copyProperties(umsMemberRegisterParamDTO, umsMember);

        //关键信息脱敏
        String encode = passwordEncoder.encode(umsMember.getPassword());
        umsMember.setPassword(encode);

        umsMemberMapper.insert(umsMember);
        return "success!";
    }

    @Override
    public String login(UmsMemberLoginParamDTO umsMemberLoginParamDTO) {
        UmsMember umsMember = umsMemberMapper.selectByUsername(umsMemberLoginParamDTO.getUsername());
        if (ObjectUtils.isEmpty(umsMember) ||
                (!passwordEncoder.matches(umsMemberLoginParamDTO.getPassword(), umsMember.getPassword()))) {
            return "用户名或密码错误！";
        }

        return "Welcome to shopping mall! ";
    }
}
