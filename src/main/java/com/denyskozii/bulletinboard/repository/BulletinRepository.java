package com.denyskozii.bulletinboard.repository;

import com.denyskozii.bulletinboard.model.Bulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Long> {

    Bulletin findByTitle(String title);
}
