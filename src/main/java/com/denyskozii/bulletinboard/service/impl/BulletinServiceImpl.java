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
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;
import java.util.Optional;
import java.util.function.Function;

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
    @Autowired
    private Validator validator;
    @Autowired
    private UserService userService;

    @Autowired
    public BulletinServiceImpl(BulletinRepository bulletinRepository, UserRepository userRepository) {
        this.bulletinRepository = bulletinRepository;
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public BulletinDto createOrUpdateBulletin(BulletinDto bulletinDto) throws EntityNotFoundException {
        User user = userRepository.findById(bulletinDto.getAuthor().getId())
                .orElseThrow(()->new EntityNotFoundException(String.format("user with id: %s not found", bulletinDto.getAuthor().getId())));
        Optional<Bulletin> bulletinOptional = bulletinRepository.findById(bulletinDto.getId());

        if(bulletinOptional.isEmpty()){
            Bulletin bulletin = new Bulletin(bulletinDto.getId(),
                    bulletinDto.getTitle(),
                    bulletinDto.getDescription(),
                    bulletinDto.getStartDate(),
                    bulletinDto.getImage(),
                    user);
            if (validator.validate(bulletin).size() == 0) {
                bulletinRepository.save(bulletin);
                return mapToBulletinDto.apply(bulletin);
            }
        }
        bulletinRepository.save(bulletinOptional.get());
        return mapToBulletinDto.apply(bulletinOptional.get());
    }


    @Override
    public Page<Bulletin> getPage(PageRequest pagesRequest) {
        return bulletinRepository.findAll(pagesRequest);
    }

    Function<Bulletin, BulletinDto> mapToBulletinDto = (bulletin -> BulletinDto.builder()
            .id(bulletin.getId())
            .title(bulletin.getTitle())
            .description(bulletin.getDescription())
            .startDate(bulletin.getStartDate())
            .author(userService.mapToUserDto.apply(bulletin.getAuthor()))
            .build());
}
