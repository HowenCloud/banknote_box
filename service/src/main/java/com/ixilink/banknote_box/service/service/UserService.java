package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.common.model.UserSimple;
import com.ixilink.banknote_box.common.pojo.User;
import com.ixilink.banknote_box.service.parameter_model.user.AddUserModel;
import com.ixilink.banknote_box.service.parameter_model.user.FindUserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-08 22:07
 */
public interface UserService {

    void addUser(AddUserModel param, HttpServletRequest request, HttpServletResponse response);

    void removeUser(Integer id, HttpServletRequest request, HttpServletResponse response);

    List<User> getUserAll();

    List<User> getUsers(FindUserModel param, HttpServletRequest request, HttpServletResponse response);

    void modifyUser(AddUserModel param, HttpServletRequest request, HttpServletResponse response);

    List<Map<String,Object>> department(Integer libraryId, HttpServletRequest request);

    User infoById(Integer id, HttpServletRequest request);

    List<Map<String,Object>> role(Integer libraryId, HttpServletRequest request);

    List<UserSimple> getDepositUser(HttpServletRequest request);
}
