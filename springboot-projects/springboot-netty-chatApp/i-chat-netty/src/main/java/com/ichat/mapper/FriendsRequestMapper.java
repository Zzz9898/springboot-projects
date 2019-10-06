package com.ichat.mapper;

import com.ichat.pojo.FriendsRequest;
import com.ichat.pojo.MyFriends;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendsRequestMapper {
    int deleteByPrimaryKey(String id);

    int insert(FriendsRequest record);

    FriendsRequest selectByPrimaryKey(String id);

    List<FriendsRequest> selectAll();

    int updateByPrimaryKey(FriendsRequest record);

    MyFriends selectHaveFriendReq(@Param("myUserId") String myUserId, @Param("id") String id);

    void deleteRelByKeys(@Param("sendUserId") String sendUserId, @Param("acceptUserId") String acceptUserId);
}