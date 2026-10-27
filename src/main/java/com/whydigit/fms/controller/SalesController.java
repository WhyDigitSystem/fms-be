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

import com.whydigit.fms.ResponseDTO.EnquiryResponseDTO;
import com.whydigit.fms.ResponseDTO.QuotationResponseDTO;
import com.whydigit.fms.common.CommonConstant;
import com.whydigit.fms.common.UserConstants;
import com.whydigit.fms.dto.EnquiryDTO;
import com.whydigit.fms.dto.QuotationDTO;
import com.whydigit.fms.dto.ResponseDTO;
import com.whydigit.fms.service.SalesService;

@RestController
@RequestMapping("/api/sales")
public class SalesController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(SalesController.class);

	@Autowired
	SalesService salesService;

	@PutMapping("/createUpdateEnquiry")
	public ResponseEntity<ResponseDTO> createUpdateEnquiry(@RequestBody EnquiryDTO enquiryDTO) {

		String methodName = "createUpdateEnquiry()";
		Map<String, Object> responseObjectsMap = new HashMap<>();

		try {
			Map<String, Object> createdEnquiryVO = salesService.createUpdateEnquiry(enquiryDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdEnquiryVO.get("message"));

			responseObjectsMap.put("enquiryVO", createdEnquiryVO.get("enquiryVO"));

			ResponseDTO responseDTO = createServiceResponse(responseObjectsMap);

			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			ResponseDTO responseDTO = createServiceResponseError(responseObjectsMap, "Enquiry creation/update failed",
					e.getMessage());

			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	@GetMapping("/getEnquiryById")
	public ResponseEntity<ResponseDTO> getEnquiryById(@RequestParam Long id) {

		String methodName = "getEnquiryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			EnquiryResponseDTO enquiryResponseDTO = salesService.getEnquiryById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Enquiry information retrieved successfully");

			responseObjectsMap.put("enquiryResponseVO", enquiryResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Enquiry information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getEnquiryByOrgId")
	public ResponseEntity<ResponseDTO> getEnquiryByOrgId(@RequestParam Long orgId, @RequestParam String branch) {

		String methodName = "getEnquiryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<EnquiryResponseDTO> enquiryResponseDTO = salesService.getEnquiryByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Enquiry information retrieved successfully");

			responseObjectsMap.put("enquiryResponseVO", enquiryResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Enquiry information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getEnquiryDocId")
	public ResponseEntity<ResponseDTO> getEnquiryDocId(@RequestParam Long orgId) {

		String methodName = "getEnquiryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		String enquiryDocId = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			enquiryDocId = salesService.getEnquiryDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(errorMsg)) {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Enquiry DocId", errorMsg);

		} else {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Enquiry DocId information retrieved successfully");

			responseObjectsMap.put("enquiryDocId", enquiryDocId);

			responseDTO = createServiceResponse(responseObjectsMap);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateQuotation")
	public ResponseEntity<ResponseDTO> createUpdateQuotation(@RequestBody QuotationDTO quotationDTO) {

		String methodName = "createUpdateQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		try {
			Map<String, Object> result = salesService.createUpdateQuotation(quotationDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));

			responseObjectsMap.put("quotationVO", result.get("quotationVO"));

			ResponseDTO responseDTO = createServiceResponse(responseObjectsMap);

			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			ResponseDTO responseDTO = createServiceResponseError(responseObjectsMap, "Quotation creation/update failed",
					e.getMessage());

			return ResponseEntity.badRequest().body(responseDTO);

		} finally {
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		}
	}

	// =========================================================
	// GET QUOTATION BY ID
	// =========================================================
	@GetMapping("/getQuotationById")
	public ResponseEntity<ResponseDTO> getQuotationById(
	        @RequestParam Long id) {

	    String methodName = "getQuotationById()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    try {
	        QuotationResponseDTO quotationDTO =
	                salesService.getQuotationById(id);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Quotation information retrieved successfully");

	        responseObjectsMap.put("quotationVO", quotationDTO);

	        ResponseDTO responseDTO =
	                createServiceResponse(responseObjectsMap);

	        return ResponseEntity.ok(responseDTO);

	    } catch (Exception e) {
	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        ResponseDTO responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Quotation information retrieval failed",
	                e.getMessage());

	        return ResponseEntity.badRequest().body(responseDTO);

	    } finally {
	        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    }
	}

	// =========================================================
	// GET QUOTATION BY ORG ID AND BRANCH
	// =========================================================
	@GetMapping("/getQuotationByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getQuotationByOrgIdAndBranch(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getQuotationByOrgIdAndBranch()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    try {
	        List<QuotationResponseDTO> quotationList =
	                salesService.getQuotationByOrgIdAndBranch(orgId, branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Quotation information retrieved successfully");

	        responseObjectsMap.put("quotationVO", quotationList);

	        ResponseDTO responseDTO =
	                createServiceResponse(responseObjectsMap);

	        return ResponseEntity.ok(responseDTO);

	    } catch (Exception e) {
	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        ResponseDTO responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Quotation information retrieval failed",
	                e.getMessage());

	        return ResponseEntity.badRequest().body(responseDTO);

	    } finally {
	        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    }
	}
}
