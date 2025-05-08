package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.Pet.PetMasterRepository;
import com.example.wedlessInvite.domain.Like.PetLike;
import com.example.wedlessInvite.domain.Like.PetLikeRepository;
import com.example.wedlessInvite.domain.User.UserMasterRepository;
import com.example.wedlessInvite.domain.Pet.*;
import com.example.wedlessInvite.domain.User.UserMaster;
import com.example.wedlessInvite.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.wedlessInvite.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class LikeService {

    private final PetLikeRepository likeRepository;
    private final PetMasterRepository invitationRepository;
    private final UserMasterRepository userRepository;

    @Transactional
    public void like(Long invitationId, Long userId) {
        PetMaster invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        UserMaster user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        boolean exists = likeRepository.existsByPetMasterAndUserMaster(invitation, user);
        if (exists) {
            throw new CustomException(ALREADY_LIKED_MESSAGE);
        }

        likeRepository.save(PetLike.builder()
                .petMaster(invitation)
                .userMaster(user)
                .build());
    }

    @Transactional
    public void unlike(Long invitationId, Long userId) {
        PetMaster invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        UserMaster user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        PetLike like = likeRepository.findByPetMasterAndUserMaster(invitation, user)
                .orElseThrow(() -> new CustomException(NOT_LIKED_YET_MESSAGE));

        likeRepository.delete(like);
    }
}
