package com.example.wedlessInvite.domain.Like;

import com.example.wedlessInvite.domain.Invitation.InvitationMaster;
import com.example.wedlessInvite.domain.User.MasterUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationLikeRepository extends JpaRepository<InvitationLike, Long> {
    boolean existsByInvitationMasterAndMasterUser(InvitationMaster invitation, MasterUser user);
    Optional<InvitationLike> findByInvitationMasterAndMasterUser(InvitationMaster invitation, MasterUser user);
    long countByInvitationMaster(InvitationMaster invitation);
}









