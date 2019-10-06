package com.ichat.controller;

import com.ichat.enums.OperatorFriendRequestTypeEnum;
import com.ichat.enums.SearchFriendsStatusEnum;
import com.ichat.pojo.ChatMsg;
import com.ichat.pojo.Users;
import com.ichat.pojo.bo.UsersBo;
import com.ichat.pojo.vo.FriendRequestVO;
import com.ichat.pojo.vo.MyFriendsVO;
import com.ichat.pojo.vo.UsersVo;
import com.ichat.service.UserService;
import com.ichat.utils.FileUtils;
import com.ichat.utils.JSONRes;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/me")
public class MeController {
    @Autowired
    private UserService userService;

    @PostMapping("/uploadFaceBase64")
    public JSONRes uploadFaceBase64(@RequestBody UsersBo usersBo) throws Exception {
        String base64Data = usersBo.getFaceData();
        String dirPath = "C://ichat/face/";
        String fileName = usersBo.getUserId() + "_userface64.png";
        String fileName_min = usersBo.getUserId() + "_userface64_80x80.png";
        String userFacePath = dirPath + fileName;
        String userFacePath_min = dirPath + fileName_min;
        File file = new File(userFacePath);
        File file_mini = new File(userFacePath_min);
        FileUtils.base64ToFile(userFacePath,base64Data);
        MultipartFile multipartFile = FileUtils.fileToMultipart(userFacePath);
        if(file.getParentFile() != null || !file.getParentFile().isDirectory()){
            file.getParentFile().mkdirs();
        }
        if(file_mini.getParentFile() != null || !file_mini.getParentFile().isDirectory()){
            file_mini.getParentFile().mkdirs();
        }
        multipartFile.transferTo(file);
        Thumbnails.of(file).size(80, 80).toFile(file_mini);
        Users users =userService.updateFaceByKey(usersBo.getUserId(),fileName,fileName_min);
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(users,usersVo);
        return JSONRes.ok(usersVo);
    }

    @PostMapping("/setNickname")
    public JSONRes setNickname(@RequestBody UsersBo usersBo){
        Users user = userService.updateNickname(usersBo.getUserId(),usersBo.getNickname());
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(user,usersVo);
        return JSONRes.ok(usersVo);
    }

    @PostMapping("/search")
    public JSONRes search(String myUserId,String friendUsername){
        if(StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)){
            return JSONRes.errorMsg("id不能为空！");
        }
        Integer status = userService.selectSearchStatus(myUserId,friendUsername);
        if(status == SearchFriendsStatusEnum.SUCCESS.status){
            Users user = userService.selectUserByUsername(friendUsername);
            UsersVo usersVo = new UsersVo();
            BeanUtils.copyProperties(user,usersVo);
            return JSONRes.ok(usersVo);
        }else{
            String errMsg =  SearchFriendsStatusEnum.getMsgByKey(status);
            return JSONRes.errorMsg(errMsg);
        }
    }

    @PostMapping("/addFriendRequest")
    public JSONRes addFriendRequest(String myUserId,String friendUsername){
        System.out.println("addFriendRequest--------------");
        System.out.println("myUserId--------------"+myUserId);
        System.out.println("friendUsername--------------"+friendUsername);
        if(StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)){
            return JSONRes.errorMsg("id不能为空！");
        }
        Integer status = userService.selectSearchStatus(myUserId,friendUsername);
        System.out.println("status-----------"+status);
        if(status == SearchFriendsStatusEnum.SUCCESS.status){
            userService.addFriendRequest(myUserId,friendUsername);
            return JSONRes.ok();
        }else{
            String errMsg =  SearchFriendsStatusEnum.getMsgByKey(status);
            return JSONRes.errorMsg(errMsg);
        }
    }

    @PostMapping("/queryFriendRequests")
    public JSONRes queryFriendRequestList(String userId){
        if(StringUtils.isBlank(userId)){
            return JSONRes.errorMsg("id不能为空！");
        }
        List<FriendRequestVO> list = userService.queryFriendRequestList(userId);
        return JSONRes.ok(list);
    }

    @PostMapping("/operFriendRequest")
    public JSONRes operFriendRequest(String acceptUserId,String sendUserId,Integer operType){
        if(StringUtils.isBlank(acceptUserId) || StringUtils.isBlank(sendUserId) || operType == null){
            return JSONRes.errorMsg("请求错误！");
        }
        if(StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByType(operType))){
            return JSONRes.errorMsg("未知错误！");
        }
        if(operType == OperatorFriendRequestTypeEnum.IGNORE.type){
            userService.deleteFriendRequest(sendUserId,acceptUserId);
        }else if(operType == OperatorFriendRequestTypeEnum.PASS.type){
            userService.saveFriendRequest(sendUserId,acceptUserId);
        }
        List<MyFriendsVO> myFriendsVOS =  userService.queryMyFriends(acceptUserId);
        return JSONRes.ok(myFriendsVOS);
    }

    @PostMapping("/myFriends")
    public JSONRes myFriends(String userId){
        List<MyFriendsVO> myFriendsVOS =  userService.queryMyFriends(userId);
        return JSONRes.ok(myFriendsVOS);
    }

    @PostMapping("/getUnReadMsgList")
    public JSONRes getUnReadMsgList(String accept){
        List<ChatMsg> chatMsgs = userService.getUnReadMsgList(accept);
        return JSONRes.ok(chatMsgs);
    }
}
