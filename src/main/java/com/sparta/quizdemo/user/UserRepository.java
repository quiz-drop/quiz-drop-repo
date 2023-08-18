
package com.sparta.quizdemo.user;

import com.sparta.quizdemo.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
// to DO
//    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);

    Optional<User> findUserById(Long id);
}
