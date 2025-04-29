package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.Invitation.InvitationMasterRepository;
import com.example.wedlessInvite.domain.Like.InvitationLike;
import com.example.wedlessInvite.domain.Like.InvitationLikeRepository;
import com.example.wedlessInvite.domain.User.MasterUserRepository;
import com.example.wedlessInvite.domain.Invitation.*;
import com.example.wedlessInvite.domain.User.MasterUser;
import com.example.wedlessInvite.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.wedlessInvite.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class LikeService {

    private final InvitationLikeRepository likeRepository;
    private final InvitationMasterRepository invitationRepository;
    private final MasterUserRepository userRepository;

    @Transactional
    public void like(Long invitationId, Long userId) {
        InvitationMaster invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        MasterUser user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        boolean exists = likeRepository.existsByInvitationMasterAndMasterUser(invitation, user);
        if (exists) {
            throw new CustomException(ALREADY_LIKED_MESSAGE);
        }

        likeRepository.save(InvitationLike.builder()
                .invitationMaster(invitation)
                .masterUser(user)
                .build());
    }

    @Transactional
    public void unlike(Long invitationId, Long userId) {
        InvitationMaster invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        MasterUser user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        InvitationLike like = likeRepository.findByInvitationMasterAndMasterUser(invitation, user)
                .orElseThrow(() -> new CustomException(NOT_LIKED_YET_MESSAGE));

        likeRepository.delete(like);
    }
}
