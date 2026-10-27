package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.ConfigModeVO;
import com.whydigit.fms.entity.ConfigVO;

@Repository
public interface ConfigModeRepo
        extends JpaRepository<ConfigModeVO, Long> {

    List<ConfigModeVO> findByConfig_Id(Long configId);
    
    List<ConfigModeVO> findByConfig(ConfigVO config);
    
    List<ConfigModeVO> findByMode(String mode);
}