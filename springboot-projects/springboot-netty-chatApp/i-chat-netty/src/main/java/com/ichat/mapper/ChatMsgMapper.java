package com.ichat.mapper;

import com.ichat.pojo.ChatMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatMsgMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChatMsg record);

    ChatMsg selectByPrimaryKey(String id);

    List<ChatMsg> selectAll();

    int updateByPrimaryKey(ChatMsg record);

    List<ChatMsg> selectByAccAndFlag(@Param("accept") String accept, @Param("flag") int flag);
}