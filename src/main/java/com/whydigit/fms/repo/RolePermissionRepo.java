package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.RolesPermissionHeaderVO;
import com.whydigit.fms.entity.RolesPermissionVO;

@Repository
public interface RolePermissionRepo extends JpaRepository<RolesPermissionVO,Long >{

	List<RolesPermissionVO> findByRolesPermissionHeaderVO(RolesPermissionHeaderVO vo);

}
