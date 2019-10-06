package com.ichat.service;

import com.ichat.netty.ChatInfo;
import com.ichat.pojo.ChatMsg;
import com.ichat.pojo.Users;
import com.ichat.pojo.vo.FriendRequestVO;
import com.ichat.pojo.vo.MyFriendsVO;

import java.util.List;

public interface UserService {
    Users updateFaceByKey(String userId, String fileName, String fileName_min);

    Users updateNickname(String userId, String nickname);

    Integer selectSearchStatus(String myUserId, String friendUsername);

    Users selectUserByUsername(String friendUsername);

    void addFriendRequest(String myUserId, String friendUsername);

    List<FriendRequestVO> queryFriendRequestList(String userId);

    void deleteFriendRequest(String sendUserId, String acceptUserId);

    void saveFriendRequest(String sendUserId, String acceptUserId);

    List<MyFriendsVO> queryMyFriends(String userId);

    String saveMsg(ChatInfo chatInfo);

    void updateMsgSigned(List<String> idsList);

    List<ChatMsg> getUnReadMsgList(String accept);
}
