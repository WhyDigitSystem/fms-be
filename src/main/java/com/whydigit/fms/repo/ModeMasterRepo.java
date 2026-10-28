package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.ModeMasterVO;

@Repository
public interface ModeMasterRepo extends JpaRepository<ModeMasterVO, Long>{

	boolean existsByModeNameAndOrgId(String modeName, Long orgId);

	boolean existsByModeCodeAndOrgId(String modeCode, Long orgId);

	@Query(value = "SELECT * FROM mode_master WHERE org_id = :orgId and cancel=0", nativeQuery = true)
	List<ModeMasterVO> findModeMasterByOrgId(@Param("orgId") Long orgId);

}
