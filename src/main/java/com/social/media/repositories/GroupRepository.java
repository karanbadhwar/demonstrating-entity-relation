package com.social.media.repositories;

import com.social.media.models.SocialGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<SocialGroup, Long> {
}
