package com.example.wedlessInvite.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {
    boolean existsByName(String name);
    Optional<UserMaster> findByName(String name);
}
