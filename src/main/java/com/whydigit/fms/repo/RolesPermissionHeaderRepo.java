package com.whydigit.fms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.fms.entity.RolesPermissionHeaderVO;

@Repository
public interface RolesPermissionHeaderRepo extends JpaRepository<RolesPermissionHeaderVO, Long>{

	List<RolesPermissionHeaderVO> getRolesPermissionHeaderByRoleAndOrgId(String role, Long orgId);

}
