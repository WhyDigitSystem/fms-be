package com.whydigit.fms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.fms.ResponseDTO.ProductResponseDTO;
import com.whydigit.fms.ResponseDTO.ServiceResponseDTO;
import com.whydigit.fms.common.CommonConstant;
import com.whydigit.fms.common.UserConstants;
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
}