package com.example.wedlessInvite.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterUserRepository extends JpaRepository<UserMaster, Long> {
    boolean existsByName(String name);
}
