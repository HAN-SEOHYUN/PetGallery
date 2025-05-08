package com.example.wedlessInvite.domain.Like;

import com.example.wedlessInvite.domain.BaseEntity;
import com.example.wedlessInvite.domain.Pet.PetMaster;
import com.example.wedlessInvite.domain.User.UserMaster;
import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "PET_LIKE")
public class PetLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PL_MU_ID", nullable = false)
    private UserMaster userMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PL_IM_ID", nullable = false)
    private PetMaster petMaster;

    @Builder
    public PetLike(UserMaster userMaster, PetMaster petMaster) {
        this.userMaster = userMaster;
        this.petMaster = petMaster;
    }

    protected PetLike() {}
}

