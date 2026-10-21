package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.whydigit.fms.entity.QuotationVO;

@Service
public interface QuotationRepo extends JpaRepository<QuotationVO, Long>{

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and  screen_code=?2")
	String getQuotationDocId(Long orgId, String screenCode);

	@Query(value = "SELECT * FROM quotation WHERE org_id = :orgId "
	        + "AND branch = :branch "
	        + "AND active = 1 AND cancel = 0",
	        nativeQuery = true)
	List<QuotationVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch
	);

}
