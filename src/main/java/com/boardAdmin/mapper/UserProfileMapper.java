package com.boardAdmin.mapper;

import com.boardAdmin.users.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {
    UserDto getUserProfile(@Param("id") String id);

    int insertUserProfile(@Param("id") String id, @Param("password") String password,
                          @Param("name") String name, @Param("phone") String phone,
                          @Param("address") String address, @Param("createTime") String createTime,
                          @Param("updateTime") String updateTime);

    int deleteUserProfile(@Param("id") String id);

    UserDto findByIdAndPassword(@Param("id") String id, @Param("password") String password);

    int register(UserDto userDto);

    int idCheck(String id);

    public int updatePassword(UserDto userDto);

    public int updateAddress(UserDto userDto);
}
