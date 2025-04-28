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
@Table(name="MASTER_USER")
public class MasterUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MU_ID")
    private Long id;

    @Column(nullable = false, name="MU_NAME")
    private String name;

    @Column(nullable = false, name="MU_PWD")
    private String pwd;

    @OneToMany(mappedBy = "masterUser")
    private List<InvitationMaster> invitationMasters;

    @Builder
    public MasterUser(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }
}
