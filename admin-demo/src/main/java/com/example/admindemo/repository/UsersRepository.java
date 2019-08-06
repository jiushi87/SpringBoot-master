package com.example.admindemo.repository;

import com.example.admindemo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * sfdsd
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findUsersByUserName(String userName);

    Users findUsersById(Integer id);

	@Modifying(clearAutomatically=true)
	@Transactional
    @Query(value = "update Users set profile_picture =?1 WHERE ID=?2",nativeQuery = true)
    int updateProfilePicture(String savePath,Integer id);
}
