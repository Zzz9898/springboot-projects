package com.ichat.service.Impl;

import com.ichat.enums.MsgActionEnum;
import com.ichat.enums.MsgSignFlagEnum;
import com.ichat.enums.SearchFriendsStatusEnum;
import com.ichat.mapper.*;
import com.ichat.netty.ChatInfo;
import com.ichat.netty.DataContent;
import com.ichat.netty.UserChannelRel;
import com.ichat.pojo.ChatMsg;
import com.ichat.pojo.FriendsRequest;
import com.ichat.pojo.MyFriends;
import com.ichat.pojo.Users;
import com.ichat.pojo.vo.FriendRequestVO;
import com.ichat.pojo.vo.MyFriendsVO;
import com.ichat.service.UserService;
import com.ichat.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private Sid sid;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UsersMapperCustom usersMapperCustom;
    @Autowired
    private MyFriendsMapper myFriendsMapper;
    @Autowired
    private FriendsRequestMapper friendsRequestMapper;
    @Autowired
    private ChatMsgMapper chatMsgMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users updateFaceByKey(String userId, String fileName, String fileName_min) {
        usersMapper.updateFaceByKey(userId,fileName,fileName_min);
        Users users = usersMapper.selectByPrimaryKey(userId);
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users updateNickname(String userId, String nickname) {
        usersMapper.updateNickname(userId,nickname);
        Users user = usersMapper.selectByPrimaryKey(userId);
        return user;
    }

    @Override
    public Integer selectSearchStatus(String myUserId, String friendUsername) {
        Users user = usersMapper.selectUserByUsername(friendUsername);
        if(user == null){
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }
        if(user.getId().equals(myUserId)){
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }
        MyFriends friend = isFriend(myUserId, user.getId());
        if(friend != null){
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }
        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    @Override
    public Users selectUserByUsername(String friendUsername) {
        Users users = usersMapper.selectUserByUsername(friendUsername);
        return users;
    }

    @Override
    public void addFriendRequest(String myUserId, String friendUsername) {
        System.out.println("addFriendRequest------------");
        Users friend = usersMapper.selectUserByUsername(friendUsername);
        MyFriends myFriends = friendsRequestMapper.selectHaveFriendReq(myUserId,friend.getId());
        if(myFriends == null){
            System.out.println("add------------");
            String requestId = sid.nextShort();
            FriendsRequest friendsRequest = new FriendsRequest();
            friendsRequest.setId(requestId);
            friendsRequest.setSendUserId(myUserId);
            friendsRequest.setAcceptUserId(friend.getId());
            friendsRequest.setRequestDataTime(new Date());
            friendsRequestMapper.insert(friendsRequest);
        }
    }


    @Override
    public List<FriendRequestVO> queryFriendRequestList(String userId) {
        return usersMapperCustom.queryFriendRequestList(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteFriendRequest(String sendUserId, String acceptUserId) {
        friendsRequestMapper.deleteRelByKeys(sendUserId,acceptUserId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveFriendRequest(String sendUserId, String acceptUserId) {
        saveFriend(sendUserId,acceptUserId);
        saveFriend(acceptUserId,sendUserId);
        deleteFriendRequest(sendUserId,acceptUserId);

        Channel sendChannel = UserChannelRel.get(sendUserId);
        if (sendChannel != null) {
            // 使用websocket主动推送消息到请求发起者，更新他的通讯录列表为最新
            DataContent dataContent = new DataContent();
            dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);

            sendChannel.writeAndFlush(
                    new TextWebSocketFrame(
                            JsonUtils.objectToJson(dataContent)));
        }
    }

    @Override
    public List<MyFriendsVO> queryMyFriends(String userId) {
        List<MyFriendsVO> myFriendsVOS = usersMapperCustom.queryMyFriends(userId);
        return myFriendsVOS;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String saveMsg(ChatInfo chatInfo) {
        ChatMsg chatMsg = new ChatMsg();
        String id = sid.nextShort();
        chatMsg.setId(id);
        chatMsg.setAcceptUserId(chatInfo.getReceiverId());
        chatMsg.setSendUserId(chatInfo.getSenderId());
        chatMsg.setMsg(chatInfo.getMsg());
        chatMsg.setCreateTime(new Date());
        chatMsg.setSignFlag(MsgSignFlagEnum.unsign.type);
        chatMsgMapper.insert(chatMsg);
        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateMsgSigned(List<String> idsList) {
        usersMapperCustom.batchUpdateMsgSigned(idsList);
    }

    @Override
    public List<ChatMsg> getUnReadMsgList(String accept) {
        List<ChatMsg> chatMsgs = chatMsgMapper.selectByAccAndFlag(accept,0);
        return chatMsgs;
    }

    public MyFriends isFriend(String myUserId,String myFriendId){
        MyFriends myFriends = myFriendsMapper.selectIsFriend(myUserId,myFriendId);
        return myFriends;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveFriend(String myUserID,String myFriendUserId){
        MyFriends myFriends = new MyFriends();
        String recordId = sid.nextShort();
        myFriends.setId(recordId);
        myFriends.setMyUserId(myUserID);
        myFriends.setMyFriendUserId(myFriendUserId);
        myFriendsMapper.insert(myFriends);
    }
}
