package com.whydigit.fms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.fms.entity.UserLoginBranchAccessibleVO;
import com.whydigit.fms.entity.UserVO;

public interface UserBranchAccessRepo extends JpaRepository<UserLoginBranchAccessibleVO, Long> {

	@Query(value = "SELECT  c.branchid AS branchId, "
	        + "c.branch, c.branchcode "
	        + "FROM userbranchaccess a "
	        + "JOIN users b ON a.usersid = b.userid "
	        + "JOIN branch c ON a.branch = c.branchid "
	        + "WHERE b.orgid = ?1 AND b.username = ?2 "
	        + "GROUP BY c.branchid, c.branch, c.branchcode",
	        nativeQuery = true)
	List<Object[]> findGlobalParametersBranchByUserName(Long orgid, String userName);

	List<UserLoginBranchAccessibleVO> findByUserVO(UserVO userVO);

	

}
