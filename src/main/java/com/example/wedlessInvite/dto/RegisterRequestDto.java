package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.domain.User.UserMaster;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {
    String name;
    String password;

    public UserMaster toEntity() {
        return UserMaster.builder()
                .name(name)
                .pwd(password)
                .build();
    }
}
