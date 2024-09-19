package com.boardAdmin.users.ui;

import com.boardAdmin.common.ui.ResponseContainer;
import com.boardAdmin.common.utils.SessionUtil;
import com.boardAdmin.users.dto.UserDto;
import com.boardAdmin.users.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("sign-up")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api 요청 성공"),
            @ApiResponse(responseCode = "400", description = "회원 가입 요청 바디 필수 값 누락"),
            @ApiResponse(responseCode = "400", description = "유저 비밀번호 일치하지 않음"),
            @ApiResponse(responseCode = "409", description = "아이디 중복"),
            @ApiResponse(responseCode = "500", description = "유저 정보 insert 실패"),
    })
    public ResponseEntity<ResponseContainer<Void>> signUp(@RequestBody @Valid UserDto userDto) {
        userService.register(userDto);
        return ResponseEntity.ok(
                ResponseContainer.success()
        );
    }

    @PostMapping("sign-in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api 요청 성공"),
            @ApiResponse(responseCode = "400", description = "회원 가입 요청 바디 필수 값 누락"),
            @ApiResponse(responseCode = "404", description = "유저 정보가 없음")
    })
    public ResponseEntity<ResponseContainer<Void>> signIn(@RequestBody @Valid SignInRequest request,
                                                          HttpSession httpSession) {
        userService.login(request.id(), request.password(), httpSession);
        return ResponseEntity.ok(
                ResponseContainer.success()
        );
    }

    @PutMapping("my-info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api 요청 성공"),
            @ApiResponse(responseCode = "404", description = "유저 정보가 없음")
    })
    public ResponseEntity<ResponseContainer<UserInfoResponse>> memberInfo(HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);
        UserDto userInfo = userService.getUserInfo(id);
        return ResponseEntity.ok(
                ResponseContainer.success(
                        new UserInfoResponse(userInfo)
                )
        );
    }

    @PutMapping("logout")
    public ResponseEntity<ResponseContainer<Void>> logout(HttpSession httpSession) {
        SessionUtil.clear(httpSession);
        return ResponseEntity.ok(
                ResponseContainer.success()
        );
    }

    @PatchMapping("password")
    public ResponseEntity<ResponseContainer<UserInfoResponse>> updateUserPassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
                                                                                  HttpSession httpSession) {
        UserDto userDto = userService.updatePassword(SessionUtil.getLoginMemberId(httpSession),
                userUpdatePasswordRequest.beforePassword(),
                userUpdatePasswordRequest.afterPassword());

        return ResponseEntity.ok(
                ResponseContainer.success(
                        new UserInfoResponse(userDto)
                )
        );
    }


    @DeleteMapping
    public ResponseEntity<ResponseContainer<UserInfoResponse>> deleteId(@RequestBody UserDeleteRequest userDeleteRequest,
                                                                        HttpSession httpSession) {
        UserDto userDto = userService.deleteId(
                SessionUtil.getLoginMemberId(httpSession),
                userDeleteRequest.password()
        );
        return ResponseEntity.ok(
                ResponseContainer.success(
                        new UserInfoResponse(userDto)
                )
        );

    }


}
