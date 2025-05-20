package com.example.wedlessInvite.domain.Like;

import com.example.wedlessInvite.domain.Pet.PetMaster;
import com.example.wedlessInvite.domain.User.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PetLikeRepository extends JpaRepository<PetLike, Long> {
    boolean existsByPetMasterAndUserMaster(PetMaster invitation, UserMaster user);
    Optional<PetLike> findByPetMasterAndUserMaster(PetMaster invitation, UserMaster user);
    int countByPetMasterId(Long invitationId);
    @Query("SELECT i.petMaster, COUNT(i) AS likeCount " +
            "FROM PetLike i " +
            "WHERE i.regTime >= :threeDaysAgo " +
            "GROUP BY i.petMaster " +
            "ORDER BY likeCount DESC")
    List<Object[]> findTopLikedPetMasters(@Param("threeDaysAgo") LocalDateTime threeDaysAgo);
    Optional<PetLike> findByPetMasterIdAndUserMasterId(Long petId, Long userId);
    boolean existsByPetMasterIdAndUserMasterId(Long petMasterId, Long userMasterId);
}
