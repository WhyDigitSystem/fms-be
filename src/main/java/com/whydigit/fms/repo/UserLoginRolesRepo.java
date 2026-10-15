package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.fms.entity.UserLoginRolesVO;
import com.whydigit.fms.entity.UserVO;

public interface UserLoginRolesRepo extends JpaRepository<UserLoginRolesVO, Long> {

	List<UserLoginRolesVO> findByUserVO(UserVO userVO);

}
