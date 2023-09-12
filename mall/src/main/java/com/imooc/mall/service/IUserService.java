package com.imooc.mall.service;

import com.imooc.mall.pojo.User;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.stereotype.Service;

/**
 * @Date: create in 9:42 2023/2/14
 * @describe:
 */
@Service
public interface IUserService {
    /**
    * @Description:注册
    * @Date: 2023/2/14 9:42
    */
    ResponseVo register(User user);

    /**
    * @Description:登录
    * @Date: 2023/2/14 9:42
    */

    ResponseVo<User> login(String username,String password);
}
