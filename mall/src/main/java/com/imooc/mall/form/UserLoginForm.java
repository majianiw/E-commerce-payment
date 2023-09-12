package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Date: create in 15:27 2023/2/14
 * @describe:用于登录
 */
@Data
public class UserLoginForm {

    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
