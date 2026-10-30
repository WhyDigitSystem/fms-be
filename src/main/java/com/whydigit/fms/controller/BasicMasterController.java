package com.whydigit.fms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.fms.ResponseDTO.ConfigResponseDTO;
import com.whydigit.fms.ResponseDTO.CustomerResponseDTO;
import com.whydigit.fms.ResponseDTO.PortResponseDTO;
import com.whydigit.fms.ResponseDTO.ProductResponseDTO;
import com.whydigit.fms.ResponseDTO.ServiceResponseDTO;
import com.whydigit.fms.common.CommonConstant;
import com.whydigit.fms.common.UserConstants;
import com.whydigit.fms.dto.ConfigDTO;
import com.whydigit.fms.dto.CustomerDTO;
import com.whydigit.fms.dto.PortDTO;
import com.whydigit.fms.dto.ProductDTO;
import com.whydigit.fms.dto.ResponseDTO;
import com.whydigit.fms.dto.ServiceDTO;
import com.whydigit.fms.service.BasicMasterService;

@RestController
@RequestMapping("/api/basicmaster")
public class BasicMasterController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(BasicMasterController.class);

	@Autowired
	BasicMasterService basicMasterService;

	// CREATE / UPDATE PRODUCT
	@PutMapping("/createUpdateProduct")
	public ResponseEntity<ResponseDTO> createUpdateProduct(@RequestBody ProductDTO productDTO) {

		String methodName = "createUpdateProduct()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> productResult = basicMasterService.createUpdateProduct(productDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, productResult.get("message"));

			responseObjectsMap.put("productVO", productResult.get("productVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// GET PRODUCT BY ID
	@GetMapping("/getProductById")
	public ResponseEntity<ResponseDTO> getProductById(@RequestParam Long id) {

		String methodName = "getProductById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			ProductResponseDTO productResponseDTO = basicMasterService.getProductById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Product information retrieved successfully");

			responseObjectsMap.put("productResponseVO", productResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Product information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// GET PRODUCT BY ORG ID AND BRANCH
	@GetMapping("/getProductByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getProductByOrgIdAndBranch(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getProductByOrgIdAndBranch()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<ProductResponseDTO> productResponseDTO = basicMasterService.getProductsByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Product information retrieved successfully");

			responseObjectsMap.put("productResponseVO", productResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Product information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// CREATE / UPDATE SERVICE
	@PutMapping("/createUpdateService")
	public ResponseEntity<ResponseDTO> createUpdateService(@RequestBody ServiceDTO serviceDTO) {

		String methodName = "createUpdateService()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> serviceResult = basicMasterService.createUpdateService(serviceDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, serviceResult.get("message"));

			responseObjectsMap.put("serviceVO", serviceResult.get("serviceVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// GET SERVICE BY ID
	@GetMapping("/getServiceById")
	public ResponseEntity<ResponseDTO> getServiceById(@RequestParam Long id) {

		String methodName = "getServiceById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			ServiceResponseDTO serviceResponseDTO = basicMasterService.getServiceById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Service information retrieved successfully");

			responseObjectsMap.put("serviceResponseVO", serviceResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Service information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// GET SERVICE BY ORG ID AND BRANCH
	@GetMapping("/getServiceByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getServiceByOrgIdAndBranch(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getServiceByOrgIdAndBranch()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<ServiceResponseDTO> serviceResponseDTO = basicMasterService.getServicesByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Service information retrieved successfully");

			responseObjectsMap.put("serviceResponseVO", serviceResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Service information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// CREATE / UPDATE PORT

	@PutMapping("/createUpdatePort")
	public ResponseEntity<ResponseDTO> createUpdatePort(@RequestBody PortDTO portDTO) {

		String methodName = "createUpdatePort()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> portResult = basicMasterService.createUpdatePort(portDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, portResult.get("message"));

			responseObjectsMap.put("portVO", portResult.get("portVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// GET PORT BY ID

	@GetMapping("/getPortById")
	public ResponseEntity<ResponseDTO> getPortById(@RequestParam Long id) {

		String methodName = "getPortById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			PortResponseDTO portResponseDTO = basicMasterService.getPortById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Port information retrieved successfully");

			responseObjectsMap.put("portResponseVO", portResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Port information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// =========================================================
	// GET PORT BY ORG ID AND BRANCH
	// =========================================================

	@GetMapping("/getPortByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getPortByOrgIdAndBranch(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getPortByOrgIdAndBranch()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<PortResponseDTO> portResponseDTO = basicMasterService.getPortsByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Port information retrieved successfully");

			responseObjectsMap.put("portResponseVO", portResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Port information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// =========================================================
	// CONFIGURATION
	// =========================================================

	// CREATE / UPDATE CONFIGURATION
	@PutMapping("/createUpdateConfig")

	public ResponseEntity<ResponseDTO> createUpdateConfig(@RequestBody ConfigDTO configDTO) {

		String methodName = "createUpdateConfig()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> configResult = basicMasterService.createUpdateConfig(configDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, configResult.get("message"));

			responseObjectsMap.put("configResponseVO", configResult.get("configResponseVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, "Configuration processing failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// GET CONFIGURATION BY ID
	@GetMapping("/getConfigById")
	public ResponseEntity<ResponseDTO> getConfigById(@RequestParam Long id) {

		String methodName = "getConfigById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			ConfigResponseDTO configResponse = basicMasterService.getConfigById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Configuration retrieved successfully");

			responseObjectsMap.put("configResponseVO", configResponse);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, "Configuration retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// GET CONFIGURATIONS BY ORGANIZATION ID AND BRANCH
	@GetMapping("/getConfigbyOrgIdandBranch")
	public ResponseEntity<ResponseDTO> getConfigByOrgIdAndBranch(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getConfigByOrgIdAndBranch()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<ConfigResponseDTO> configResponses = basicMasterService.getConfigByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Configurations retrieved successfully");

			responseObjectsMap.put("configResponseVO", configResponses);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, "Configuration retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// CREATE / UPDATE CUSTOMER
	@PutMapping("/createUpdateCustomer")

	public ResponseEntity<ResponseDTO> createUpdateCustomer(@RequestBody CustomerDTO customerDTO) {

		String methodName = "createUpdateCustomer()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> customerResult = basicMasterService.createUpdateCustomer(customerDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, customerResult.get("message"));

			responseObjectsMap.put("customerVO", customerResult.get("customerVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, "Customer processing failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// GET CUSTOMER BY ID
	@GetMapping("/getByCustomerId")
	public ResponseEntity<ResponseDTO> getCustomerById(@RequestParam Long id) {

		String methodName = "getCustomerById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			CustomerResponseDTO customerResponse = basicMasterService.getCustomerById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer retrieved successfully");

			responseObjectsMap.put("customerVO", customerResponse);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, "Customer retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// GET CUSTOMERS BY BRANCH AND ORGANIZATION ID
	@GetMapping("/getByCustomerByBranchAndOrgId")
	public ResponseEntity<ResponseDTO> getCustomersByBranchAndOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getCustomersByBranchAndOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<CustomerResponseDTO> customerResponses = basicMasterService.getCustomersByBranchAndOrgId(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customers retrieved successfully");

			responseObjectsMap.put("customerVO", customerResponses);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, "Customer retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

}