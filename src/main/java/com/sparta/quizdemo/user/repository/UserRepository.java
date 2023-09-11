
package com.sparta.quizdemo.user.repository;

import com.sparta.quizdemo.chat.entity.ChatRoom;
import com.sparta.quizdemo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);

    Optional<User> findUserById(Long id);

    Optional<User> findBySocialIdAndSocial(String naverId, String social);


    Optional<User> findByEmail(String email);
}
