package com.boardAdmin.users.dto;

import java.util.Date;

public record UserDto(
        int id,
        String userId,
        String password,
        String nickname,
        boolean isAdmin,
        Date createTime,
        boolean isWithDraw,
        Status status,
        Date updateTime
) {
    public UserDto withCreateDateAndPassword(Date updateTime, String password) {
        return new UserDto(id, userId, password, nickname, isAdmin, createTime, isWithDraw, status, updateTime);
    }

    enum Status {
        DEFAULT,
        ADMIN,
        DELETED
    }

}
