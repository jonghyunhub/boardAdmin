package com.boardAdmin.users.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record UserDto(
        @NotBlank int id,
        @NotBlank String userId,
        @NotBlank String password,
        @NotBlank String nickname,
        @NotBlank boolean isAdmin,
        Date createTime,
        @NotBlank boolean isWithDraw,
        @NotBlank Status status,
        Date updateTime
) {
    public UserDto withCreateDateAndPassword(Date updateTime, String password) {
        return new UserDto(id, userId, password, nickname, isAdmin, createTime, isWithDraw, status, updateTime);
    }

    public UserDto withPassword(String password) {
        return new UserDto(id, userId, password, nickname, isAdmin, createTime, isWithDraw, status, updateTime);
    }

    public enum Status {
        DEFAULT,
        ADMIN,
        DELETED
    }

}
