package com.imooc.mall;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.exception.UserLoginException;
import com.imooc.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Date: create in 16:38 2023/2/16
 * @describe:登录拦截器
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    /**
     * @Description: true表示继续流程，false表示中断
     * @Date: 2023/2/16 16:40
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        log.info("preHandle...");
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
        if (user == null) {
            log.info("user==null");
            throw new UserLoginException();
        }
        return true;
    }

}
