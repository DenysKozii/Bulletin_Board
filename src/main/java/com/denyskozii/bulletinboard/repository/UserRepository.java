package com.denyskozii.bulletinboard.repository;


import com.denyskozii.bulletinboard.model.Role;
import com.denyskozii.bulletinboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(@Email(message = "Wrong email format") String email);

    List<User> getAllByRole(Role role);

    User findByFirstNameAndLastName(String firstName, String lastName);

    User findByEmailAndFirstNameAndLastName(String email, String firstName, String lastName);

}
