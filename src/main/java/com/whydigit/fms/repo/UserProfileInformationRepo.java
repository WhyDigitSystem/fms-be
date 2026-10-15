package com.whydigit.fms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.UserProfileInformationVO;

@Repository
public interface UserProfileInformationRepo extends JpaRepository<UserProfileInformationVO, Long> {

	@Query(nativeQuery = true, value = "select * from userprofileinformation where userprofileinformationid=?1")
	UserProfileInformationVO getUserProfileInformationById(Long id);

	@Query(nativeQuery = true, value = "select * from userprofileinformation where userid=?1")
	UserProfileInformationVO getProfileInformationByUserId(Long userId);

}
