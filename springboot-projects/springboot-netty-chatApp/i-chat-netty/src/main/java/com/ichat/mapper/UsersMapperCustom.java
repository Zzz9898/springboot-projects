package com.ichat.mapper;

import com.ichat.pojo.vo.FriendRequestVO;
import com.ichat.pojo.vo.MyFriendsVO;

import java.util.List;

public interface UsersMapperCustom {
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    public List<MyFriendsVO> queryMyFriends(String userId);

    public void batchUpdateMsgSigned(List<String> msgIdList);
}