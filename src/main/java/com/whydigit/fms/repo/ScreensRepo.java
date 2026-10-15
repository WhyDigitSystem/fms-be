package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.fms.entity.ResponsibilityVO;
import com.whydigit.fms.entity.ScreensVO;

public interface ScreensRepo extends JpaRepository<ScreensVO, Long>{

	List<ScreensVO> findByResponsibilityVO(ResponsibilityVO responsibilityVO);

}
