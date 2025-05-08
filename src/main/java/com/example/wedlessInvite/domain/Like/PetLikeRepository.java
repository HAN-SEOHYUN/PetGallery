package com.example.wedlessInvite.domain.Like;

import com.example.wedlessInvite.domain.Invitation.InvitationMaster;
import com.example.wedlessInvite.domain.User.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetLikeRepository extends JpaRepository<PetLike, Long> {
    boolean existsByInvitationMasterAndUserMaster(InvitationMaster invitation, UserMaster user);
    Optional<PetLike> findByInvitationMasterAndUserMaster(InvitationMaster invitation, UserMaster user);
    int countByInvitationMasterId(Long invitationId);
}









