package com.denyskozii.bulletinboard.service;

import com.denyskozii.bulletinboard.dto.BulletinDto;
import com.denyskozii.bulletinboard.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
public interface BulletinService {
    BulletinDto createOrUpdateBulletin(BulletinDto bulletinDto);

    Page<BulletinDto> getPage(Pageable pageable);

    BulletinDto getBulletinById(Long id) throws EntityNotFoundException;

    BulletinDto getBulletinByTitle(String title);

}
