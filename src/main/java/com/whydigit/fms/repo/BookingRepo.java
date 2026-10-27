package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.BookingVO;

@Repository
public interface BookingRepo extends JpaRepository<BookingVO, Long>{

	 @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1  and  screen_code=?2")
	String getBookingDocId(Long orgId, String screenCode);

	@Query(value = "SELECT * FROM booking WHERE org_id = :orgId AND branch = :branchId",
		       nativeQuery = true)
		List<BookingVO> findByOrgIdAndBranch(
		        @Param("orgId") Long orgId,
		        @Param("branchId") Long branch);

}
