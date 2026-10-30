package com.whydigit.fms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.fms.ResponseDTO.BookingResponseDTO;
import com.whydigit.fms.ResponseDTO.EnquiryResponseDTO;
import com.whydigit.fms.ResponseDTO.QuotationResponseDTO;
import com.whydigit.fms.common.CommonConstant;
import com.whydigit.fms.common.UserConstants;
import com.whydigit.fms.dto.BookingDTO;
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
	public ResponseEntity<ResponseDTO> getEnquiryByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

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
	
	@GetMapping("/getQuotationDocId")
	public ResponseEntity<ResponseDTO> getQuotationDocId(@RequestParam Long orgId) {

		String methodName = "getQuotationDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		String quotationDocId = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			quotationDocId = salesService.getQuotationDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(errorMsg)) {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Quotation DocId", errorMsg);

		} else {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Quotation DocId information retrieved successfully");

			responseObjectsMap.put("quotationDocId", quotationDocId);

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
	
	
	//booking
	
	
	
	// =========================================================
	// CREATE / UPDATE BOOKING
	// =========================================================

	@PutMapping(value = "/createUpdateBooking",
	        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateBooking(
	        @RequestPart("bookingDTO") BookingDTO bookingDTO,
//	        @RequestBody BookingDTO bookingDTO,

	        @RequestPart(value = "files", required = false) MultipartFile[] files) {

	    String methodName = "createUpdateBooking()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        Map<String, Object> result =
	                salesService.createUpdateBooking(bookingDTO, files);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                result.get("message"));

	        responseObjectsMap.put(
	                "bookingVO",
	                result.get("bookingVO"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Booking creation/update failed",
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/viewBookingFile/**")
	public ResponseEntity<byte[]> viewBookingFile(
	        HttpServletRequest request) {

	    String methodName = "viewBookingFile()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    try {
	        return salesService.viewBookingFile(request);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        return ResponseEntity.status(500).build();
	    }
	
	}
	
	// =========================================================
	// GET BOOKING BY ID
	// =========================================================

	@GetMapping("/getBookingById")
	public ResponseEntity<ResponseDTO> getBookingById(
	        @RequestParam Long id) {

	    String methodName = "getBookingById()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    try {

	        BookingResponseDTO bookingDTO =
	        		salesService.getBookingById(id);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Booking information retrieved successfully");

	        responseObjectsMap.put("bookingVO", bookingDTO);

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
	                "Booking information retrieval failed",
	                e.getMessage());

	        return ResponseEntity.badRequest().body(responseDTO);

	    } finally {

	        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    }
	}
	
	@GetMapping("/getBookingByOrgId")
	public ResponseEntity<ResponseDTO> getBookingByOrgId(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getBookingByOrgId()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        List<BookingResponseDTO> bookingResponseDTO =
	        		salesService.getBookingByOrgId(orgId, branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Booking information retrieved successfully");

	        responseObjectsMap.put("bookingResponseVO", bookingResponseDTO);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Booking information retrieval failed",
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok(responseDTO);
	}


	@GetMapping("/getBookingDocId")
	public ResponseEntity<ResponseDTO> getBookingDocId(
	        @RequestParam Long orgId) {

	    String methodName = "getBookingDocId()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    String bookingDocId = null;

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        bookingDocId = salesService.getBookingDocId(orgId);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);
	    }

	    if (org.apache.commons.lang3.StringUtils.isNotBlank(errorMsg)) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Failed to retrieve Booking DocId",
	                errorMsg);

	    } else {

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Booking DocId information retrieved successfully");

	        responseObjectsMap.put("bookingDocId", bookingDocId);

	        responseDTO = createServiceResponse(responseObjectsMap);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("/getNextQuotationRevision")
	public ResponseEntity<ResponseDTO> getNextQuotationRevision(
	        @RequestParam String docId) {

	    String methodName = "getNextQuotationRevision()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    Map<String, Object> revisionDetails = new HashMap<>();

	    try {
	        revisionDetails = salesService.getNextQuotationRevision(docId);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	    }

	    if (errorMsg == null || errorMsg.trim().isEmpty()) {

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                "Quotation revision information retrieved successfully");

	        responseObjectsMap.put("docId", revisionDetails.get("docId"));
	        responseObjectsMap.put("revisionNo", revisionDetails.get("revisionNo"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } else {
	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Quotation revision information retrieval failed",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	
	 
}
