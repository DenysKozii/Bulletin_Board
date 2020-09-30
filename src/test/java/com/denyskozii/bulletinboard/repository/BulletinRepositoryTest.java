//package com.denyskozii.bulletinboard.repository;
//
//
//import com.denyskozii.bulletinboard.model.Bulletin;
//import com.denyskozii.bulletinboard.model.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@DataJpaTest
//@ActiveProfiles("test")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class BulletinRepositoryTest {
//    @Autowired
//    BulletinRepository bulletinRepository;
//
//    private List<Bulletin> loadDb() {
//        User user = new User();
//        user.setId(1L);
//        user.setLastName("Kozii");
//        user.setFirstName("Denys");
//        user.setEmail("de.kozii@gmail.com");
//        user.setPassword("123123");
//
//        Bulletin bulletin1 = new Bulletin();
//        bulletin1.setTitle("First");
//        bulletin1.setDescription("First");
//        bulletin1.setStartDate(LocalDate.now());
//        bulletin1.setAuthor(user);
//
//        bulletinRepository.save(bulletin1);
//
//        Bulletin bulletin2 = new Bulletin();
//        bulletin1.setTitle("Second");
//        bulletin1.setDescription("Second");
//        bulletin1.setStartDate(LocalDate.now());
//        bulletin1.setAuthor(user);
//
//        bulletinRepository.save(bulletin2);
//
//
//        Bulletin bulletin3 = new Bulletin();
//        bulletin1.setTitle("Third");
//        bulletin1.setDescription("Third");
//        bulletin1.setStartDate(LocalDate.now());
//        bulletin1.setAuthor(user);
//
//        bulletinRepository.save(bulletin3);
//
//        return new ArrayList<>() {{
//            add(bulletin1);
//            add(bulletin2);
//            add(bulletin3);
//        }};
//    }
//
//    @Test
//    public void testAdd() {
//        User user = new User();
//        user.setId(1L);
//        user.setLastName("Kozii");
//        user.setFirstName("Denys");
//        user.setEmail("de.kozii@gmail.com");
//        user.setPassword("123123");
//
//        Bulletin expected = new Bulletin();
//        expected.setTitle("Expected");
//        expected.setDescription("Expected");
//        expected.setStartDate(LocalDate.now());
//        expected.setAuthor(user);
//        bulletinRepository.save(expected);
//
//        Bulletin actual = bulletinRepository.findById(1L).orElse(null);
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testFindAll() {
//        List<Bulletin> expected = loadDb();
//        List<Bulletin> actual = bulletinRepository.findAll();
//
//        Assertions.assertEquals(expected, actual);
//    }
//}
