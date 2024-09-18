package com.boardAdmin.users.ui;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank String id,
        @NotBlank String password
) {
}
