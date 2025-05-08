package com.example.wedlessInvite.domain.Like;

import com.example.wedlessInvite.domain.Pet.PetMaster;
import com.example.wedlessInvite.domain.User.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetLikeRepository extends JpaRepository<PetLike, Long> {
    boolean existsByPetMasterAndUserMaster(PetMaster invitation, UserMaster user);
    Optional<PetLike> findByPetMasterAndUserMaster(PetMaster invitation, UserMaster user);
    int countByPetMasterId(Long invitationId);
}









