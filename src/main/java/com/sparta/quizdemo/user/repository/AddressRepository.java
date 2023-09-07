package com.sparta.quizdemo.user.repository;

import com.sparta.quizdemo.user.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByUser_id(Long id);

}
