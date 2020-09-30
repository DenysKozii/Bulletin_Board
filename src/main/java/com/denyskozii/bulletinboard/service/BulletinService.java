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
    /**
     * Create new bulletin, or update existing with new data
     * @param bulletinDto
     * @return <code>bulletinDto</code> bulletin
     */
    BulletinDto createOrUpdateBulletin(BulletinDto bulletinDto);

    /**
     * Return page with information about all bulletins
     * @param pagesRequest
     * @return <code>pagesRequest</code> bulletin
     */
    Page<Bulletin> getPage(PageRequest pagesRequest);
}
