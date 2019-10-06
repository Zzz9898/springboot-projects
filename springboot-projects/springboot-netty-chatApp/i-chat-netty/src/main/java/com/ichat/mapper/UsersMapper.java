package com.ichat.mapper;

import com.ichat.pojo.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersMapper {
    int deleteByPrimaryKey(String id);

    int insert(Users record);

    Users selectByPrimaryKey(String id);

    List<Users> selectAll();

    int updateByPrimaryKey(Users record);

    Users userLogin(@Param("username") String username, @Param("password") String password);

    Users selectUserByUsername(String username);

    void updateFaceByKey(@Param("userId") String userId, @Param("fileName") String fileName, @Param("fileName_min") String fileName_min);

    void updateNickname(@Param("userId") String userId, @Param("nickname") String nickname);
}