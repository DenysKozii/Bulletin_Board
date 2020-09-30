package com.denyskozii.bulletinboard.service.impl;

import com.denyskozii.bulletinboard.dto.BulletinDto;
import com.denyskozii.bulletinboard.model.Bulletin;
import com.denyskozii.bulletinboard.model.User;
import com.denyskozii.bulletinboard.repository.BulletinRepository;
import com.denyskozii.bulletinboard.repository.UserRepository;
import com.denyskozii.bulletinboard.service.BulletinService;
import com.denyskozii.bulletinboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Service
@Transactional
public class BulletinServiceImpl implements BulletinService {

    private final BulletinRepository bulletinRepository;
    private final UserRepository userRepository;
//    @Autowiredred
    private UserService userService;
    private final Validator validator;

    @Autowired
    public BulletinServiceImpl(BulletinRepository bulletinRepository, UserRepository userRepository, UserService userService, Validator validator) {
        this.bulletinRepository = bulletinRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.validator = validator;
    }

    @Override
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Page<BulletinDto> getPage(Pageable pageable) {
//        Page<Bulletin> page = bulletinRepository.getPage(pageable);
//        return new PageImpl<>(page.getContent().stream().map(mapToBulletinDto).collect(Collectors.toList()), pageable, page.getTotalElements());
    return null;
    }

    @Override
    public BulletinDto getBulletinById(Long id) {
        return mapToBulletinDto.apply(bulletinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marathon with id " + id + " doesn't exists!")));
    }

    @Override
    public BulletinDto createOrUpdateBulletin(BulletinDto bulletinDto) throws EntityNotFoundException {
        User user = userRepository.findById(bulletinDto.getAuthor().getId())
                .orElseThrow(()->new EntityNotFoundException(String.format("user with id: %s not found", bulletinDto.getAuthor().getId())));
        Bulletin bulletin = new Bulletin(bulletinDto.getId(),
                bulletinDto.getTitle(),
                bulletinDto.getDescription(),
                bulletinDto.getStartDate(),
                user);
        if (validator.validate(bulletin).size() == 0) {
            bulletinRepository.save(bulletin);
            return mapToBulletinDto.apply(bulletin);
        }
        bulletinRepository.save(bulletin);
        return mapToBulletinDto.apply(bulletin);
    }

    @Override
    public BulletinDto getBulletinByTitle(String title) {
        Bulletin bulletin = bulletinRepository.findByTitle(title);
        return bulletin == null ? null : mapToBulletinDto.apply(bulletin);
    }

    Function<Bulletin, BulletinDto> mapToBulletinDto = (bulletin -> BulletinDto.builder()
            .id(bulletin.getId())
            .title(bulletin.getTitle())
            .description(bulletin.getDescription())
            .startDate(bulletin.getStartDate())
            .author(userService.mapToUserDto.apply(bulletin.getAuthor()))
            .build());
}
