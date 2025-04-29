package com.example.wedlessInvite.domain.Like;

import com.example.wedlessInvite.domain.BaseEntity;
import com.example.wedlessInvite.domain.Invitation.InvitationMaster;
import com.example.wedlessInvite.domain.User.MasterUser;
import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "INVITATION_LIKE")
public class InvitationLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IL_MU_ID", nullable = false)
    private MasterUser masterUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IL_IM_ID", nullable = false)
    private InvitationMaster invitationMaster;

    @Builder
    public InvitationLike(MasterUser masterUser, InvitationMaster invitationMaster) {
        this.masterUser = masterUser;
        this.invitationMaster = invitationMaster;
    }

    protected InvitationLike() {}
}

