package com.whydigit.fms.service;

import java.util.List;
import java.util.Map;

import com.whydigit.fms.dto.RolesPermissionHeaderDTO;
import com.whydigit.fms.entity.RolesPermissionHeaderVO;
import com.whydigit.fms.exception.ApplicationException;

public interface RolesResponsibilitiesService {

	List<RolesPermissionHeaderVO> getRolesPermissionHeaderByRoleandOrgid(String role, Long orgid);

	Map<String, Object> createUpdateRoleScreenPermission(RolesPermissionHeaderDTO rolesPermissionHeaderDTO) throws ApplicationException;

}
