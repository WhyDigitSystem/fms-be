package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.ConfigVO;

@Repository
public interface ConfigRepo extends JpaRepository<ConfigVO, Long> {

	@Query(value = "SELECT * FROM config " + "WHERE org_id = :orgId " + "AND branch = :branch", nativeQuery = true)
	List<ConfigVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);
}