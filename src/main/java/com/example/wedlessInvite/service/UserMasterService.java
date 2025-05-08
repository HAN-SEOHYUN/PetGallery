package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.User.UserMaster;
import com.example.wedlessInvite.domain.User.UserMasterRepository;
import com.example.wedlessInvite.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

import static com.example.wedlessInvite.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMasterRepository userMasterRepository;

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣]+$");

    @Transactional
    public UserMaster register(String name, String password) {
        validateName(name);
        validatePassword(password);

        UserMaster userMaster = UserMaster.builder()
                .name(name)
                .pwd(password)
                .build();

        return userMasterRepository.save(userMaster);
    }

    private void validateName(String name) {
        if (!NICKNAME_PATTERN.matcher(name).matches()) {
            throw new CustomException(INVALID_NICKNAME_PATTERN);
        }
        if (userMasterRepository.existsByName(name)) {
            throw new CustomException(DUPLICATED_NICKNAME);
        }
    }

    private void validatePassword(String password) {
        if (password.length() < 4 || password.length() > 8) {
            throw new CustomException(INVALID_PASSWORD_LENGTH);
        }
    }
}
