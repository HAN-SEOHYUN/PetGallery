package com.example.wedlessInvite.domain.User;

import com.example.wedlessInvite.domain.BaseEntity;
import com.example.wedlessInvite.domain.Invitation.InvitationMaster;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="USER_MASTER")
public class UserMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UM_ID")
    private Long id;

    @Column(nullable = false, name="UM_NAME")
    private String name;

    @Column(nullable = false, name="UM_PWD")
    private String pwd;

    @OneToMany(mappedBy = "userMaster")
    private List<InvitationMaster> invitationMasters;

    @Builder
    public UserMaster(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }
}
