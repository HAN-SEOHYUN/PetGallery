package com.example.wedlessInvite.domain.Invitation;

import com.example.wedlessInvite.common.YN;
import com.example.wedlessInvite.common.logtrace.LogTrace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationMasterRepository extends JpaRepository<InvitationMaster, Long> {

    Page<InvitationMaster> findByDeleteYNOrderByRegTimeDesc(String deleteYN, Pageable pageable);
    Optional<InvitationMaster> findByAccessKey(String accessKey);
}
