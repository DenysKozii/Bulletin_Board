package com.denyskozii.bulletinboard.repository;


import com.denyskozii.bulletinboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Searching user by his email
     * @param email
     * @return <code>email</code> user
     */
    User findByEmail(@Email(message = "Wrong email format") String email);

}
