package com.whydigit.fms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.ListOfValuesVO;
@Repository
public interface ListOfValuesRepo extends JpaRepository<ListOfValuesVO, Long> {



	boolean existsByListCodeAndOrgId(String listCode, Long orgId);

	boolean existsByListDescriptionAndOrgId(String listDescription, Long orgId);

	@Query(nativeQuery = true,value = "select l1.value_description from listofvalues l join listofvaluesdetails l1 on l.listofvaluesid=l1.listofvaluesid where \r\n"
			+ "l.org_id=?1 and l.listdescription=?2 \r\n"
			+ " group by l1.valuedescription order by l1.valuedescription asc")
	Set<Object[]> getAllListValues(Long orgId,String listDescription);

	@Query(nativeQuery = true, value = "select * from listofvalues where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<ListOfValuesVO> getListOfValuesByOrgId(Long orgId,Long branchId );
	
	@Query(nativeQuery = true, value = "select * from listofvalues where listofvalues_id=?1")
	ListOfValuesVO getListOfValuesById(Long id);



	@Query(value = "SELECT a.listofvaluesdetails_id,\r\n"
			+ "		           a.value_description\r\n"
			+ "		    FROM listofvaluesdetails a\r\n"
			+ "		    JOIN listofvalues b\r\n"
			+ "		      ON a.listofvalues_id = b.listofvalues_id\r\n"
			+ "		    WHERE b.org_id = ?1\r\n"
			+ "		      AND b.list_description = ?2\r\n"
			+ "		      AND a.active = 1\r\n"
			+ "		    GROUP BY a.listofvaluesdetails_id, a.value_description", nativeQuery = true)
		Set<Object[]> getListValuesDetailsForBudget(Long orgId, String name);


        
}


