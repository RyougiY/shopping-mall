package com.naruse.shopping.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naruse.shopping.ums.entity.UmsMember;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Naruse Shinji
 * @since 2021-10-04
 */
@Repository
public interface UmsMemberMapper extends BaseMapper<UmsMember> {
    long countUmsMemberByUsername(String username);

    UmsMember selectByUsername(String username);
}
