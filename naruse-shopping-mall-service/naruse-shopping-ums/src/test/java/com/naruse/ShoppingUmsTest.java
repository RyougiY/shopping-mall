package com.naruse;

import com.naruse.shoppingUms.UmsSpringBootApplication;
import com.naruse.shoppingUms.entity.UmsMember;
import com.naruse.shoppingUms.mapper.UmsMemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UmsSpringBootApplication.class)
public class ShoppingUmsTest {
    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Test
    void testInsert() {
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername("Naruse Shinji");
        umsMember.setPassword("123456");
        umsMember.setIcon("icon");
        umsMember.setEmail("1660336171@qq.com");
        umsMember.setNickName("nick");
        umsMember.setNote("note");
        umsMemberMapper.insert(umsMember);
    }
}
