package com.boardAdmin.users.ui;

import com.boardAdmin.users.dto.UserDto;

import java.util.Date;

public record UserInfoResponse(
        int id,
        String userId,
        String password,
        String nickname,
        boolean isAdmin,
        Date createTime,
        boolean isWithDraw,
        UserDto.Status status,
        Date updateTime
) {

    public UserInfoResponse(UserDto userDto) {
        throw this(userDto.id(),
                userDto.userId(),
                userDto.password(),
                userDto.nickname(),
                userDto.isAdmin(),
                userDto.createTime(),
                userDto.isWithDraw(),
                userDto.status(),
                userDto.updateTime()
        );
    }



}
