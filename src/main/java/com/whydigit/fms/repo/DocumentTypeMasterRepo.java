package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.whydigit.fms.entity.DocumentTypeMasterVO;

public interface DocumentTypeMasterRepo extends JpaRepository<DocumentTypeMasterVO, Long> {

	@Query(value = "SELECT *\r\n"
			+ "			FROM documenttypemaster\r\n"
			+ "			WHERE org_id = :orgId\r\n"
			+ "			  AND branch = :branch\r\n"
			+ "			  AND cancel = false\r\n"
			+ "			  AND active = true\r\n"
			+ "			ORDER BY name", nativeQuery = true)
	List<DocumentTypeMasterVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);

//	boolean existsByOrgIdAndCodeIgnoreCase(Long orgId, String code);

	boolean existsByOrgIdAndScreenCode(Long orgId, String screenCode);

	boolean existsByOrgIdAndDocCode(Long orgId, String docCode);

	List<DocumentTypeMasterVO> findAllByOrgId(Long orgId);

}
