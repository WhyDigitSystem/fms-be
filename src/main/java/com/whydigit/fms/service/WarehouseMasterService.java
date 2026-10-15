package com.whydigit.fms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.whydigit.fms.dto.BranchDTO;
import com.whydigit.fms.dto.DocumentTypeDTO;
import com.whydigit.fms.dto.DocumentTypeMappingDTO;
import com.whydigit.fms.dto.EmployeeDTO;
import com.whydigit.fms.entity.BranchVO;
import com.whydigit.fms.entity.DocumentTypeMappingVO;
import com.whydigit.fms.entity.DocumentTypeVO;
import com.whydigit.fms.entity.EmployeeVO;
import com.whydigit.fms.exception.ApplicationException;

@Service
public interface WarehouseMasterService {

	
	
	// Branch

	List<BranchVO> getAllBranch(Long orgid);

	Optional<BranchVO> getBranchById(Long branchid);

	Map<String, Object> createUpdateBranch(BranchDTO branchDTO) throws Exception;

	
	void deleteBranch(Long branchid);

	// employee
	
	List<EmployeeVO> getAllEmployee();

	List<EmployeeVO> getAllEmployeeByOrgId(Long orgId);

	Optional<EmployeeVO> getEmployeeById(Long employeeid);

	Map<String, Object> createEmployee(EmployeeDTO employeeDTO) throws ApplicationException;

	void deleteEmployee(Long employeeid);

	// UserLogin

	Set<Object[]> getAllNameAndEmployeeCodeByOrgId(Long orgid);

//	Set<Object[]> getAllCustomerAndClientByOrgId(Long orgid);


	// Create Pallet No
//	Set<Object[]> getPalletnoByRownoAndLevelAndStartAndEnd(String rowno, String level, int startno, int endno);
	
	
	
	// Document Type 
	
	Map<String, Object> createUpdateDocumentType(DocumentTypeDTO documentTypeDTO)throws ApplicationException;
	
	DocumentTypeVO getDocumentTypeById(Long id) throws ApplicationException;
	
	List<DocumentTypeVO>getAllDocumentTypeByOrgId(Long orgId);
	
	
	List<Map<String, Object>>getPendingDocumentTypeMapping(Long orgId,String branch,String branchCode,String finYear,String finYearIdentifier,String clientCode);
	
	Map<String, Object> createDocumentTypeMapping(DocumentTypeMappingDTO documentTypeMappingDTO)throws ApplicationException;
	
	List<DocumentTypeMappingVO> getAllDocumentTypeMapping(Long orgId);
	
	DocumentTypeMappingVO getDocumentTypeMappingById(Long id)throws ApplicationException;

	
	
	
	

}
