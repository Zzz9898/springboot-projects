package com.ichat.service;

import com.ichat.pojo.Users;

public interface LoginService {
    Users userLogin(Users users);

    boolean selectUserByUsername(String username);

    void userRegister(Users user);
}
