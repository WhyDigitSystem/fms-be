package com.whydigit.fms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.fms.ResponseDTO.CityResponseDTO;
import com.whydigit.fms.ResponseDTO.DocumentTypeMappingResponseDTO;
import com.whydigit.fms.ResponseDTO.GlobalParameterResponseDTO;
import com.whydigit.fms.ResponseDTO.StateResponseDTO;
import com.whydigit.fms.common.CommonConstant;
import com.whydigit.fms.common.UserConstants;
import com.whydigit.fms.dto.CityDTO;
import com.whydigit.fms.dto.CompanyDTO;
import com.whydigit.fms.dto.CountryDTO;
import com.whydigit.fms.dto.CurrencyDTO;
import com.whydigit.fms.dto.DepartmentDTO;
import com.whydigit.fms.dto.DesignationDTO;
import com.whydigit.fms.dto.DocumentTypeMappingDTO;
import com.whydigit.fms.dto.DocumentTypeMasterDTO;
import com.whydigit.fms.dto.FinancialYearDTO;
import com.whydigit.fms.dto.GlobalParameterDTO;
import com.whydigit.fms.dto.ModeMasterDTO;
import com.whydigit.fms.dto.ResponseDTO;
import com.whydigit.fms.dto.ScreenNamesDTO;
import com.whydigit.fms.dto.StateDTO;
import com.whydigit.fms.dto.UserProfileInformationDTO;
import com.whydigit.fms.entity.CompanyVO;
import com.whydigit.fms.entity.CountryVO;
import com.whydigit.fms.entity.CurrencyVO;
import com.whydigit.fms.entity.DepartmentVO;
import com.whydigit.fms.entity.DesignationVO;
import com.whydigit.fms.entity.DocumentTypeMasterVO;
import com.whydigit.fms.entity.FinancialYearVO;
import com.whydigit.fms.entity.GlobalParameterVO;
import com.whydigit.fms.entity.ScreenNamesVO;
import com.whydigit.fms.entity.UserProfileInformationVO;
import com.whydigit.fms.service.CommonMasterService;

@RestController
@RequestMapping("/api/commonmaster")
public class CommonMasterController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonMasterController.class);

	@Autowired
	CommonMasterService commonMasterService;

	@GetMapping("/country")
	public ResponseEntity<ResponseDTO> getAllcountries(@RequestParam Long orgid) {
		String methodName = "getAllCountry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CountryVO> countryVO = new ArrayList<>();
		try {
			countryVO = commonMasterService.getAllCountry(orgid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "countries information get successfully");
			responseObjectsMap.put("countryVO", countryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "countries information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/country/{countryid}")
	public ResponseEntity<ResponseDTO> getCountryById(@PathVariable Long countryid) {
		String methodName = "getCountryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CountryVO CountryVO = null;
		try {
			CountryVO = commonMasterService.getCountryById(countryid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Country found by ID");
			responseObjectsMap.put("Country", CountryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Country not found for ID: " + countryid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Country not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/createUpdateCountry")
	public ResponseEntity<ResponseDTO> createUpdateCountry(@RequestBody CountryDTO countryDTO) {
		String methodName = "createCountry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseObjectsMap = new HashMap<String, Object>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdCountryVO = commonMasterService.createUpdateCountry(countryDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdCountryVO.get("message"));
			responseObjectsMap.put("countryVO", createdCountryVO.get("createdCountryVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// State

	@GetMapping("/getAllStateByOrgId")
	public ResponseEntity<ResponseDTO> getAllStateByOrgId(@RequestParam Long orgid) {
	    String methodName = "getAllStateByOrgId()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    List<StateResponseDTO> stateVO = new ArrayList<>();

	    try {
	        stateVO = commonMasterService.getAllStateByOrgId(orgid);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	    }

	    if (StringUtils.isBlank(errorMsg)) {
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "State information get successfully");
	        responseObjectsMap.put("stateVO", stateVO);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } else {
	        responseDTO = createServiceResponseError(
	                responseObjectsMap, "States information receive failed", errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getStateById")
	public ResponseEntity<ResponseDTO> getStateById(@RequestParam Long stateid) {
	    String methodName = "getStateById()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    StateResponseDTO stateVO = null;

	    try {
	        stateVO = commonMasterService.getStateById(stateid).orElse(null);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	    }

	    if (StringUtils.isBlank(errorMsg) && stateVO != null) {
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "States found by State ID");
	        responseObjectsMap.put("stateVO", stateVO);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } else {
	        errorMsg = "State not found for State ID: " + stateid;
	        responseDTO = createServiceResponseError(
	                responseObjectsMap, "States not found", errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getStateByCountry")
	public ResponseEntity<ResponseDTO> getStateByCountry(
	        @RequestParam Long orgid,
	        @RequestParam Long country) {

	    String methodName = "getStateByCountry()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    List<StateResponseDTO> stateVO = null;

	    try {
	        stateVO = commonMasterService.getStatesByCountry(orgid, country);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	    }

	    if (StringUtils.isBlank(errorMsg)) {
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "States found by Country");
	        responseObjectsMap.put("stateVO", stateVO);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } else {
	        errorMsg = "State not found for country: " + country;
	        responseDTO = createServiceResponseError(
	                responseObjectsMap, "States not found", errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateState")
	public ResponseEntity<ResponseDTO> createUpdateState(@RequestBody StateDTO stateDTO) {
		String methodName = "createUpdateState()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> stateVO = commonMasterService.createUpdateState(stateDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, stateVO.get("message"));
			responseObjectsMap.put("stateVO", stateVO.get("stateVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	// city
	@GetMapping("/getAllCityByOrgId")
	public ResponseEntity<ResponseDTO> getAllCityByOrgId(@RequestParam Long orgid) {
	    String methodName = "getAllCityByOrgId()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    List<CityResponseDTO> cityVO = new ArrayList<>();

	    try {
	        cityVO = commonMasterService.getAllCityByOrgId(orgid);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	    }

	    if (StringUtils.isBlank(errorMsg)) {
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "city information get successfully");
	        responseObjectsMap.put("cityVO", cityVO);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } else {
	        responseDTO = createServiceResponseError(
	                responseObjectsMap, "city information receive failed", errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCityByOrgIdAndstate")
	public ResponseEntity<ResponseDTO> getCityByOrgIdAndstate(
	        @RequestParam Long orgid, @RequestParam Long state) {
	    String methodName = "getCityByOrgIdAndstate()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    List<CityResponseDTO> cityVO = new ArrayList<>();

	    try {
	        cityVO = commonMasterService.getCityByOrgIdAndstate(orgid, state);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	    }

	    if (StringUtils.isBlank(errorMsg)) {
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "city information get successfully");
	        responseObjectsMap.put("cityVO", cityVO);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } else {
	        responseDTO = createServiceResponseError(
	                responseObjectsMap, "city information receive failed", errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/cityById")
	public ResponseEntity<ResponseDTO> getCityById(@RequestParam Long cityid) {
	    String methodName = "getCityById()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    CityResponseDTO cityVO = null;

	    try {
	        cityVO = commonMasterService.getCityById(cityid).orElse(null);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	    }

	    if (StringUtils.isBlank(errorMsg) && cityVO != null) {
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "City found by City ID");
	        responseObjectsMap.put("cityVO", cityVO);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } else {
	        errorMsg = "City not found for City ID: " + cityid;
	        responseDTO = createServiceResponseError(
	                responseObjectsMap, "City not found", errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateCity")
	public ResponseEntity<ResponseDTO> createUpdateCity(@RequestBody CityDTO cityDTO) {
		String methodName = "createCity()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdCityVO = commonMasterService.createUpdateCity(cityDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdCityVO.get("message"));
			responseObjectsMap.put("cityVO", createdCityVO.get("cityVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	

	// Currency

	@GetMapping("/getAllCurrencyByOrgId")
	public ResponseEntity<ResponseDTO> getAllCurrency(@RequestParam Long orgid) {
		String methodName = "getAllCurrency()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CurrencyVO> currencyVO = new ArrayList<>();
		try {
			currencyVO = commonMasterService.getAllCurrency(orgid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Currency" + " information get successfully");
			responseObjectsMap.put("currencyVO", currencyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Currency information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// getCUrrencyById

	@GetMapping("/getCurrencyById")
	public ResponseEntity<ResponseDTO> getCurrencyById(@RequestParam Long currencyid) {
		String methodName = "getCurrencyById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CurrencyVO currencyVO = null;
		try {
			currencyVO = commonMasterService.getCurrencyById(currencyid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Currency found by Currency ID");
			responseObjectsMap.put("currencyVO", currencyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Currency not found for Currency ID: " + currencyid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Currency not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateCurrency")
	public ResponseEntity<ResponseDTO> createUpdateCurrency(@RequestBody CurrencyDTO currencyDTO) {
		String methodName = "createUpdateCurrency()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> currency = commonMasterService.createUpdateCurrency(currencyDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, currency.get("message"));
			responseObjectsMap.put("currency", currency.get("currencyVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Company

	@GetMapping("/company")
	public ResponseEntity<ResponseDTO> getAllCompany() {
		String methodName = "getAllCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CompanyVO> companyVO = new ArrayList<>();
		try {
			companyVO = commonMasterService.getAllCompany();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Company information get successfully");
			responseObjectsMap.put("companyVO", companyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Company information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/company/{companyid}")
	public ResponseEntity<ResponseDTO> getcompanyById(@PathVariable Long companyid) {
		String methodName = "getCompanyById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CompanyVO> companyVO = new ArrayList<CompanyVO>();
		try {
			companyVO = commonMasterService.getCompanyById(companyid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Company found by Company ID");
			responseObjectsMap.put("companyVO", companyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "company not found for companyID: " + companyid;
			responseDTO = createServiceResponseError(responseObjectsMap, "company not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/company")
	public ResponseEntity<ResponseDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
		String methodName = "createCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CompanyVO createdCompanyVO = commonMasterService.createCompany(companyDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Company created successfully");
			responseObjectsMap.put("createdCompanyVO", createdCompanyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Company creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCompany")
	public ResponseEntity<ResponseDTO> updateCompany(@RequestBody CompanyDTO companyDTO) {
		String methodName = "updateCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CompanyVO updatedCompanyVO = commonMasterService.updateCompany(companyDTO);
			if (updatedCompanyVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customers updated successfully");
				responseObjectsMap.put("CompanyVO", updatedCompanyVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Organization not found for ID: " + companyDTO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	// Global Parameter

	@GetMapping("/globalparamBranchByUserName")
	public ResponseEntity<ResponseDTO> getGlobalParameterBranchByUserName(
	        @RequestParam Long orgid,
	        @RequestParam String userName) {

	    String methodName = "getGlobalParameterBranchByUserName()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;

	    try {
	        List<Map<String, Object>> globalParameters =
	                commonMasterService
	                        .getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(
	                                orgid, userName);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Global Parameter Branch information retrieved successfully");

	        responseObjectsMap.put("GlopalParameters", globalParameters);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Global Parameter Branch information retrieval failed",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	// get Access Customers

//	@GetMapping("/globalparamCustomerByUserName")
//	public ResponseEntity<ResponseDTO> getGlobalParameterCustomerByUserName(@RequestParam Long orgid,
//			@RequestParam String userName, @RequestParam String branchcode) {
//		String methodName = "getGlobalParameterCustomerByUserName()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		Set<Object[]> customerVO = new HashSet<>();
//		try {
//			customerVO = commonMasterService.getAllAccessCustomerForLogin(orgid, userName, branchcode);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			List<Map<String, String>> getCustomer = formatCustomer(customerVO);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"Global Parameter Customer information get successfully");
//			responseObjectsMap.put("GlopalParameterCustomer", getCustomer);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"Global Parameter Customer information receive failed", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}
//
//	private List<Map<String, String>> formatCustomer(Set<Object[]> customerVO) {
//		List<Map<String, String>> getCustomer = new ArrayList<>();
//		for (Object[] parameters : customerVO) {
//			Map<String, String> param = new HashMap<>();
//			param.put("customer", parameters[0].toString());
//			getCustomer.add(param);
//		}
//		return getCustomer;
//	}
//
//	@GetMapping("/globalparamClientByUserName")
//	public ResponseEntity<ResponseDTO> getGlobalParameterClientByUserName(@RequestParam Long orgid,
//			@RequestParam String userName, @RequestParam String branchcode, @RequestParam String customer) {
//		String methodName = "getGlobalParameterClientByUserName()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		Set<Object[]> clientVO = new HashSet<>();
//		try {
//			clientVO = commonMasterService.getAllAccessClientForLogin(orgid, userName, branchcode, customer);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			List<Map<String, String>> getClient = format(clientVO);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"Global Parameter Client information get successfully");
//			responseObjectsMap.put("GlopalParameterClient", getClient);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"Global Parameter Client information receive failed", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

	// get Global Param

	private List<Map<String, String>> format(Set<Object[]> clientVO) {
		List<Map<String, String>> getClient = new ArrayList<>();
		for (Object[] parameters : clientVO) {
			Map<String, String> param = new HashMap<>();
			param.put("client", parameters[0].toString());
			getClient.add(param);
		}
		return getClient;
	}

	@GetMapping("/globalparam/username")
	public ResponseEntity<ResponseDTO> getGlobalParamByOrgIdAndUserId(@RequestParam Long orgid,
			@RequestParam String userId) {
		String methodName = "getCountryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<GlobalParameterVO> globalparam = null;
		try {
			globalparam = commonMasterService.getGlobalParamByOrgIdAndUserName(orgid, userId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GlobalParam found by ID");
			responseObjectsMap.put("globalParam", globalparam);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "GlobalParam not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "GlobalParam not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getWarehouseNameByOrgIdAndBranchAndClient")
	public ResponseEntity<ResponseDTO> getWarehouseNameByOrgIdAndBranchAndClient(@RequestParam Long orgid,
			@RequestParam String branch, @RequestParam String client) {
		String methodName = "getWarehouseNameByOrgIdAndBranchAndClient()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> global = new HashSet<>();
		try {
			global = commonMasterService.getWarehouseNameByOrgIdAndBranchAndClient(orgid, branch, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, String>> getGlobal = formatt(global);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Global Parameter Warehouse information get successfully");
			responseObjectsMap.put("GlopalParameterWarehouse", getGlobal);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Global Parameter Warehouse information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// get Global Param

	private List<Map<String, String>> formatt(Set<Object[]> global) {
		List<Map<String, String>> getglobal = new ArrayList<>();
		for (Object[] parameters : global) {
			Map<String, String> param = new HashMap<>();
			param.put("warehouse", parameters[0].toString());
			getglobal.add(param);
		}
		return getglobal;
	}

	@PutMapping("/globalparam")
	public ResponseEntity<ResponseDTO> updateGlobalParam(@RequestBody GlobalParameterDTO globalParameterDTO) {
		String methodName = "updateGlobalParam()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			GlobalParameterResponseDTO gloParameterVO = commonMasterService.updateGlobaParameter(globalParameterDTO);
			if (gloParameterVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Global Parameter Updated successfully");
				responseObjectsMap.put("GlobalParameterVO", gloParameterVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Global Parameter not found for ID: " ;
				responseDTO = createServiceResponseError(responseObjectsMap, "Global Parameter Update failed",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Global Parameter Update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateScreenNames")
	public ResponseEntity<ResponseDTO> createUpdateScreenNames(@RequestBody ScreenNamesDTO screenNamesDTO) {
		String methodName = "createUpdateScreenNames()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> screenNamesVO = commonMasterService.createUpdateScreenNames(screenNamesDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, screenNamesVO.get("message"));
			responseObjectsMap.put("screenNamesVO", screenNamesVO.get("screenNamesVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/allScreenNames")
	public ResponseEntity<ResponseDTO> getAllScreenNames() {
		String methodName = "getAllScreenNames()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ScreenNamesVO> screenNamesVO = new ArrayList<>();
		try {
			screenNamesVO = commonMasterService.getAllScreenNames();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "screenNames information get successfully");
			responseObjectsMap.put("screenNamesVO", screenNamesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "screenNames information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/screenNamesById")
	public ResponseEntity<ResponseDTO> getScreenNamesById(@RequestParam Long id) {
		String methodName = "getScreenNamesById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ScreenNamesVO screenNamesVO = new ScreenNamesVO();
		try {
			screenNamesVO = commonMasterService.getScreenNamesById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "screenNames information get successfully");
			responseObjectsMap.put("screenNamesVO", screenNamesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "screenNames information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Designation

	@PutMapping("/createUpdateDesignation")
	public ResponseEntity<ResponseDTO> createUpdateDesignation(@RequestBody DesignationDTO designationDTO) {
		String methodName = "createUpdateDesignation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> designationVO = commonMasterService.createUpdateDesignation(designationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, designationVO.get("messages")); // Correct the key to
																									// "message"
			responseObjectsMap.put("designationVO", designationVO.get("designationVO")); // Directly put the
																							// designationVO map
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllDesignation")
	public ResponseEntity<ResponseDTO> getAllDesignation() {
		String methodName = "getAllDesignation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DesignationVO> designationVOs = new ArrayList<>();
		try {
			designationVOs = commonMasterService.getAllDesignation();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Designation information get successfully");
			responseObjectsMap.put("designationVO", designationVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Designation information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllDesignationByOrgId")
	public ResponseEntity<ResponseDTO> getAllDesignationByOrgId(Long orgId) {
		String methodName = "getAllDesignationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DesignationVO> designationVOs = new ArrayList<>();
		try {
			designationVOs = commonMasterService.getAllDesignationByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "screenNames information get successfully");
			responseObjectsMap.put("designationVO", designationVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "screenNames information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllDesignationById")
	public ResponseEntity<ResponseDTO> getAllDesignationById(Long id) {
		String methodName = "getAllDesignationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DesignationVO designationVOs = null;
		try {
			designationVOs = commonMasterService.getAllDesignationById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "screenNames information get successfully");
			responseObjectsMap.put("designationVO", designationVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "screenNames information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// FINANCIAL YEAR

	@PutMapping("/createUpdateFinYear")
	public ResponseEntity<ResponseDTO> createUpdateFinYear(@RequestBody FinancialYearDTO financialYearDTO) {
		String methodName = "createUpdateFinYear()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> finYearVO = commonMasterService.createUpdateFinYear(financialYearDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, finYearVO.get("messages"));
			responseObjectsMap.put("finYearVO", finYearVO.get("financialYearVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllAciveFInYear")
	public ResponseEntity<ResponseDTO> getAllFInYear(@RequestParam Long orgId) {
		String methodName = "getAllFInYear()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<FinancialYearVO> financialYearVOs = new ArrayList<FinancialYearVO>();
		try {
			financialYearVOs = commonMasterService.getAllActiveFInYear(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FInYear information get successfully");
			responseObjectsMap.put("financialYearVOs", financialYearVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "FInYear information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllFInYearByOrgId")
	public ResponseEntity<ResponseDTO> getAllFInYearByOrgId(Long orgId) {
		String methodName = "getAllFInYearByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<FinancialYearVO> financialYearVOs = new ArrayList<FinancialYearVO>();
		try {
			financialYearVOs = commonMasterService.getAllFInYearByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FInYear information get successfully By OrgId");
			responseObjectsMap.put("financialYearVOs", financialYearVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "FInYear information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllFInYearById")
	public ResponseEntity<ResponseDTO> getAllFInYearById(Long id) {
		String methodName = "getAllFInYearById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		FinancialYearVO financialYearVOs = null;
		try {
			financialYearVOs = commonMasterService.getAllFInYearById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FInYear information get successfully By Id");
			responseObjectsMap.put("financialYearVOs", financialYearVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "FInYear information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// DEPARTMENT

	@PutMapping("/createUpdateDepartment")
	public ResponseEntity<ResponseDTO> createUpdateDepartment(@RequestBody DepartmentDTO departmentDTO) {
		String methodName = "createUpdateDepartment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> departmentVO = commonMasterService.createUpdateDepartment(departmentDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, departmentVO.get("messages"));
			responseObjectsMap.put("departmentVO", departmentVO.get("departmentVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllDepartmentByOrgId")
	public ResponseEntity<ResponseDTO> getAllDepartmentByOrgId(Long orgId) {
		String methodName = "getAllDepartmentByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DepartmentVO> departmentVOs = new ArrayList<>();
		try {
			departmentVOs = commonMasterService.getAllDepartmentByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Department information get successfully By OrgId");
			responseObjectsMap.put("departmentVOs", departmentVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Department information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllDepartmentById")
	public ResponseEntity<ResponseDTO> getAllDepartmentById(Long id) {
		String methodName = "getAllDepartmentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DepartmentVO> departmentVOs = new ArrayList<>();
		try {
			departmentVOs = commonMasterService.getAllDepartmentById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Department information get successfully By Id");
			responseObjectsMap.put("departmentVOs", departmentVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Department information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadExpenseUserProfileInBloob")
	public ResponseEntity<ResponseDTO> uploadExpenseUserProfileInBloob(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
		String methodName = "uploadExpenseUserProfileInBloob()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		UserProfileInformationVO userProfileInformationVO = null;
		try {
			userProfileInformationVO = commonMasterService.uploadExpenseUserProfileInBloob(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload PartImage", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "UserInformation Image Successfully Upload");
			responseObjectsMap.put("userProfileInformationVO", userProfileInformationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "UserInformation Image Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("getUserProfileInformationById")
	public ResponseEntity<ResponseDTO> getUserProfileInformationById(@RequestParam Long id) {
		String methodName = "getUserProfileInformationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		UserProfileInformationVO userProfileInformationVO = new UserProfileInformationVO();
		try {
			userProfileInformationVO = commonMasterService.getUserProfileInformationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "UserInformation found by ID");
			responseObjectsMap.put("userProfileInformationVO", userProfileInformationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "UserProfile not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "UserInformation not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateUserProfileInformation")
	public ResponseEntity<ResponseDTO> createUpdateUserProfileInformation(
			@RequestBody UserProfileInformationDTO userProfileInformationDTO) {
		String methodName = "createUpdateUserProfileInformation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseObjectsMap = new HashMap<String, Object>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> userProfileInformationVO = commonMasterService
					.createUpdateUserProfileInformation(userProfileInformationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, userProfileInformationVO.get("message"));
			responseObjectsMap.put("userProfileInformationVO",
					userProfileInformationVO.get("userProfileInformationVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	@GetMapping("getProfileInformationByUserId")
	public ResponseEntity<ResponseDTO> getProfileInformationByUserId(@RequestParam Long userId) {
		String methodName = "getProfileInformationByUserId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		UserProfileInformationVO userProfileInformationVO = new UserProfileInformationVO();
		try {
			userProfileInformationVO = commonMasterService.getProfileInformationByUserId(userId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "UserInformation found by ID");
			responseObjectsMap.put("userProfileInformationVO", userProfileInformationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "UserProfile not found for ID: " + userId;
			responseDTO = createServiceResponseError(responseObjectsMap, "UserProfile not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	// Document Type Master

		@PutMapping("/createUpdateDocumentTypeMaster")
		public ResponseEntity<ResponseDTO> createUpdateDocumentTypeMaster(
				@RequestBody DocumentTypeMasterDTO documentTypeMasterDTO) {

			String methodName = "createUpdateDocumentTypeMaster()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;

			try {

				Map<String, Object> documentTypeMasterVO = commonMasterService
						.createUpdateDocumentTypeMaster(documentTypeMasterDTO);

				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, documentTypeMasterVO.get("message"));
				responseObjectsMap.put("documentTypeMasterVO", documentTypeMasterVO.get("documentTypeMasterVO"));

				responseDTO = createServiceResponse(responseObjectsMap);

			} catch (Exception e) {

				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/getDocumentTypeMasterById")
		public ResponseEntity<ResponseDTO> getDocumentTypeMasterById(@RequestParam Long id) {

			String methodName = "getDocumentTypeMasterById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			Map<String, Object> responseObjectsMap = new HashMap<>();
			String errorMsg = null;
			ResponseDTO responseDTO = null;

			try {

				DocumentTypeMasterVO documentTypeMasterVO = commonMasterService.getDocumentTypeMasterById(id);

				responseObjectsMap.put("documentTypeMasterVO", documentTypeMasterVO);

				responseDTO = createServiceResponse(responseObjectsMap);

			} catch (Exception e) {

				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/getAllDocumentTypeMasterByOrgId")
		public ResponseEntity<ResponseDTO> getAllDocumentTypeMasterByOrgId(@RequestParam Long orgId) {

			String methodName = "getAllDocumentTypeMasterByOrgId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			Map<String, Object> responseObjectsMap = new HashMap<>();
			String errorMsg = null;
			ResponseDTO responseDTO = null;

			try {

				List<DocumentTypeMasterVO> documentTypeMasterList = commonMasterService
						.getAllDocumentTypeMasterByOrgId(orgId);

				responseObjectsMap.put("documentTypeMasterList", documentTypeMasterList);

				responseDTO = createServiceResponse(responseObjectsMap);

			} catch (Exception e) {

				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return ResponseEntity.ok().body(responseDTO);
		}
		
		@PutMapping("/updateCreateDocumentTypeMapping")
		public ResponseEntity<ResponseDTO> updateCreateDocumentTypeMapping(
				@RequestBody DocumentTypeMappingDTO documentTypeMappingDTO) {

			String methodName = "updateCreateDocumentTypeMapping()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;

			try {

				Map<String, Object> documentTypeMappingVO = commonMasterService
						.updateCreateDocumentTypeMapping(documentTypeMappingDTO);

				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, documentTypeMappingVO.get("message"));
				responseObjectsMap.put("documentTypeMappingVO", documentTypeMappingVO.get("documentTypeMapping"));

				responseDTO = createServiceResponse(responseObjectsMap);

			} catch (Exception e) {

				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/getDocumentTypeMappingById")
		public ResponseEntity<ResponseDTO> getDocumentTypeMappingById(@RequestParam Long id) {

			String methodName = "getDocumentTypeMappingById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;

			try {

				DocumentTypeMappingResponseDTO documentTypeMappingResponseDTO = commonMasterService
						.getDocumentTypeMappingById(id);

				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
						"Document Type Mapping information retrieved successfully");

				responseObjectsMap.put("documentTypeMappingResponseVO", documentTypeMappingResponseDTO);

				responseDTO = createServiceResponse(responseObjectsMap);

			} catch (Exception e) {

				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

				responseDTO = createServiceResponseError(responseObjectsMap,
						"Document Type Mapping information retrieval failed", errorMsg);
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return ResponseEntity.ok(responseDTO);
		}

		@GetMapping("/getDocumentTypeMappingByOrgId")
		public ResponseEntity<ResponseDTO> getDocumentTypeMappingByOrgId(@RequestParam Long orgId,
				@RequestParam Long branch) {

			String methodName = "getDocumentTypeMappingByOrgId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO;

			try {

				List<DocumentTypeMappingResponseDTO> documentTypeMappingResponseDTO = commonMasterService
						.getDocumentTypeMappingByOrgId(orgId, branch);

				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
						"Document Type Mapping information retrieved successfully");

				responseObjectsMap.put("documentTypeMappingResponseVO", documentTypeMappingResponseDTO);

				responseDTO = createServiceResponse(responseObjectsMap);

			} catch (Exception e) {

				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

				responseDTO = createServiceResponseError(responseObjectsMap,
						"Document Type Mapping information retrieval failed", e.getMessage());
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return ResponseEntity.ok(responseDTO);
		}

		@GetMapping("/getPendingDocumentTypeMapping")
		public ResponseEntity<ResponseDTO> getPendingDocumentTypeMapping(@RequestParam Long orgId,
				@RequestParam String branch, @RequestParam String branchCode, @RequestParam String finYear,
				@RequestParam String finYearIdentifier) {
			String methodName = "getPendingDocumentTypeMapping()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String, Object>> documentTypeMappingVO = new ArrayList<>();
			try {
				documentTypeMappingVO = commonMasterService.getPendingDocumentTypeMapping(orgId, branch, branchCode,
						finYear, finYearIdentifier);
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
		
		
		@PutMapping("/createUpdateModeMaster")
		public ResponseEntity<ResponseDTO> createUpdateModeMaster(
		        @RequestBody ModeMasterDTO modeMasterDTO) {

		    String methodName = "createUpdateModeMaster()";

		    Map<String, Object> responseObjectsMap = new HashMap<>();

		    try {

		        Map<String, Object> createdModeMasterVO =
		                commonMasterService.createUpdateModeMaster(modeMasterDTO);

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                createdModeMasterVO.get("message"));

		        responseObjectsMap.put(
		                "modeMasterVO",
		                createdModeMasterVO.get("createdModeMasterVO"));

		        ResponseDTO responseDTO = createServiceResponse(responseObjectsMap);

		        return ResponseEntity.ok(responseDTO);

		    } catch (Exception e) {

		        ResponseDTO responseDTO = createServiceResponseError(
		                responseObjectsMap,
		                "Mode Master creation/update failed",
		                e.getMessage());

		        return ResponseEntity.badRequest().body(responseDTO);
		    }
		}
		
		
		@GetMapping("/getModeMasterById")
		public ResponseEntity<ResponseDTO> getModeMasterById(
		        @RequestParam Long id) {

		    String methodName = "getModeMasterById()";
		    Map<String, Object> responseObjectsMap = new HashMap<>();

		    try {
		        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
		                "Mode Master information retrieved successfully");

		        responseObjectsMap.put("modeMasterVO",
		                commonMasterService.getModeMasterById(id));

		        ResponseDTO responseDTO = createServiceResponse(responseObjectsMap);

		        return ResponseEntity.ok(responseDTO);

		    } catch (Exception e) {
		        ResponseDTO responseDTO = createServiceResponseError(
		                responseObjectsMap,
		                "Mode Master information retrieval failed",
		                e.getMessage());

		        return ResponseEntity.badRequest().body(responseDTO);
		    }
		}

		@GetMapping("/getModeMasterByOrgId")
		public ResponseEntity<ResponseDTO> getModeMasterByOrgId(
		        @RequestParam Long orgId) {

		    String methodName = "getModeMasterByOrgId()";
		    Map<String, Object> responseObjectsMap = new HashMap<>();

		    try {
		        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
		                "Mode Master information retrieved successfully");

		        responseObjectsMap.put("modeMasterVO",
		                commonMasterService.getModeMasterByOrgId(orgId));

		        ResponseDTO responseDTO = createServiceResponse(responseObjectsMap);

		        return ResponseEntity.ok(responseDTO);

		    } catch (Exception e) {
		        ResponseDTO responseDTO = createServiceResponseError(
		                responseObjectsMap,
		                "Mode Master information retrieval failed",
		                e.getMessage());

		        return ResponseEntity.badRequest().body(responseDTO);
		    }
		}
		

}
