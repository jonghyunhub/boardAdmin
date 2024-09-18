package com.boardAdmin.users.service;

import com.boardAdmin.common.utils.SHA256Util;
import com.boardAdmin.exception.DomainException;
import com.boardAdmin.exception.DuplicateIdException;
import com.boardAdmin.mapper.UserProfileMapper;
import com.boardAdmin.users.dto.UserDto;
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
            throw new DuplicateIdException("중복된 아이디 입니다.");
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
                    "insertUser Error! + 회원가입 메서드 확인\n" + "Params : " + withCreateDate
            );
        }

    }

    private boolean isDuplicated(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }


    public UserDto login(String id, String password) {
        String encryptedPassword = SHA256Util.encryptSHA256(password);
        return userProfileMapper.findByIdAndPassword(id, encryptedPassword);
    }

    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String encryptedPassword = SHA256Util.encryptSHA256(beforePassword);
        UserDto memberInfo = userProfileMapper.findByIdAndPassword(id, encryptedPassword);

        if (memberInfo == null) {
            log.error("update password Error {}", memberInfo);
            throw new DomainException("비밀번호가 일치하지 않습니다.");
        }

        UserDto updatedUserDto = memberInfo.withPassword(SHA256Util.encryptSHA256(afterPassword));
        userProfileMapper.updatePassword(updatedUserDto);
    }

    public void deleteId(String id, String password) {
        String encryptedPassword = SHA256Util.encryptSHA256(password);
        UserDto memberInfo = userProfileMapper.findByIdAndPassword(id, encryptedPassword);

        if (memberInfo == null) {
            log.error("update password Error {}", memberInfo);
            throw new DomainException("비밀번호가 일치하지 않습니다.");
        }

        userProfileMapper.deleteUserProfile(id);
    }


}
