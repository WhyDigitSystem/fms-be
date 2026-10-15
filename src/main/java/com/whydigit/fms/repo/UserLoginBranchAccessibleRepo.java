package com.whydigit.fms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.fms.entity.UserLoginBranchAccessibleVO;

public interface UserLoginBranchAccessibleRepo extends JpaRepository<UserLoginBranchAccessibleVO, Long> {

}
