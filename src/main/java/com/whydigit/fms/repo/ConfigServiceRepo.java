package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.ConfigServiceVO;
import com.whydigit.fms.entity.ConfigVO;

@Repository
public interface ConfigServiceRepo extends JpaRepository<ConfigServiceVO, Long> {

	List<ConfigServiceVO> findByConfig_Id(Long configId);
	
	List<ConfigServiceVO> findByConfig(ConfigVO config);

	List<ConfigServiceVO> findByService_Id(Long serviceId);

	List<ConfigServiceVO> findByConfig_IdAndService_Id(Long configId, Long serviceId);
}