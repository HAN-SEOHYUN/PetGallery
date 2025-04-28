package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.User.MasterUser;
import com.example.wedlessInvite.domain.User.MasterUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MasterUserRepository masterUserRepository;

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣]+$");

    @Transactional
    public void register(String name, String password) {
        validateName(name);
        validatePassword(password);

        MasterUser masterUser = MasterUser.builder()
                .name(name)
                .pwd(password)
                .build();

        masterUserRepository.save(masterUser);
    }

    private void validateName(String name) {
        if (!NICKNAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("닉네임은 영어 대소문자와 한글만 사용할 수 있습니다.");
        }
        if (masterUserRepository.existsByName(name)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < 4 || password.length() > 8) {
            throw new IllegalArgumentException("비밀번호는 4~8자 이내여야 합니다.");
        }
    }
}
