package com.whydigit.fms.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.PortVO;

@Repository
public interface PortRepo extends JpaRepository<PortVO, Long> {

	@Query(value = "SELECT * FROM port " + "WHERE port_code = :portCode " + "LIMIT 1", nativeQuery = true)
	Optional<PortVO> findByPortCode(@Param("portCode") String portCode);

	@Query(value = "SELECT * FROM port " + "WHERE org_id = :orgId " + "AND branch = :branch", nativeQuery = true)
	List<PortVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);
}