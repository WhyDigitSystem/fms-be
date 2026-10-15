package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.EnquiryVO;

@Repository
public interface EnquiryRepo extends JpaRepository<EnquiryVO, Long>{

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and  screen_code=?2")
	String getEnquiryDocId(Long orgId, String screenCode);

	@Query("SELECT e FROM EnquiryVO e WHERE e.orgId = :orgId AND e.branch = :branch")
	List<EnquiryVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") String branch);

}
