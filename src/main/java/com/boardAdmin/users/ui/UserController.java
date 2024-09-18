package com.boardAdmin.users.ui;

import com.boardAdmin.common.ui.ResponseContainer;
import com.boardAdmin.users.dto.UserDto;
import com.boardAdmin.users.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("sign-up")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api 요청 성공"),
            @ApiResponse(responseCode = "400", description = "유저 비밀번호 일치하지 않음"),
            @ApiResponse(responseCode = "500", description = "유저 정보 insert 실패"),
    })
    public ResponseEntity<ResponseContainer<Void>> signUp(@RequestBody @Valid UserDto userDto) {
        userService.register(userDto);
        return ResponseEntity.ok(
                ResponseContainer.success()
        );
    }

}
