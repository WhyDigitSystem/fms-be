package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.fms.entity.UserLoginClientAccessVO;
import com.whydigit.fms.entity.UserVO;

public interface UserClientAccessRepo extends JpaRepository<UserLoginClientAccessVO,Long> {

	List<UserLoginClientAccessVO> findByUserVO(UserVO userVO);

	

//	Set<Object[]> findCustomerByOrgIdAndBranchCode(Long orgid, String branchcode);

//	Set<Object[]> findClientByOrgIdAndCustomer(Long orgid, String customer);

}
