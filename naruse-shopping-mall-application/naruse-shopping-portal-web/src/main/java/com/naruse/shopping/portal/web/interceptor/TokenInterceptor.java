package com.naruse.shopping.portal.web.interceptor;

import com.naruse.shopping.common.base.annotations.TokenVerify;
import com.naruse.shopping.common.util.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * token认证的拦截器
 */
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(TokenVerify.class)) {

            String token = request.getHeader("token");
            if (StringUtils.isBlank(token)) {
                throw new RuntimeException("token不能为空！");
            }

            TokenVerify tokenVerify = method.getAnnotation(TokenVerify.class);
            if (tokenVerify.required()) {
                //满足条件，校验token
                try {
                    JwtUtil.parseToken(token);
                }catch (Exception e) {
                    throw new RuntimeException("token异常！请检查token！");
                }
                return true;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
