package com.boardAdmin.users.service;

import com.boardAdmin.common.exception.DomainException;
import com.boardAdmin.common.exception.ErrorCode;
import com.boardAdmin.common.utils.SHA256Util;
import com.boardAdmin.common.utils.SessionUtil;
import com.boardAdmin.mapper.UserProfileMapper;
import com.boardAdmin.users.dto.UserDto;
import com.boardAdmin.users.exception.DuplicateIdException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserProfileMapper userProfileMapper;

    public void register(UserDto userDto) {
        boolean duplicatedIdResult = isDuplicated(userDto.userId());
        if (duplicatedIdResult) {
            throw new DuplicateIdException(
                    ErrorCode.DUPLICATE_ID_ERROR,
                    ErrorCode.DUPLICATE_ID_ERROR.getMessage()
            );
        }

        // [toDo] : 서비스에서 비밀번호 암호화, 생성 시간 처리는 책임의 적절하지 못하므로 -> 컨트롤러, 인터셉터 단에서 처리
        final UserDto withCreateDate = userDto.withCreateDateAndPassword(
                new Date(),
                SHA256Util.encryptSHA256(userDto.password())
        );

        final int insertCount = userProfileMapper.register(withCreateDate);
        if (insertCount != 1) {
            log.error("insertMember ERROR : {}", withCreateDate);
            throw new DomainException(
                    ErrorCode.USER_INSERT_QUERY_ERROR, ErrorCode.USER_INSERT_QUERY_ERROR.getMessage()
            );
        }

    }

    private boolean isDuplicated(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }


    // [todo] : 회원 조회 로직 및 예외처리 별도 reader 클래스로 위임
    public void login(String id, String password, HttpSession httpSession) {
        String encryptedPassword = SHA256Util.encryptSHA256(password);
        UserDto memberInfo = userProfileMapper.findByIdAndPassword(id, encryptedPassword);

        if (memberInfo == null) {
            log.error("login Error {}", memberInfo);
            throw new DomainException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage());
        }

        if (memberInfo.isAdmin()) {
            SessionUtil.setLoginAdminId(httpSession, memberInfo.userId());
            return;
        }

        SessionUtil.setLoginMemberId(httpSession, memberInfo.userId());
    }

    public UserDto updatePassword(String id, String beforePassword, String afterPassword) {
        String encryptedPassword = SHA256Util.encryptSHA256(beforePassword);
        UserDto memberInfo = userProfileMapper.findByIdAndPassword(id, encryptedPassword);

        if (memberInfo == null) {
            log.error("update password Error {}", memberInfo);
            throw new DomainException(ErrorCode.USER_PASSWORD_NOT_MATCH, ErrorCode.USER_PASSWORD_NOT_MATCH.getMessage());
        }

        UserDto updatedUserDto = memberInfo.withPassword(SHA256Util.encryptSHA256(afterPassword));
        userProfileMapper.updatePassword(updatedUserDto);
        return updatedUserDto;
    }

    public UserDto deleteId(String id, String password) {
        String encryptedPassword = SHA256Util.encryptSHA256(password);
        UserDto memberInfo = userProfileMapper.findByIdAndPassword(id, encryptedPassword);

        if (memberInfo == null) {
            log.error("update password Error {}", memberInfo);
            throw new DomainException(ErrorCode.USER_PASSWORD_NOT_MATCH, ErrorCode.USER_PASSWORD_NOT_MATCH.getMessage());
        }

        userProfileMapper.deleteUserProfile(id);
        return memberInfo;
    }


    public UserDto getUserInfo(String id) {
        UserDto userProfile = userProfileMapper.getUserProfile(id);
        if (userProfile == null) {
            throw new DomainException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage());
        }
        return userProfile;
    }
}
