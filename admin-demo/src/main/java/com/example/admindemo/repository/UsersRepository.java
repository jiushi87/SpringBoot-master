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

	@Query(value = "select t.id,t.date_birth,t.email,t.identity,t.iphone,t.password,t.profile_picture,t.real_name,t.signature,t.user_id,t.user_name from users t where t.id=?1",nativeQuery = true)
	Users selectUsers(Integer id);

	@Modifying(clearAutomatically=true)
	@Transactional
    @Query(value = "update Users set profile_picture =?1 WHERE ID=?2",nativeQuery = true)
    int updateProfilePicture(String profilePicture,Integer id);
}
