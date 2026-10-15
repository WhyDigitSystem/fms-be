package com.whydigit.fms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.TokenVO;

@Repository
public interface TokenRepo extends JpaRepository<TokenVO, String>{

	
}
