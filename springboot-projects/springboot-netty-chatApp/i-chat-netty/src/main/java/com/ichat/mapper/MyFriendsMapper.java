package com.ichat.mapper;

import com.ichat.pojo.MyFriends;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyFriendsMapper {
    int deleteByPrimaryKey(String id);

    int insert(MyFriends record);

    MyFriends selectByPrimaryKey(String id);

    List<MyFriends> selectAll();

    int updateByPrimaryKey(MyFriends record);

    MyFriends selectIsFriend(@Param("myUserId") String myUserId, @Param("myFriendId") String myFriendId);
}