package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.ConfigProductVO;
import com.whydigit.fms.entity.ConfigVO;

@Repository
public interface ConfigProductRepo extends JpaRepository<ConfigProductVO, Long> {

	List<ConfigProductVO> findByConfig_Id(Long configId);
	
	List<ConfigProductVO> findByConfig(ConfigVO config);

	List<ConfigProductVO> findByProduct_Id(Long productId);

	List<ConfigProductVO> findByConfig_IdAndProduct_Id(Long configId, Long productId);
}