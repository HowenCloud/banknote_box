package com.ixilink.banknote_box.login.service;

import com.ixilink.banknote_box.login.parameter_model.LoginModel;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-11 16:27
 */
public interface LoginService {

    String login(LoginModel loginModel, HttpServletRequest request);

    String partnerLogin(LoginModel loginModel, HttpServletRequest request);

    void logout(HttpServletRequest request);

}
