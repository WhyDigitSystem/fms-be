package com.whydigit.fms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.whydigit.fms.dto.ChangePasswordFormDTO;
import com.whydigit.fms.dto.LoginFormDTO;
import com.whydigit.fms.dto.RefreshTokenDTO;
import com.whydigit.fms.dto.ResetPasswordFormDTO;
import com.whydigit.fms.dto.ResponsibilityDTO;
import com.whydigit.fms.dto.RolesDTO;
import com.whydigit.fms.dto.SignUpFormDTO;
import com.whydigit.fms.dto.UserResponseDTO;
import com.whydigit.fms.entity.ResponsibilityVO;
import com.whydigit.fms.entity.RolesVO;
import com.whydigit.fms.entity.UserVO;
import com.whydigit.fms.exception.ApplicationException;

@Service
public interface AuthService {

	public void signup(SignUpFormDTO signUpRequest);

	public UserResponseDTO login(LoginFormDTO loginRequest,HttpServletRequest httpRequest) throws ApplicationException;

	public void logout(String userName);

	public void changePassword(ChangePasswordFormDTO changePasswordRequest);

	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest);

	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException;
	
	List<Map<String, Object>> getResponsibilityForRolesByOrgId(Long orgId);
	
	Map<String, Object> createUpdateRoles(RolesDTO rolesDTO) throws ApplicationException;
	
	public List<RolesVO> getAllRoles(Long orgId);

	public List<RolesVO> getAllActiveRoles(Long orgId);
	
	RolesVO getRolesById(Long id) throws ApplicationException;
	
	Map<String, Object> createUpdateResponsibilities(ResponsibilityDTO responsibilityDTO) throws ApplicationException;
	
	public List<ResponsibilityVO> getAllResponsibility(Long orgId);

	public List<ResponsibilityVO> getAllActiveResponsibility(Long orgId);
	
	ResponsibilityVO getResponsibilityById(Long id) throws ApplicationException;
	
	List<UserVO>getAllUsersByOrgId(Long orgId);
	
	public UserVO getUserById(Long userId);

	public UserVO getUserByUserName(String userName);

	

}
