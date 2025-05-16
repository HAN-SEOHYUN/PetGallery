package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.domain.User.UserMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMasterResponseDto {
    private Long id;
    private String name;

    public static UserMasterResponseDto fromEntity(UserMaster user) {
        return new UserMasterResponseDto(
                user.getId(),
                user.getName()
        );
    }
}

