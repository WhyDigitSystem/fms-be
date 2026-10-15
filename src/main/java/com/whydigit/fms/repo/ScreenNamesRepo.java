package com.whydigit.fms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.fms.entity.ScreenNamesVO;

public interface ScreenNamesRepo extends JpaRepository<ScreenNamesVO, Long> {

	boolean existsByScreenName(String screenName);

	boolean existsByScreenCode(String screenCode);

}
