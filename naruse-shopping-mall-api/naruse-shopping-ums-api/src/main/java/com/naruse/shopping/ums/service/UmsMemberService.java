package com.naruse.shopping.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.naruse.shopping.ums.entity.UmsMember;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Naruse Shinji
 * @since 2021-10-04
 */
public interface UmsMemberService extends IService<UmsMember> {

    String register();

}
