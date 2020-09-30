package com.denyskozii.bulletinboard.service;

import com.denyskozii.bulletinboard.dto.BulletinDto;
import com.denyskozii.bulletinboard.model.Bulletin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
public interface BulletinService {
    BulletinDto createOrUpdateBulletin(BulletinDto bulletinDto);

    Page<Bulletin> getPage(PageRequest pagesRequest);
}
