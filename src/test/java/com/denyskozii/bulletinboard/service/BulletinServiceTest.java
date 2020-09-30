package com.denyskozii.bulletinboard.service;

import com.denyskozii.bulletinboard.dto.BulletinDto;
import com.denyskozii.bulletinboard.dto.UserDto;
import com.denyskozii.bulletinboard.model.Bulletin;
import com.denyskozii.bulletinboard.model.User;
import com.denyskozii.bulletinboard.repository.BulletinRepository;
import com.denyskozii.bulletinboard.repository.UserRepository;
import com.denyskozii.bulletinboard.service.impl.BulletinServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestExecutionListeners;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class BulletinServiceTest {

    private BulletinService bulletinService;

    @Mock
    BulletinRepository bulletinRepository;
    @Mock
    UserRepository userRepository;

    private User user;
    private UserDto userdto;

    @BeforeEach
    public void setUp() {

        user = new User();
        user.setId(1L);
        user.setLastName("Kozii");
        user.setFirstName("Denys");
        user.setEmail("de.kozii@gmail.com");
        user.setPassword("123123");

        userdto = new UserDto();
        userdto.setId(1L);
        userdto.setLastName("Kozii");
        userdto.setFirstName("Denys");
        userdto.setEmail("de.kozii@gmail.com");
        userdto.setPassword("123123");

        PageRequest pageRequest = PageRequest.of(1,2, Sort.Direction.DESC,"id");

        bulletinService = new BulletinServiceImpl(bulletinRepository, userRepository);
        Bulletin bulletin = new Bulletin(1L, "First","Desc", LocalDate.now(), null, user);
        bulletin.setId(1L);

        doReturn(Optional.of(user)).when(userRepository).findById(1L);
        doReturn(bulletin).when(bulletinRepository).findByTitle("First");

        doReturn(Collections.singletonList(bulletin)).when(bulletinRepository).findAll();
        doReturn(new Page<Bulletin>() {
            @Override
            public Iterator<Bulletin> iterator() {
                return null;
            }

            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 1;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 1;
            }

            @Override
            public int getNumberOfElements() {
                return 1;
            }

            @Override
            public List<Bulletin> getContent() {
                return Arrays.asList(bulletin);
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public <U> Page<U> map(Function<? super Bulletin, ? extends U> function) {
                return null;
            }
        }).when(bulletinRepository).findAll(pageRequest);
    }

    @Test
    void test() {
        assertEquals(1, 1);
    }

    @Test
    public void CreateOrUpdateBulletin() {
        BulletinDto bulletinDto = new BulletinDto(1L, "First","Desc",LocalDate.now(), userdto, null);
        BulletinDto actual = bulletinService.createOrUpdateBulletin(bulletinDto);

        assertEquals(1, bulletinRepository.findAll().size());
        assertEquals(actual.getTitle(), bulletinDto.getTitle());
    }

    @Test
    public void getPage() {
        PageRequest pageRequest = PageRequest.of(1,2, Sort.Direction.DESC,"id");
        Page<Bulletin> actual = bulletinService.getPage(pageRequest);

        assertEquals("First", actual.getContent().get(0).getTitle());
        assertEquals(1, actual.getTotalElements());
    }
}
