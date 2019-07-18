package com.example.admindemo.repository;

import com.example.admindemo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * sfdsd
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findUsersByUserName(String userName);

    Users findUsersById(Long id);
}
