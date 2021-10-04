package com.naruse.shopping.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.naruse.shopping.ums.entity.UmsMember;
import com.naruse.shopping.ums.mapper.UmsMemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naruse.shopping.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public String register() {
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername("测试一下！！！");
        umsMemberMapper.insert(umsMember);
        return "success!";
    }
}
