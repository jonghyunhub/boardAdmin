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
    enum Status {
        DEFAULT,
        ADMIN,
        DELETED
    }

    public UserDto withCreateDate(Date createTime) {
        return new UserDto(id, userId, password, nickname, isAdmin, createTime, isWithDraw, status, updateTime);
    }
}
