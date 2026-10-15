package com.whydigit.fms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.fms.entity.UserLoginClientAccessVO;

public interface UserLoginClientAccessRepo extends JpaRepository<UserLoginClientAccessVO, Long
> {

}
