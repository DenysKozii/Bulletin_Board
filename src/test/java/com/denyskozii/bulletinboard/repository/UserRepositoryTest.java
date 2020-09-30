package com.denyskozii.bulletinboard.repository;

import com.denyskozii.bulletinboard.model.Role;
import com.denyskozii.bulletinboard.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private List<User> loadDb() {
        User user1 = new User();
        user1.setRole(Role.USER);
        user1.setEmail("first@gmail.com");
        user1.setFirstName("first");
        user1.setLastName("first");
        user1.setPassword("first");
        user1.setBulletins(new ArrayList<>());
        userRepository.save(user1);

        User user2 = new User();
        user2.setRole(Role.USER);
        user2.setEmail("second@gmail.com2");
        user2.setFirstName("second");
        user2.setLastName("second");
        user2.setPassword("second");
        userRepository.save(user2);

        User user3 = new User();
        user3.setRole(Role.USER);
        user3.setEmail("third@gmail.com3");
        user3.setFirstName("third");
        user3.setLastName("third");
        user3.setPassword("third");
        userRepository.save(user3);

        User user4 = new User();
        user4.setRole(Role.ADMIN);
        user4.setEmail("fourth@gmail.com4");
        user4.setFirstName("fourth");
        user4.setLastName("fourth");
        user4.setPassword("fourth");
        userRepository.save(user4);

        return new ArrayList<>() {{
            add(user1);
            add(user2);
            add(user3);
            add(user4);
        }};
    }

    @Test
    public void testFindByEmail() {
        User expected = loadDb().get(0);
        User actual = userRepository.findByEmail("first@gmail.com");

        Assertions.assertEquals(expected, actual);
    }
}
