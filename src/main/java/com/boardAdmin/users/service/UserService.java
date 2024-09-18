package com.boardAdmin.users.service;

import com.boardAdmin.exception.DuplicateIdException;
import com.boardAdmin.users.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    public void register(UserDto userDto) {
        boolean duplicatedIdResult = isDuplicated(userDto.userId());
        if (duplicatedIdResult) {
            throw new DuplicateIdException("중복된 아이디 입니다.");
        }

        final UserDto withCreateDate = userDto.withCreateDate(new Date());

    }

    private boolean isDuplicated(String id) {
        return false;
    }
}
