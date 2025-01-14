package com.example.wedlessInvite.domain.Image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageUploadsRepository extends JpaRepository<ImageUploads, Long> {
    ImageUploads findImageUploadsById(Long imageId);
}
