package com.example.wedlessInvite.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {
    boolean existsByName(String name);
}
