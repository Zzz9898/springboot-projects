package com.ichat.controller;


import com.ichat.pojo.Users;
import com.ichat.pojo.vo.UsersVo;
import com.ichat.service.LoginService;
import com.ichat.utils.JSONRes;
import com.ichat.utils.MD5Utils;
import com.ichat.utils.QRCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private Sid sid;
    @PostMapping("/login")
    public JSONRes login(@RequestBody Users user) throws Exception {
        System.out.println("login-------"+user);
        if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
            return JSONRes.errorMsg("用户名或密码不能为空！");
        }else{
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            Users users = loginService.userLogin(user);
            if(users == null){
                return JSONRes.errorMsg("账号或密码不正确！");
            }
            UsersVo usersVo = new UsersVo();
            BeanUtils.copyProperties(users,usersVo);
            return JSONRes.ok(usersVo);
        }
    }

    @PostMapping("/register")
    public JSONRes register(@RequestBody Users user) throws Exception {
        System.out.println("register-------"+user);
        if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
            return JSONRes.errorMsg("用户名或密码不能为空！");
        }else{
            boolean res = loginService.selectUserByUsername(user.getUsername());
            if(res){
                user.setId(sid.nextShort());
                user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
                user.setNickname(user.getUsername());
                user.setCid(user.getCid());
                user.setFaceImage("face-default-cat_80x80.jpg");
                user.setFaceImageBig("face-default-cat.jpg");
                String qrDirPath = "C://ichat/qrcode/";
                String qrCodeName = user.getId()+"_qrcode.png";
                String qrCodePath = qrDirPath + qrCodeName;
                File qrcode = new File(qrCodePath);
                if(qrcode.getParentFile() != null || !qrcode.getParentFile().isDirectory()){
                    qrcode.getParentFile().mkdirs();
                }
                QRCodeUtils.createQRCode(qrCodePath,"ichat-qrcode:"+user.getUsername());
                user.setQrcode(qrCodeName);
                loginService.userRegister(user);
                UsersVo usersVo = new UsersVo();
                BeanUtils.copyProperties(user,usersVo);
                return JSONRes.ok(usersVo);
            }else{
                return JSONRes.errorMsg("用户名已存在！");
            }
        }
    }
}
