package com.whydigit.fms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.fms.common.CommonConstant;
import com.whydigit.fms.common.UserConstants;
import com.whydigit.fms.dto.BranchDTO;
import com.whydigit.fms.dto.DocumentTypeDTO;
import com.whydigit.fms.dto.DocumentTypeMappingDTO;
import com.whydigit.fms.dto.EmployeeDTO;
import com.whydigit.fms.dto.ResponseDTO;
import com.whydigit.fms.entity.BranchVO;
import com.whydigit.fms.entity.DocumentTypeMappingVO;
import com.whydigit.fms.entity.DocumentTypeVO;
import com.whydigit.fms.entity.EmployeeVO;
import com.whydigit.fms.service.WarehouseMasterService;

@RequestMapping("/api/warehousemastercontroller")
@RestController
public class WarehouseMasterController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(WarehouseMasterController.class);

	@Autowired
	WarehouseMasterService warehouseMasterService;

	// Group

	
	// Branch
	@GetMapping("/branch")
	public ResponseEntity<ResponseDTO> getAllBranch(@RequestParam Long orgid) {
		String methodName = "getAllBranch()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BranchVO> branchVO = new ArrayList<>();
		try {
			branchVO = warehouseMasterService.getAllBranch(orgid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch information get successfully");
			responseObjectsMap.put("branchVO", branchVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Branch information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/branch/{branchid}")
	public ResponseEntity<ResponseDTO> getBranchById(@PathVariable Long branchid) {
		String methodName = "getBranchById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		BranchVO branchVO = null;
		try {
			branchVO = warehouseMasterService.getBranchById(branchid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch found by ID");
			responseObjectsMap.put("Branch", branchVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Branch not found for ID: " + branchid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Branch not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateBranch")
	public ResponseEntity<ResponseDTO> createUpdateBranch(@RequestBody BranchDTO branchDTO) {
		String methodName = "createBranch()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdBranchVO = warehouseMasterService.createUpdateBranch(branchDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdBranchVO.get("message"));
			responseObjectsMap.put("branchVO", createdBranchVO.get("branchVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	// Employee

	@GetMapping("/getAllEmployeeByOrgId")
	public ResponseEntity<ResponseDTO> getAllEmployeeByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllEmployeeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EmployeeVO> employeeVO = new ArrayList<>();
		try {
			employeeVO = warehouseMasterService.getAllEmployeeByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee information get successfully");
			responseObjectsMap.put("employeeVO", employeeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Employee information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllEmployee")
	public ResponseEntity<ResponseDTO> getAllEmployee() {
		String methodName = "getAllEmployeeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EmployeeVO> employeeVO = new ArrayList<>();
		try {
			employeeVO = warehouseMasterService.getAllEmployee();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee information get successfully");
			responseObjectsMap.put("employeeVO", employeeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Employee information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/employee/{employeeid}")
	public ResponseEntity<ResponseDTO> getEmployeeById(@PathVariable Long employeeid) {
		String methodName = "getEmployeeById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		EmployeeVO employeeVO = null;
		try {
			employeeVO = warehouseMasterService.getEmployeeById(employeeid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee found by ID");
			responseObjectsMap.put("Employee", employeeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Employee not found for ID: " + employeeid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Employee not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateEmployee")
	public ResponseEntity<ResponseDTO> createUpdateEmployee(@RequestBody EmployeeDTO employeeDTO) {
		String methodName = "createEmployee()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdEmployeeVO = warehouseMasterService.createEmployee(employeeDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdEmployeeVO.get("message"));
			responseObjectsMap.put("employeeVO", createdEmployeeVO.get("employeeVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//getAllNameAndEmployeeCodeByCompany

	@GetMapping("/getAllNameAndEmployeeCodeByCompany")
	public ResponseEntity<ResponseDTO> getAllNameAndEmployeeCodeByCompany(@RequestParam Long orgid) {

		String methodName = "getAllNameAndEmployeeCodeByCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> employeeVO = new HashSet<>();
		try {
			employeeVO = warehouseMasterService.getAllNameAndEmployeeCodeByOrgId(orgid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> formattedEmployees = formatEmployees(employeeVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "employee Founded");
			responseObjectsMap.put("Employee", formattedEmployees);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "employee not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "employee not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formatEmployees(Set<Object[]> employeeVO) {
		List<Map<String, String>> formattedEmployees = new ArrayList<>();
		for (Object[] employee : employeeVO) {
			Map<String, String> formattedEmployee = new HashMap<>();
			formattedEmployee.put("EmployeeCode", employee[0].toString());
			formattedEmployee.put("EmployeeName", employee[1].toString());
			formattedEmployees.add(formattedEmployee);
		}
		return formattedEmployees;
	}

	// findAllCustomerAndClientByCompany

//	@GetMapping("/getAllCustomerAndClientByCompany")
//	public ResponseEntity<ResponseDTO> getAllCustomerAndClientByCompany(@RequestParam Long orgid) {
//
//		String methodName = "getAllCustomerAndClientByCompany()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		Set<Object[]> customerVO = new HashSet<>();
//		try {
//			customerVO = warehouseMasterService.getAllCustomerAndClientByOrgId(orgid);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isEmpty(errorMsg)) {
//			List<Map<String, String>> formattedCustomers = formatCustomers(customerVO);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "customer Founded");
//			responseObjectsMap.put("Customer", formattedCustomers);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			errorMsg = "customer not found";
//			responseDTO = createServiceResponseError(responseObjectsMap, "customer not found", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}
//
//	private List<Map<String, String>> formatCustomers(Set<Object[]> customerVO) {
//		List<Map<String, String>> formattedCustomers = new ArrayList<>();
//		for (Object[] customer : customerVO) {
//			Map<String, String> formattedCustomer = new HashMap<>();
//			formattedCustomer.put("Customer", customer[0].toString());
//			formattedCustomer.put("Client", customer[1].toString());
//			formattedCustomers.add(formattedCustomer);
//		}
//		return formattedCustomers;
//	}
//
//	// getAllBranchCodeAndBranchByCompany
//
//	@GetMapping("/getAllBranchCodeAndBranchByCompany")
//	public ResponseEntity<ResponseDTO> getAllBranchCodeAndBranchByCompany(@RequestParam String client,
//			@RequestParam Long orgid) {
//
//		String methodName = "getAllBranchCodeAndBranchByCompany()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		Set<Object[]> branchVO = new HashSet<>();
//		try {
//			branchVO = warehouseMasterService.getAllBranchCodeAndBranchByOrgId(client, orgid);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isEmpty(errorMsg)) {
//			List<Map<String, String>> formattedBranches = formatBranches(branchVO);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch Founded");
//			responseObjectsMap.put("Branch", formattedBranches);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			errorMsg = "branch not found";
//			responseDTO = createServiceResponseError(responseObjectsMap, "branch not found", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}
//
//	private List<Map<String, String>> formatBranches(Set<Object[]> branchVO) {
//		List<Map<String, String>> formattedBranches = new ArrayList<>();
//		for (Object[] branch : branchVO) {
//			Map<String, String> formattedBranch = new HashMap<>();
//			formattedBranch.put("BranchCode", branch[0].toString());
//			formattedBranches.add(formattedBranch);
//		}
//		return formattedBranches;
//	}
//
//	

	// Document type

	@PutMapping("/createUpdateDocumentType")
	public ResponseEntity<ResponseDTO> createUpdateDocumentType(@RequestBody DocumentTypeDTO documentTypeDTO) {
		String methodName = "createUpdateDocumentType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> documentType1 = warehouseMasterService.createUpdateDocumentType(documentTypeDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, documentType1.get("message"));
			responseObjectsMap.put("documentTypeVO", documentType1.get("documentTypeVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/documentTypeById")
	public ResponseEntity<ResponseDTO> getdocumentTypeById(@RequestParam Long id) {
		String methodName = "getdocumentTypeById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DocumentTypeVO documentTypeVO = new DocumentTypeVO();
		try {
			documentTypeVO = warehouseMasterService.getDocumentTypeById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocumentType information get successfully");
			responseObjectsMap.put("documentTypeVO", documentTypeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "DocumentTypeVO information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllDocumentType")
	public ResponseEntity<ResponseDTO> getAllDocumentType(@RequestParam Long orgid) {
		String methodName = "getAllDocumentType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DocumentTypeVO> documentTypeVO = new ArrayList<>();
		try {
			documentTypeVO = warehouseMasterService.getAllDocumentTypeByOrgId(orgid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document Type information get successfully");
			responseObjectsMap.put("documentTypeVO", documentTypeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Document Type information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createDocumentTypeMapping")
	public ResponseEntity<ResponseDTO> createDocumentTypeMapping(
			@RequestBody DocumentTypeMappingDTO documentTypeMappingDTO) {
		String methodName = "createDocumentTypeMapping()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> documentTypeMappingVO = warehouseMasterService
					.createDocumentTypeMapping(documentTypeMappingDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, documentTypeMappingVO.get("message"));
			responseObjectsMap.put("documentTypeMappingVO", documentTypeMappingVO.get("documentTypeMappingVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPendingDocumentTypeMapping")
	public ResponseEntity<ResponseDTO> getPendingDocumentTypeMapping(@RequestParam Long orgId,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String finYear,
			@RequestParam String finYearIdentifier, @RequestParam String clientCode) {
		String methodName = "getPendingDocumentTypeMappin	g()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> documentTypeMappingVO = new ArrayList<>();
		try {
			documentTypeMappingVO = warehouseMasterService.getPendingDocumentTypeMapping(orgId, branch, branchCode,
					finYear, finYearIdentifier, clientCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document Type information get successfully");
			responseObjectsMap.put("documentTypeMappingVO", documentTypeMappingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Document Type information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllDocumentTypeMapping")
	public ResponseEntity<ResponseDTO> getAllDocumentTypeMapping(@RequestParam Long orgId) {
		String methodName = "getAllDocumentTypeMapping()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DocumentTypeMappingVO> documentTypeMappingVO = new ArrayList<>();
		try {
			documentTypeMappingVO = warehouseMasterService.getAllDocumentTypeMapping(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document Type Mapping information get successfully");
			responseObjectsMap.put("documentTypeMappingVO", documentTypeMappingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Document Type Mapping information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/documentTypeMappingById")
	public ResponseEntity<ResponseDTO> documentTypeMappingById(@RequestParam Long id) {
		String methodName = "documentTypeMappingById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DocumentTypeMappingVO documentTypeMappingVO = new DocumentTypeMappingVO();
		try {
			documentTypeMappingVO = warehouseMasterService.getDocumentTypeMappingById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "documentTypeMappingVO information get successfully");
			responseObjectsMap.put("documentTypeMappingVO", documentTypeMappingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"documentTypeMappingVO information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	

	
}
