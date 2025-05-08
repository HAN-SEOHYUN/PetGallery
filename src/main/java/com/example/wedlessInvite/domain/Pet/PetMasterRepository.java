package com.example.wedlessInvite.domain.Pet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetMasterRepository extends JpaRepository<PetMaster, Long> {

    Page<PetMaster> findByDeleteYNOrderByRegTimeDesc(String deleteYN, Pageable pageable);
    Optional<PetMaster> findByAccessKey(String accessKey);
}
