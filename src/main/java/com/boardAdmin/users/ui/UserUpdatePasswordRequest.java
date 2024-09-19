package com.boardAdmin.users.ui;

public record UserUpdatePasswordRequest(String beforePassword, String afterPassword) {
}
