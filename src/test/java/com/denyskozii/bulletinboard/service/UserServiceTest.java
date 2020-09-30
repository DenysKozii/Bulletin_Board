package com.denyskozii.bulletinboard.service;

import com.denyskozii.bulletinboard.dto.UserDto;
import com.denyskozii.bulletinboard.model.Role;
import com.denyskozii.bulletinboard.model.User;
import com.denyskozii.bulletinboard.repository.BulletinRepository;
import com.denyskozii.bulletinboard.repository.UserRepository;
import com.denyskozii.bulletinboard.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestExecutionListeners;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class UserServiceTest {
    private UserService userService;

    @Mock
    UserRepository userRepository;
    @Mock
    BulletinRepository bulletinRepository;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
        User user = new User(1L, "Denys", "Kozii", "denys.kozii@gmail.com", "123123", "123123", Role.USER, Date.valueOf(LocalDate.now()), new ArrayList<>());
        doReturn(Optional.of(user)).when(userRepository).findById(1L);
        doReturn(user).when(userRepository).findByEmail("denys.kozii@gmail.com");
    }
    @Test
    void test() {
        assertEquals(1, 1);
    }

    @Test
    void updateUser() {
        UserDto user = new UserDto( 1L,"Denys", "Kozii", "denys.kozii@gmail.com",  Role.USER, "123123", "123123");
        UserDto userDto = userService.updateUser(user);
        assertEquals(userDto, user);
    }
    @Test
    void loadUserByUsername() {
        UserDto userDto = new UserDto( 1L,"Denys", "Kozii", "denys.kozii@gmail.com",  Role.USER, "123123", "123123");
        UserDetails userDetails = userService.loadUserByUsername(userDto.getEmail());
        assertEquals(userDto.getId().toString(), userDetails.getUsername());
    }
    @Test
    void register() {
        UserDto userDto = new UserDto( 1L,"Denys", "Kozii", "denys.kozii@gmail.com",  Role.USER, "123123", "123123");
        boolean register = userService.register(userDto);
        assertEquals(false, register);
    }
}
