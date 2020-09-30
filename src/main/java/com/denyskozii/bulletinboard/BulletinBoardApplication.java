package com.denyskozii.bulletinboard;

import com.denyskozii.bulletinboard.model.Bulletin;
import com.denyskozii.bulletinboard.model.User;
import com.denyskozii.bulletinboard.repository.BulletinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@SpringBootApplication
public class BulletinBoardApplication implements CommandLineRunner {

    BulletinRepository bulletinRepository;

    @Autowired
    public BulletinBoardApplication(BulletinRepository bulletinRepository) {
        this.bulletinRepository = bulletinRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(BulletinBoardApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setId(1L);
        user.setLastName("Kozii");
        user.setFirstName("Denys");
        user.setEmail("de.kozii@gmail.com");
        user.setPassword("123123");

        for (int i = 0; i < 34; i++) {
            bulletinRepository.save(new Bulletin(Long.valueOf(i),
                    "Bulletin #" + i,
                    "Description for bulletin #" + i,
                    LocalDate.now(),
                    null,
                    user));
        }
    }
}
