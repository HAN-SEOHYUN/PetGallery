package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.User.UserMaster;
import com.example.wedlessInvite.domain.User.UserMasterRepository;
import com.example.wedlessInvite.dto.UserMasterResponseDto;
import com.example.wedlessInvite.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.regex.Pattern;

import static com.example.wedlessInvite.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserMasterService {

    private final UserMasterRepository userMasterRepository;

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣]+$");
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public UserMaster register(String name, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);

        validateName(name);
        validatePassword(password);

        UserMaster userMaster = UserMaster.builder()
                .name(name)
                .pwd(encodedPassword)
                .build();

        return userMasterRepository.save(userMaster);
    }

    public UserMaster login(String name, String password) {
        UserMaster user = userMasterRepository.findByName(name)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPwd())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }
        return user;
    }

    public UserMaster findById(Long id) {
        return userMasterRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
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

    public UserMasterResponseDto getAuthenticatedUser(Long userId) {
        if (userId == null) {
            throw new CustomException(LOGIN_REQUIRED);
        }
        Optional<UserMaster> entity = userMasterRepository.findById(userId);

        // 값이 없으면 예외를 발생
        UserMaster user = entity.orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return UserMasterResponseDto.fromEntity(user);
    }

}
