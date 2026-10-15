package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.fms.entity.DocumentTypeVO;

public interface DocumentTypeRepo extends JpaRepository<DocumentTypeVO, Long> {

	boolean existsByOrgIdAndScreenCode(Long orgId, String screenCode);

	boolean existsByOrgIdAndDocCode(Long orgId, String docCode);

	@Query(value = "select a from DocumentTypeVO a where a.orgId=?1")
	List<DocumentTypeVO> findAllByOrgId(Long orgId);

}
