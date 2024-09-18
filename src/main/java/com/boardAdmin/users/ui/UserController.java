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
            @ApiResponse(responseCode = "404", description = "유튜버 데이터가 존재하지 않음"),
            @ApiResponse(responseCode = "500", description = "서버 오류"),
    })
    public ResponseEntity<ResponseContainer<Void>> signUp(@RequestBody @Valid UserDto userDto) {
        userService.register(userDto);
        return ResponseEntity.ok(
                ResponseContainer.success()
        );
    }

}
