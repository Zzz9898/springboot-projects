package com.ichat.service.Impl;

import com.ichat.mapper.UsersMapper;
import com.ichat.pojo.Users;
import com.ichat.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UsersMapper usersMapper;
    @Override
    public Users userLogin(Users users) {
        Users user = usersMapper.userLogin(users.getUsername(), users.getPassword());
        return user;
    }

    @Override
    public boolean selectUserByUsername(String username) {
        Users user = usersMapper.selectUserByUsername(username);
        if(user == null){
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userRegister(Users user) {
        usersMapper.insert(user);
    }
}
