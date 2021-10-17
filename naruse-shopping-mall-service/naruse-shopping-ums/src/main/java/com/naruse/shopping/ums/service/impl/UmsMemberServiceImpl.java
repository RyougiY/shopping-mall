package com.naruse.shopping.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.naruse.shopping.common.base.constant.UserMemberConstant;
import com.naruse.shopping.common.base.exceptions.ShoppingMallBusinessException;
import com.naruse.shopping.common.base.result.ResultObject;
import com.naruse.shopping.common.util.util.JwtUtil;
import com.naruse.shopping.ums.entity.UmsMember;
import com.naruse.shopping.ums.entity.dto.UmsMemberLoginParamDTO;
import com.naruse.shopping.ums.entity.dto.UmsMemberRegisterParamDTO;
import com.naruse.shopping.ums.entity.dto.UmsMemberUpdateParamDTO;
import com.naruse.shopping.ums.entity.dto.VerifyCode;
import com.naruse.shopping.ums.entity.response.UserMemberLoginResponse;
import com.naruse.shopping.ums.mapper.UmsMemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naruse.shopping.ums.service.UmsMemberService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResultObject register(UmsMemberRegisterParamDTO umsMemberRegisterParamDTO) {
        long count = umsMemberMapper.countUmsMemberByUsername(umsMemberRegisterParamDTO.getUsername());
        if (count > 0) {
            throw new ShoppingMallBusinessException("用户名重复！无法注册！");
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
    public ResultObject login(UmsMemberLoginParamDTO umsMemberLoginParamDTO, HttpServletRequest request) {
        if (stringRedisTemplate.opsForValue().get(UserMemberConstant.LOGIN_RESTRICT_PREFIX + request.getRemoteAddr()) != null) {
            throw new ShoppingMallBusinessException("输入密码错误达上限！10分钟内无法在当前设备登录");
        }

        UmsMember umsMember = umsMemberMapper.selectByUsername(umsMemberLoginParamDTO.getUsername());
        if (ObjectUtils.isEmpty(umsMember)) {
            throw new ShoppingMallBusinessException("该用户不存在！");
        }

        stringRedisTemplate.opsForValue().setIfAbsent(UserMemberConstant.LOGIN_FAILED_COUNT + umsMember.getUsername(), "0");

        //密码防刷校验
        if ((!passwordEncoder.matches(umsMemberLoginParamDTO.getPassword(), umsMember.getPassword()))) {
            stringRedisTemplate.opsForValue().increment(UserMemberConstant.LOGIN_FAILED_COUNT + umsMember.getUsername());
            int count = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(umsMember.getUsername())));

            //密码输入错误到达5次，禁止登录10分钟
            if ((5-count) == 0) {
                stringRedisTemplate.delete(UserMemberConstant.LOGIN_FAILED_COUNT + umsMember.getUsername());
                stringRedisTemplate.opsForValue().set(UserMemberConstant.LOGIN_RESTRICT_PREFIX + request.getRemoteAddr(), "1", 10, TimeUnit.MINUTES);
                throw new ShoppingMallBusinessException("输入密码错误达上限！10分钟内无法在当前设备登录");
            }

            throw new ShoppingMallBusinessException("密码输入错误！还剩下" + (5 - count) + "次机会");
        }

        //密码输入正确，返回token
        String token = JwtUtil.createToken(umsMemberLoginParamDTO.getUsername());
        UserMemberLoginResponse userMemberLoginResponse = new UserMemberLoginResponse();
        userMemberLoginResponse.setToken(token);
        userMemberLoginResponse.setUmsMember(umsMember);
        stringRedisTemplate.delete(UserMemberConstant.LOGIN_FAILED_COUNT + umsMember.getUsername());

        return ResultObject.getSuccessBuilder().data(userMemberLoginResponse).build();
    }

    @Override
    public ResultObject updateUserMember(UmsMemberUpdateParamDTO umsMemberUpdateParamDTO) {
        UmsMember umsMember = umsMemberMapper.selectById(umsMemberUpdateParamDTO.getId());
        if (ObjectUtils.isEmpty(umsMember) || !umsMember.getStatus()) {
            throw new ShoppingMallBusinessException("系统中没有该用户！无法更新信息");
        }

        BeanUtils.copyProperties(umsMemberUpdateParamDTO, umsMember);

        //如果是设置密码，额外设置
        String newPassword = umsMemberUpdateParamDTO.getNewPassword();
        if (StringUtils.isNotBlank(newPassword)) {
            umsMember.setPassword(newPassword);
        }

        String encode = passwordEncoder.encode(umsMember.getPassword());
        umsMember.setPassword(encode);

        umsMemberMapper.updateById(umsMember);

        return ResultObject.getSuccessBuilder().data(umsMember).build();
    }

    @Override
    public Map<String, String> generateCode(HttpServletRequest request) throws IOException {

        /**
         * 1 -> PC
         * 2 -> IOS
         * 3 -> Android
         * 分析用户登录设备
         **/
//        String clientId = request.getHeader("client_id");

        //验证码防刷
        String remoteAddr = request.getRemoteAddr();
        String codeCount = stringRedisTemplate.opsForValue().get(UserMemberConstant.CODE_RESTRICT_PREFIX + remoteAddr);

        if (StringUtils.isNotBlank(codeCount) && Integer.parseInt(codeCount) >= 1) {
            throw new ShoppingMallBusinessException("1分钟之内只能发送一次验证码");
        }

        stringRedisTemplate.opsForValue()
                .setIfAbsent(UserMemberConstant.CODE_RESTRICT_PREFIX + remoteAddr, "1", 1, TimeUnit.MINUTES);

        return getVerifyCodeMap();
    }

    public ResultObject verify(String uuid, String verifyCode) {
        String code = stringRedisTemplate.opsForValue().get(uuid);

        if (StringUtils.isBlank(code)) {
            return ResultObject.getFailedBuilder().msg("验证码已过期！").build();
        }

        if (code.equals(verifyCode)) {
            stringRedisTemplate.delete(uuid);
            return ResultObject.getSuccessBuilder().msg("验证码通过").build();
        }

        return ResultObject.getFailedBuilder().msg("验证码输入错误！").build();
    }

    private Map<String, String> getVerifyCodeMap() throws IOException {
        VerifyCode verifyCode = VerifyCode.getInstance();
        BufferedImage image = verifyCode.getImage();

        String text = verifyCode.getText();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");

        stringRedisTemplate.opsForValue().set(uuid, text, 1, TimeUnit.MINUTES);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", outputStream);
        String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());

        Map<String, String> res = new HashMap<>();
        res.put("base64", base64);
        res.put("uuid", uuid);

        return res;
    }
}
