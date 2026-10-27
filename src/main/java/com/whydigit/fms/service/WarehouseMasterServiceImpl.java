package com.whydigit.fms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.fms.ResponseDTO.BranchResponseDTO;
import com.whydigit.fms.ResponseDTO.DepartmentResponseDTO;
import com.whydigit.fms.ResponseDTO.DesignationResponseDTO;
import com.whydigit.fms.ResponseDTO.EmployeeResponseDTO;
import com.whydigit.fms.dto.BranchDTO;
import com.whydigit.fms.dto.DocumentTypeMasterDTO;
import com.whydigit.fms.dto.DocumentTypeMappingDTO;
import com.whydigit.fms.dto.DocumentTypeMappingDetailsDTO;
import com.whydigit.fms.dto.EmployeeDTO;
import com.whydigit.fms.entity.BranchVO;
import com.whydigit.fms.entity.DepartmentVO;
import com.whydigit.fms.entity.DesignationVO;
import com.whydigit.fms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.fms.entity.DocumentTypeMappingVO;
import com.whydigit.fms.entity.DocumentTypeMasterVO;
import com.whydigit.fms.entity.EmployeeVO;
import com.whydigit.fms.entity.FinancialYearVO;
import com.whydigit.fms.exception.ApplicationException;
import com.whydigit.fms.repo.BranchRepo;
import com.whydigit.fms.repo.DepartmentRepo;
import com.whydigit.fms.repo.DesignationRepo;
import com.whydigit.fms.repo.DocumentTypeDetailsRepo;
import com.whydigit.fms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.fms.repo.DocumentTypeMappingRepo;
import com.whydigit.fms.repo.DocumentTypeMasterRepo;
import com.whydigit.fms.repo.EmployeeRepo;
import com.whydigit.fms.repo.FinancialYearRepo;
import com.whydigit.fms.repo.NotificationRepo;

@Service
public class WarehouseMasterServiceImpl implements WarehouseMasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(WarehouseMasterServiceImpl.class);

	
	@Autowired
	BranchRepo branchRepo;

	
	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	DocumentTypeMasterRepo documentTypeMasterRepo;

	@Autowired
	DocumentTypeDetailsRepo documentTypeDetailsRepo;

	@Autowired
	DocumentTypeMappingRepo documentTypeMappingRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	
	@Autowired
	FinancialYearRepo financialYearRepo;

	@Autowired
	NotificationRepo notificationRepo;

	
   @Autowired
   DesignationRepo designationRepo;
   
   @Autowired
   DepartmentRepo departmentRepo;
	
	// Branch

	@Override
	public List<BranchVO> getAllBranch(Long orgid) {
		return branchRepo.findAll(orgid);
	}

	@Override
	public Optional<BranchVO> getBranchById(Long branchid) {

		return branchRepo.findById(branchid);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateBranch(BranchDTO branchDTO) throws Exception {
		BranchVO branchVO;
		String message = null;

		if (ObjectUtils.isEmpty(branchDTO.getId())) {
			// Check if the branch already exists for creation
			if (branchRepo.existsByBranchAndOrgId(branchDTO.getBranch(), branchDTO.getOrgId())) {
				String errorMessage = String.format("This Branch: %s Already Exists in This Organization",
						branchDTO.getBranch());
				throw new ApplicationException(errorMessage);
			}

			if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
				String errorMessage = String.format("This BranchCode: %s Already Exists in This Organization",
						branchDTO.getBranchCode());
				throw new ApplicationException(errorMessage);
			}

			// Create new branch
			branchVO = new BranchVO();
			branchVO.setCreatedBy(branchDTO.getCreatedBy());
			branchVO.setUpdatedBy(branchDTO.getCreatedBy());
			message = "Branch Created Successfully";
		} else {
			// Update existing branch
			branchVO = branchRepo.findById(branchDTO.getId())
					.orElseThrow(() -> new ApplicationException("Branch not found with id: " + branchDTO.getId()));

			branchVO.setUpdatedBy(branchDTO.getCreatedBy());

			if (!branchVO.getBranch().equalsIgnoreCase(branchDTO.getBranch())) {
				if (branchRepo.existsByBranchAndOrgId(branchDTO.getBranch(), branchDTO.getOrgId())) {
					String errorMessage = String.format("This Branch: %s Already Exists in This Organization",
							branchDTO.getBranch());
					throw new ApplicationException(errorMessage);
				}
				branchVO.setBranch(branchDTO.getBranch().toUpperCase());
			}

			if (!branchVO.getBranchCode().equalsIgnoreCase(branchDTO.getBranchCode())) {
				if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
					String errorMessage = String.format("This BranchCode: %s Already Exists in This Organization",
							branchDTO.getBranchCode());
					throw new ApplicationException(errorMessage);
				}
				branchVO.setBranchCode(branchDTO.getBranchCode().toUpperCase());
			}

			message = "Branch Updated Successfully";
		}

		getBranchVOFromBranchDTO(branchVO, branchDTO);
		branchRepo.save(branchVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("branchVO", branchVO);
		return response;
	}

	private void getBranchVOFromBranchDTO(BranchVO branchVO, BranchDTO branchDTO) {
		branchVO.setBranch(branchDTO.getBranch().toUpperCase());
		branchVO.setBranchCode(branchDTO.getBranchCode().toUpperCase());
		branchVO.setOrgId(branchDTO.getOrgId());
		branchVO.setAddressLine1(branchDTO.getAddressLine1());
		branchVO.setAddressLine2(branchDTO.getAddressLine2());
		branchVO.setPan(branchDTO.getPan());
		branchVO.setGstIn(branchDTO.getGstIn());
		branchVO.setPhone(branchDTO.getPhone());
		branchVO.setState(branchDTO.getState().toUpperCase());
		branchVO.setCity(branchDTO.getCity().toUpperCase());
		branchVO.setPinCode(branchDTO.getPinCode());
		branchVO.setCountry(branchDTO.getCountry().toUpperCase());
		branchVO.setStateNo(branchDTO.getStateNo().toUpperCase());
		branchVO.setStateCode(branchDTO.getStateCode().toUpperCase());
		branchVO.setLccurrency(branchDTO.getLccurrency());
		branchVO.setCancelRemarks(branchDTO.getCancelRemarks());
		branchVO.setActive(branchDTO.isActive());
	}

	@Override
	public void deleteBranch(Long branchid) {
		branchRepo.deleteById(branchid);
	}

	
	// Employee

	@Override
	public List<EmployeeVO> getAllEmployeeByOrgId(Long orgId) {
		return employeeRepo.findAllEmployeeByOrgId(orgId);
	}

	@Override
	public List<EmployeeVO> getAllEmployee() {
		return employeeRepo.findAll();
	}

	@Override
	public Optional<EmployeeVO> getEmployeeById(Long employeeid) {
		return employeeRepo.findById(employeeid);
	}

	@Override
	@Transactional
	public Map<String, Object> createEmployee(EmployeeDTO employeeDTO)
	        throws ApplicationException {

	    EmployeeVO employeeVO;
	    String message;

	    if (ObjectUtils.isEmpty(employeeDTO.getId())) {

	        if (employeeRepo.existsByEmployeeCodeAndOrgId(
	                employeeDTO.getEmployeeCode(), employeeDTO.getOrgId())) {

	            throw new ApplicationException(String.format(
	                    "This EmployeeCode: %s Already Exists in This Organization",
	                    employeeDTO.getEmployeeCode()));
	        }

	        employeeVO = new EmployeeVO();
	        employeeVO.setCreatedBy(employeeDTO.getCreatedBy());
	        employeeVO.setUpdatedBy(employeeDTO.getCreatedBy());

	        message = "Employee Creation Successfully";

	    } else {

	        employeeVO = employeeRepo.findById(employeeDTO.getId())
	                .orElseThrow(() -> new ApplicationException(
	                        "ID is Not Found Any Information: "
	                                + employeeDTO.getId()));

	        if (!employeeVO.getEmployeeCode().equalsIgnoreCase(
	                employeeDTO.getEmployeeCode())
	                && employeeRepo.existsByEmployeeCodeAndOrgId(
	                        employeeDTO.getEmployeeCode(),
	                        employeeDTO.getOrgId())) {

	            throw new ApplicationException(String.format(
	                    "This EmployeeCode: %s Already Exists in This Organization",
	                    employeeDTO.getEmployeeCode()));
	        }

	        employeeVO.setUpdatedBy(employeeDTO.getCreatedBy());

	        message = "Employee Update Successfully";
	    }

	    getEmployeeVOFromEmployeeDTO(employeeVO, employeeDTO);

	    EmployeeVO savedEmployee = employeeRepo.save(employeeVO);

	    EmployeeResponseDTO employeeResponseDTO =
	            getEmployeeResponseDTO(savedEmployee);

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", message);
	    response.put("employeeVO", employeeResponseDTO);

	    return response;
	}
	
	private void getEmployeeVOFromEmployeeDTO(
	        EmployeeVO employeeVO,
	        EmployeeDTO employeeDTO) throws ApplicationException {

	    employeeVO.setEmployeeCode(employeeDTO.getEmployeeCode());
	    employeeVO.setEmployeeName(employeeDTO.getEmployeeName());
	    employeeVO.setGender(employeeDTO.getGender());

	    // Branch mapping
	    if (employeeDTO.getBranch() != null) {
	        BranchVO branchVO = branchRepo.findById(employeeDTO.getBranch())
	                .orElseThrow(() -> new ApplicationException(
	                        "Branch ID not found: " + employeeDTO.getBranch()));

	        employeeVO.setBranch(branchVO);
	    } else {
	        employeeVO.setBranch(null);
	    }

	    // Department mapping
	    if (employeeDTO.getDepartment() != null) {
	        DepartmentVO departmentVO =
	                departmentRepo.findById(employeeDTO.getDepartment())
	                        .orElseThrow(() -> new ApplicationException(
	                                "Department ID not found: "
	                                        + employeeDTO.getDepartment()));

	        employeeVO.setDepartment(departmentVO);
	    } else {
	        employeeVO.setDepartment(null);
	    }

	    // Designation mapping
	    if (employeeDTO.getDesignation() != null) {
	        DesignationVO designationVO =
	                designationRepo.findById(employeeDTO.getDesignation())
	                        .orElseThrow(() -> new ApplicationException(
	                                "Designation ID not found: "
	                                        + employeeDTO.getDesignation()));

	        employeeVO.setDesignation(designationVO);
	    } else {
	        employeeVO.setDesignation(null);
	    }

	    // Remaining fields
	    employeeVO.setDateOfBirth(employeeDTO.getDateOfBirth());
	    employeeVO.setJoiningDate(employeeDTO.getJoiningdate());
	    employeeVO.setOrgId(employeeDTO.getOrgId());
	    employeeVO.setCancel(employeeDTO.isCancel());
	    employeeVO.setActive(employeeDTO.isActive());
	    employeeVO.setCancelRemark(employeeDTO.getCancelRemark());
	}
	
	private EmployeeResponseDTO getEmployeeResponseDTO(EmployeeVO employeeVO) {

	    EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();

	    responseDTO.setId(employeeVO.getId());
	    responseDTO.setEmployeeCode(employeeVO.getEmployeeCode());
	    responseDTO.setEmployeeName(employeeVO.getEmployeeName());
	    responseDTO.setGender(employeeVO.getGender());

	    // Branch response
	    if (employeeVO.getBranch() != null) {
	        BranchResponseDTO branchDTO = new BranchResponseDTO();
	        branchDTO.setId(employeeVO.getBranch().getId());
	        branchDTO.setBranch(employeeVO.getBranch().getBranch());
	        branchDTO.setBranchCode(employeeVO.getBranch().getBranchCode());

	        responseDTO.setBranch(branchDTO);
	    }

	    // Department response
	    if (employeeVO.getDepartment() != null) {
	        DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();
	        departmentDTO.setId(employeeVO.getDepartment().getId());
	        departmentDTO.setDepartment(
	                employeeVO.getDepartment().getDepartment());
	        departmentDTO.setCode(employeeVO.getDepartment().getCode());

	        responseDTO.setDepartment(departmentDTO);
	    }

	    // Designation response
	    if (employeeVO.getDesignation() != null) {
	        DesignationResponseDTO designationDTO = new DesignationResponseDTO();
	        designationDTO.setId(employeeVO.getDesignation().getId());
	        designationDTO.setDesignation(
	                employeeVO.getDesignation().getDesignation());
	        designationDTO.setCode(employeeVO.getDesignation().getCode());

	        responseDTO.setDesignation(designationDTO);
	    }

	    responseDTO.setDateOfBirth(employeeVO.getDateOfBirth());
	    responseDTO.setJoiningdate(employeeVO.getJoiningDate());
	    responseDTO.setCreatedBy(employeeVO.getCreatedBy());
	    responseDTO.setUpdatedBy(employeeVO.getUpdatedBy());
	    responseDTO.setOrgId(employeeVO.getOrgId());
	    responseDTO.setCancel(employeeVO.getCancel());
	    responseDTO.setCancelRemark(employeeVO.getCancelRemark());
	    responseDTO.setActive(employeeVO.getActive());

	    return responseDTO;
	}
	
	

	@Override
	public void deleteEmployee(Long employeeid) {
		employeeRepo.deleteById(employeeid);
	}

	// UserLogin

	@Override
	public Set<Object[]> getAllNameAndEmployeeCodeByOrgId(Long orgid) {
		return employeeRepo.findAllNameAndEmployeeCodeByOrgId(orgid);
	}

//	@Override
//	public List<Map<String, Object>> getPendingDocumentTypeMapping(Long orgId, String branch, String branchCode,
//			String finYear, String finYearIdentifier) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public Set<Object[]> getAllCustomerAndClientByOrgId(Long orgid) {
//		return customerRepo.findAllCustomerAndClientByOrgId(orgid);
//	}
//
//	@Override
//	public Set<Object[]> getAllBranchCodeAndBranchByOrgId(String client, Long orgid) {
//		return clientBranchRepo.findAllBranchCodeAndBranchByOrgId(client, orgid);
//	}

	// find All Warehouse By Branch
	


//	@Override
//	public Map<String, Object> createDocumentTypeMapping(DocumentTypeMappingDTO documentTypeMappingDTO)
//			throws ApplicationException {
//		String message;
//		DocumentTypeMappingVO documentTypeMappingVO = new DocumentTypeMappingVO();
//		documentTypeMappingVO.setBranch(documentTypeMappingDTO.getBranch());
//		documentTypeMappingVO.setBranchCode(documentTypeMappingDTO.getBranchCode());
//		documentTypeMappingVO.setFinYear(documentTypeMappingDTO.getFinYear());
//		documentTypeMappingVO.setFinYearIdentifier(documentTypeMappingDTO.getFinYearIdentifier());
//		documentTypeMappingVO.setOrgId(documentTypeMappingDTO.getOrgId());
//		documentTypeMappingVO.setCreatedBy(documentTypeMappingDTO.getCreatedBy());
//		documentTypeMappingVO.setUpdatedBy(documentTypeMappingDTO.getCreatedBy());
//
//		List<DocumentTypeMappingDetailsVO> documentTypeMappingDetailsVO = new ArrayList<>();
//
//		if (documentTypeMappingDTO.getDocumentTypeMappingDetailsDTO() != null) {
//			for (DocumentTypeMappingDetailsDTO documentTypeMappingDetailsDTO : documentTypeMappingDTO
//					.getDocumentTypeMappingDetailsDTO()) {
//				DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO1 = new DocumentTypeMappingDetailsVO();
//				documentTypeMappingDetailsVO1.setScreenCode(documentTypeMappingDetailsDTO.getScreenCode());
//				documentTypeMappingDetailsVO1.setScreenName(documentTypeMappingDetailsDTO.getScreenName());
//				documentTypeMappingDetailsVO1.setDocCode(documentTypeMappingDetailsDTO.getDocCode());
//				documentTypeMappingDetailsVO1.setBranch(documentTypeMappingDetailsDTO.getBranch());
//				documentTypeMappingDetailsVO1.setBranchCode(documentTypeMappingDetailsDTO.getBranchCode());
//				documentTypeMappingDetailsVO1.setPrefixField(documentTypeMappingDetailsDTO.getPrefixField());
//				documentTypeMappingDetailsVO1.setFinYear(documentTypeMappingDetailsDTO.getFinYear());
//				documentTypeMappingDetailsVO1
//						.setFinYearIdentifier(documentTypeMappingDetailsDTO.getFinYearIdentifier());
//				documentTypeMappingDetailsVO1.setConcatenation(
//						documentTypeMappingDetailsDTO.getScreenCode() + documentTypeMappingDetailsDTO.getDocCode());
//				documentTypeMappingDetailsVO1.setOrgId(documentTypeMappingDTO.getOrgId());
//				documentTypeMappingDetailsVO1.setClient(documentTypeMappingDetailsDTO.getClient());
//				documentTypeMappingDetailsVO1.setClientCode(documentTypeMappingDetailsDTO.getClientCode());
//				documentTypeMappingDetailsVO1.setDocumentTypeMappingVO(documentTypeMappingVO);
//				documentTypeMappingDetailsVO.add(documentTypeMappingDetailsVO1);
//			}
//		}
//		documentTypeMappingVO.setDocumentTypeMappingDetailsVO(documentTypeMappingDetailsVO);
//		documentTypeMappingRepo.save(documentTypeMappingVO);
//		message = "Document Type created Successfully";
//		Map<String, Object> response = new HashMap<>();
//		response.put("documentTypeMappingVO", documentTypeMappingVO);
//		response.put("message", message);
//		return response;
//
//	}
//
//	@Override
//	public List<Map<String, Object>> getPendingDocumentTypeMapping(Long orgId, String branch, String branchCode,
//			String finYear, String finYearIdentifier) {
//		int finyear = Integer.parseInt(finYear.toString());
//		FinancialYearVO financialYearVO = financialYearRepo.findByOrgIdAndFinYear(orgId, finyear);
//		String finYearIden = financialYearVO.getFinYearId().toString();
//		Set<Object[]> pendingDocTypeDetails = documentTypeMappingRepo.getPendingDoctypeMapping(orgId, branch,
//				branchCode, finYear, finYearIden);
//		return getPendingDocType(pendingDocTypeDetails);
//	}
//
//	private List<Map<String, Object>> getPendingDocType(Set<Object[]> pendingDocTypeDetails) {
//		List<Map<String, Object>> doctypeMappingDetails = new ArrayList<>();
//		for (Object[] sup : pendingDocTypeDetails) {
//			Map<String, Object> doctype = new HashMap<>();
//			doctype.put("screenName", sup[0] != null ? sup[0].toString() : "");
//			doctype.put("screenCode", sup[1] != null ? sup[1].toString() : "");
//			doctype.put("docCode", sup[2] != null ? sup[2].toString() : "");
//			doctype.put("finYear", sup[3] != null ? sup[3].toString() : "");
//			doctype.put("branch", sup[4] != null ? sup[4].toString() : "");
//			doctype.put("branchCode", sup[5] != null ? sup[5].toString() : "");
//			doctype.put("finYearIdentifier", sup[6] != null ? sup[6].toString() : "");
//			doctype.put("prefixField", sup[7] != null ? sup[7].toString() : "");
//			doctypeMappingDetails.add(doctype);
//		}
//
//		return doctypeMappingDetails;
//	}
//
//	@Override
//	public List<DocumentTypeMappingVO> getAllDocumentTypeMapping(Long orgId) {
//
//		return documentTypeMappingRepo.findByOrgId(orgId);
//	}
//
//	@Override
//	public DocumentTypeMappingVO getDocumentTypeMappingById(Long id) throws ApplicationException {
//		if (ObjectUtils.isEmpty(id)) {
//			throw new ApplicationException("Invalid DocumentTypeMapping Id");
//		}
//		DocumentTypeMappingVO documentTypeMappingVO = documentTypeMappingRepo.findById(id)
//				.orElseThrow(() -> new ApplicationException("Document Type Mapping not found for Id: " + id));
//
//		return documentTypeMappingVO;
//	}


	

}
