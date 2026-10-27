package com.whydigit.fms.service;

import java.io.IOException;
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
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.fms.ResponseDTO.BranchResponseDTO;
import com.whydigit.fms.ResponseDTO.CityResponseDTO;
import com.whydigit.fms.ResponseDTO.CountryResponseDTO;
import com.whydigit.fms.ResponseDTO.FinancialYearResponseDTO;
import com.whydigit.fms.ResponseDTO.GlobalParameterResponseDTO;
import com.whydigit.fms.ResponseDTO.StateDetailResponseDTO;
import com.whydigit.fms.ResponseDTO.StateResponseDTO;
import com.whydigit.fms.dto.CityDTO;
import com.whydigit.fms.dto.CompanyDTO;
import com.whydigit.fms.dto.CountryDTO;
import com.whydigit.fms.dto.CurrencyDTO;
import com.whydigit.fms.dto.DepartmentDTO;
import com.whydigit.fms.dto.DesignationDTO;
import com.whydigit.fms.dto.FinancialYearDTO;
import com.whydigit.fms.dto.GlobalParameterDTO;
import com.whydigit.fms.dto.Role;
import com.whydigit.fms.dto.ScreenNamesDTO;
import com.whydigit.fms.dto.StateDTO;
import com.whydigit.fms.dto.UserProfileInformationDTO;
import com.whydigit.fms.entity.CityVO;
import com.whydigit.fms.entity.CompanyVO;
import com.whydigit.fms.entity.CountryVO;
import com.whydigit.fms.entity.CurrencyVO;
import com.whydigit.fms.entity.DepartmentVO;
import com.whydigit.fms.entity.DesignationVO;
import com.whydigit.fms.entity.EmployeeVO;
import com.whydigit.fms.entity.FinancialYearVO;
import com.whydigit.fms.entity.GlobalParameterVO;
import com.whydigit.fms.entity.ScreenNamesVO;
import com.whydigit.fms.entity.StateVO;
import com.whydigit.fms.entity.UserProfileInformationVO;
import com.whydigit.fms.entity.UserVO;
import com.whydigit.fms.exception.ApplicationException;
import com.whydigit.fms.repo.BranchRepo;
import com.whydigit.fms.repo.CityRepo;
import com.whydigit.fms.repo.CompanyRepo;
import com.whydigit.fms.repo.CountryRepository;
import com.whydigit.fms.repo.CurrencyRepo;
import com.whydigit.fms.repo.DepartmentRepo;
import com.whydigit.fms.repo.DesignationRepo;
import com.whydigit.fms.repo.EmployeeRepo;
import com.whydigit.fms.repo.FinancialYearRepo;
import com.whydigit.fms.repo.GlobalParameterRepo;
import com.whydigit.fms.repo.ScreenNamesRepo;
import com.whydigit.fms.repo.StateRepo;
import com.whydigit.fms.repo.UserBranchAccessRepo;
import com.whydigit.fms.repo.UserClientAccessRepo;
import com.whydigit.fms.repo.UserProfileInformationRepo;
import com.whydigit.fms.repo.UserRepo;
import com.whydigit.fms.util.CryptoUtils;

@Service
public class CommonMasterServiceImpl implements CommonMasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonMasterServiceImpl.class);

	@Autowired
	CountryRepository countryRepo;

	@Autowired
	CurrencyRepo currencyRepo;

	@Autowired
	StateRepo stateRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	CityRepo cityRepo;

	@Autowired
	ScreenNamesRepo screenNamesRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	FinancialYearRepo financialYearRepo;

	@Autowired
	GlobalParameterRepo globalParameterRepo;

	@Autowired
	UserBranchAccessRepo userBranchAccessRepo;

	@Autowired
	UserClientAccessRepo userClientAccessRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	BranchRepo branchRepo;
	@Autowired
	DesignationRepo designationRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	UserProfileInformationRepo userProfileInformationRepo;

	// Country

	@Override
	public List<CountryVO> getAllCountry(Long orgid) {
		return countryRepo.findAll(orgid);
	}

	@Override
	public Optional<CountryVO> getCountryById(Long countryid) {
		return countryRepo.findById(countryid);
	}

	@Override
	public Map<String, Object> createUpdateCountry(CountryDTO countryDTO) throws ApplicationException {

		CountryVO countryVO;
		String message = null;

		if (ObjectUtils.isEmpty(countryDTO.getId())) {
			if (countryRepo.existsByCountryNameAndCountryCodeAndOrgId(countryDTO.getCountryName(),
					countryDTO.getCountryCode(), countryDTO.getOrgId())) {
				String errorMessage = String.format(
						"The CountryName: %s and CountryCode: %s already exists This Organization.",
						countryDTO.getCountryName(), countryDTO.getCountryCode());
				throw new ApplicationException(errorMessage);
			}

			if (countryRepo.existsByCountryNameAndOrgId(countryDTO.getCountryName(), countryDTO.getOrgId())) {
				String errorMessage = String.format("The CountryName: %s already exists This Organization.",
						countryDTO.getCountryName());
				throw new ApplicationException(errorMessage);
			}

			if (countryRepo.existsByCountryCodeAndOrgId(countryDTO.getCountryCode(), countryDTO.getOrgId())) {
				String errorMessage = String.format("The CountryCode: %s already exists This Organization.",
						countryDTO.getCountryCode());
				throw new ApplicationException(errorMessage);
			}
			message = "Country Creation SuccessFully";
		}
		if (countryDTO.getId() != null) {
			// Update existing branch
			countryVO = countryRepo.findById(countryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Branch not found with id: " + countryDTO.getId()));
			countryVO.setUpdatedBy(countryDTO.getCreatedBy());
			if (!countryVO.getCountryCode().equalsIgnoreCase(countryDTO.getCountryCode())) {
				if (countryRepo.existsByCountryCodeAndOrgId(countryDTO.getCountryCode(), countryDTO.getOrgId())) {
					String errorMessage = String.format("The CountryCode: %s already exists This Organization.",
							countryDTO.getCountryCode());
					throw new ApplicationException(errorMessage);
				}
				countryVO.setCountryCode(countryDTO.getCountryCode().toUpperCase());
			}
			if (!countryVO.getCountryName().equalsIgnoreCase(countryDTO.getCountryName())) {
				if (countryRepo.existsByCountryNameAndOrgId(countryDTO.getCountryName(), countryDTO.getOrgId())) {
					String errorMessage = String.format("The CountryName: %s already exists This Organization.",
							countryDTO.getCountryName());
					throw new ApplicationException(errorMessage);
				}
				countryVO.setCountryName(countryDTO.getCountryName().toUpperCase());

			}

		} else {
			// Create new branch
			countryVO = new CountryVO();
			countryVO.setCreatedBy(countryDTO.getCreatedBy());
			countryVO.setUpdatedBy(countryDTO.getCreatedBy());
			message = "Country Creation Failed";
		}

		getCountryVOFromCounytryDTO(countryVO, countryDTO);
		countryRepo.save(countryVO);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("createdCountryVO", countryVO);
		return response;

	}

	private void getCountryVOFromCounytryDTO(CountryVO countryVO, CountryDTO countryDTO) {
		countryVO.setCountryName(countryDTO.getCountryName().toUpperCase());
		countryVO.setCountryCode(countryDTO.getCountryCode().toUpperCase());
		countryVO.setActive(countryDTO.isActive());
		countryVO.setOrgId(countryDTO.getOrgId());
		countryVO.setCancel(countryDTO.isCancel());

	}

	@Override
	public void deleteCountry(Long countryid) {

		countryRepo.deleteById(countryid);
	}

	// State

	@Override
	public List<StateResponseDTO> getAllStateByOrgId(Long orgid) {

	    List<StateVO> stateVOList = stateRepo.findAllByOrgId(orgid);

	    List<StateResponseDTO> stateResponseDTOList = new ArrayList<>();

	    for (StateVO stateVO : stateVOList) {
	        stateResponseDTOList.add(getStateResponseDTO(stateVO));
	    }

	    return stateResponseDTOList;
	}

	@Override
	public Optional<StateResponseDTO> getStateById(Long stateid) {

	    Optional<StateVO> stateVO = stateRepo.findById(stateid);

	    if (stateVO.isPresent()) {
	        return Optional.of(getStateResponseDTO(stateVO.get()));
	    }

	    return Optional.empty();
	}

	@Override
	public List<StateResponseDTO> getStatesByCountry(Long orgid, Long country) {

	    List<StateVO> stateVOList = stateRepo.findByCountry(orgid, country);

	    List<StateResponseDTO> stateResponseDTOList = new ArrayList<>();

	    for (StateVO stateVO : stateVOList) {
	        stateResponseDTOList.add(getStateResponseDTO(stateVO));
	    }

	    return stateResponseDTOList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateState(StateDTO stateDTO) throws ApplicationException {
		StateVO stateVO;
		String message = null;

		if (ObjectUtils.isEmpty(stateDTO.getId())) {
			// Check for existing state by state code, state number, and state name
			if (stateRepo.existsByStateCodeAndOrgId(stateDTO.getStateCode(), stateDTO.getOrgId())) {
				String errorMessage = String.format("The StateCode: %s already exists in This Organization.",
						stateDTO.getStateCode());
				throw new ApplicationException(errorMessage);
			}
			if (stateRepo.existsByStateNumberAndOrgId(stateDTO.getStateNumber(), stateDTO.getOrgId())) {
				String errorMessage = String.format("The StateNumber: %s already exists in This Organization.",
						stateDTO.getStateNumber());
				throw new ApplicationException(errorMessage);
			}
			if (stateRepo.existsByStateNameAndOrgId(stateDTO.getStateName(), stateDTO.getOrgId())) {
				String errorMessage = String.format("The StateName: %s already exists in This Organization.",
						stateDTO.getStateName());
				throw new ApplicationException(errorMessage);
			}

			// Create new state
			stateVO = new StateVO();
			stateVO.setCreatedBy(stateDTO.getCreatedBy());
			stateVO.setUpdatedBy(stateDTO.getCreatedBy());
			message = "State Creation Successfully";
		} else {
			// Update existing state
			stateVO = stateRepo.findById(stateDTO.getId())
					.orElseThrow(() -> new ApplicationException("State not found with id: " + stateDTO.getId()));

			stateVO.setUpdatedBy(stateDTO.getCreatedBy());

			if (!stateVO.getStateCode().equalsIgnoreCase(stateDTO.getStateCode())) {
				if (stateRepo.existsByStateCodeAndOrgId(stateDTO.getStateCode(), stateDTO.getOrgId())) {
					String errorMessage = String.format("The StateCode: %s already exists in This Organization.",
							stateDTO.getStateCode());
					throw new ApplicationException(errorMessage);
				}
				stateVO.setStateCode(stateDTO.getStateCode().toUpperCase());
			}

			if (!stateVO.getStateName().equalsIgnoreCase(stateDTO.getStateName())) {
				if (stateRepo.existsByStateNameAndOrgId(stateDTO.getStateName(), stateDTO.getOrgId())) {
					String errorMessage = String.format("The StateName: %s already exists in This Organization.",
							stateDTO.getStateName());
					throw new ApplicationException(errorMessage);
				}
				stateVO.setStateName(stateDTO.getStateName().toUpperCase());
			}

			if (!stateVO.getStateNumber().equalsIgnoreCase(stateDTO.getStateNumber())) {
				if (stateRepo.existsByStateNumberAndOrgId(stateDTO.getStateNumber(), stateDTO.getOrgId())) {
					String errorMessage = String.format("The StateNumber: %s already exists in This Organization.",
							stateDTO.getStateNumber());
					throw new ApplicationException(errorMessage);
				}
				stateVO.setStateNumber(stateDTO.getStateNumber().toUpperCase());
			}

			message = "State Update Successfully";
		}

		// Map the remaining fields
		getStateVOFromStateDTO(stateVO, stateDTO);

		// Save the entity
		stateVO = stateRepo.save(stateVO);

		// Prepare the response
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("stateVO", getStateResponseDTO(stateVO));

		return response;

	}

	private void getStateVOFromStateDTO(StateVO stateVO, StateDTO stateDTO) {
		stateVO.setStateCode(stateDTO.getStateCode().toUpperCase());
		stateVO.setStateName(stateDTO.getStateName().toUpperCase());
		stateVO.setStateNumber(stateDTO.getStateNumber().toUpperCase());
		CountryVO countryVO = countryRepo.findById(stateDTO.getCountry())
		        .orElseThrow(() -> new RuntimeException("Country Not Found"));

		stateVO.setCountry(countryVO);
		stateVO.setRegion(stateDTO.getRegion().toUpperCase());
		stateVO.setActive(stateDTO.isActive());
		stateVO.setOrgId(stateDTO.getOrgId());
		// stateVO.setDupchk(stateDTO.getOrgId() + stateDTO.getStateCode() +
		// stateDTO.getStateName());
	}
	
	private StateResponseDTO getStateResponseDTO(StateVO stateVO) {

	    StateResponseDTO stateResponseDTO = new StateResponseDTO();

	    stateResponseDTO.setId(stateVO.getId());
	    stateResponseDTO.setStateCode(stateVO.getStateCode());
	    stateResponseDTO.setStateName(stateVO.getStateName());
	    stateResponseDTO.setRegion(stateVO.getRegion());
	    stateResponseDTO.setStateNumber(stateVO.getStateNumber());
	    stateResponseDTO.setActive(stateVO.getActive());
	    stateResponseDTO.setCreatedBy(stateVO.getCreatedBy());
	    stateResponseDTO.setUpdatedBy(stateVO.getUpdatedBy());
	    stateResponseDTO.setOrgId(stateVO.getOrgId());

	    if (stateVO.getCountry() != null) {

	        CountryResponseDTO countryResponseDTO = new CountryResponseDTO();

	        countryResponseDTO.setId(stateVO.getCountry().getId());
	        countryResponseDTO.setCountry(stateVO.getCountry().getCountryName());
	        countryResponseDTO.setCountryCode(stateVO.getCountry().getCountryCode());

	        stateResponseDTO.setCountry(countryResponseDTO);
	    }

	    return stateResponseDTO;
	}

	@Override
	public void deleteState(Long countryid) {
		stateRepo.deleteById(countryid);
	}

	// City
	@Override
	public List<CityResponseDTO> getAllCityByOrgId(Long orgid) {

	    List<CityVO> cityVOList = cityRepo.findAll(orgid);

	    List<CityResponseDTO> cityResponseDTOList = new ArrayList<>();

	    for (CityVO cityVO : cityVOList) {
	        cityResponseDTOList.add(getCityResponseDTO(cityVO));
	    }

	    return cityResponseDTOList;
	}

	@Override
	public List<CityResponseDTO> getCityByOrgIdAndstate(Long orgid, Long state) {

	    List<CityVO> cityVOList = cityRepo.getAllCitiesByState(orgid, state);

	    List<CityResponseDTO> cityResponseDTOList = new ArrayList<>();

	    for (CityVO cityVO : cityVOList) {
	        cityResponseDTOList.add(getCityResponseDTO(cityVO));
	    }

	    return cityResponseDTOList;
	}

	@Override
	public Optional<CityResponseDTO> getCityById(Long cityid) {

	    Optional<CityVO> cityVO = cityRepo.findById(cityid);

	    if (cityVO.isPresent()) {
	        return Optional.of(getCityResponseDTO(cityVO.get()));
	    }

	    return Optional.empty();
	}
	@Override
	@Transactional
	public Map<String, Object> createUpdateCity(CityDTO cityDTO) throws ApplicationException {

	    CityVO cityVO;
	    String message;

	    if (ObjectUtils.isEmpty(cityDTO.getId())) {

	        if (cityRepo.existsByCityCodeAndOrgId(cityDTO.getCityCode(), cityDTO.getOrgId())) {
	            String errorMessage = String.format(
	                    "The CityCode: %s already exists in this organization.",
	                    cityDTO.getCityCode());
	            throw new ApplicationException(errorMessage);
	        }

	        if (cityRepo.existsByCityNameAndOrgId(cityDTO.getCityName(), cityDTO.getOrgId())) {
	            String errorMessage = String.format(
	                    "The CityName: %s already exists in this organization.",
	                    cityDTO.getCityName());
	            throw new ApplicationException(errorMessage);
	        }

	        // Create new city
	        cityVO = new CityVO();
	        cityVO.setCreatedBy(cityDTO.getCreatedBy());
	        cityVO.setUpdatedBy(cityDTO.getCreatedBy());

	        message = "City Created Successfully";

	    } else {

	        // Update existing city
	        cityVO = cityRepo.findById(cityDTO.getId())
	                .orElseThrow(() -> new ApplicationException(
	                        "City not found with id: " + cityDTO.getId()));

	        cityVO.setUpdatedBy(cityDTO.getCreatedBy());

	        if (!cityVO.getCityCode().equalsIgnoreCase(cityDTO.getCityCode())) {
	            if (cityRepo.existsByCityCodeAndOrgId(cityDTO.getCityCode(), cityDTO.getOrgId())) {
	                String errorMessage = String.format(
	                        "The CityCode: %s already exists in this organization.",
	                        cityDTO.getCityCode());
	                throw new ApplicationException(errorMessage);
	            }

	            cityVO.setCityCode(cityDTO.getCityCode().toUpperCase());
	        }

	        if (!cityVO.getCityName().equalsIgnoreCase(cityDTO.getCityName())) {
	            if (cityRepo.existsByCityNameAndOrgId(cityDTO.getCityName(), cityDTO.getOrgId())) {
	                String errorMessage = String.format(
	                        "The CityName: %s already exists in this organization.",
	                        cityDTO.getCityName());
	                throw new ApplicationException(errorMessage);
	            }

	            cityVO.setCityName(cityDTO.getCityName().toUpperCase());
	        }

	        message = "City Updated Successfully";
	    }

	    // Map DTO to entity
	    getCityVOFromCityDTO(cityVO, cityDTO);

	    // Save the entity
	    CityVO savedCity = cityRepo.save(cityVO);

	    // Prepare the response
	    Map<String, Object> response = new HashMap<>();
	    response.put("message", message);
	    response.put("cityVO", getCityResponseDTO(savedCity));

	    return response;
	}
	
	private void getCityVOFromCityDTO(CityVO cityVO, CityDTO cityDTO) {

	    cityVO.setCityCode(cityDTO.getCityCode().toUpperCase());
	    cityVO.setCityName(cityDTO.getCityName().toUpperCase());

	    CountryVO countryVO = countryRepo.findById(cityDTO.getCountry())
	            .orElseThrow(() -> new RuntimeException("Country Not Found"));

	    cityVO.setCountry(countryVO);

	    StateVO stateVO = stateRepo.findById(cityDTO.getState())
	            .orElseThrow(() -> new RuntimeException("State Not Found"));

	    cityVO.setState(stateVO);

	    cityVO.setActive(cityDTO.isActive());
	    cityVO.setOrgId(cityDTO.getOrgId());
	}
	
	private CityResponseDTO getCityResponseDTO(CityVO cityVO) {

	    CityResponseDTO cityResponseDTO = new CityResponseDTO();

	    cityResponseDTO.setId(cityVO.getId());
	    cityResponseDTO.setCityCode(cityVO.getCityCode());
	    cityResponseDTO.setCityName(cityVO.getCityName());
	    cityResponseDTO.setActive(cityVO.getActive());
	    cityResponseDTO.setCreatedBy(cityVO.getCreatedBy());
	    cityResponseDTO.setUpdatedBy(cityVO.getUpdatedBy());
	    cityResponseDTO.setOrgId(cityVO.getOrgId());
	    cityResponseDTO.setCancel(cityVO.getCancel());

	    // Map CountryResponseDTO
	    if (cityVO.getCountry() != null) {

	        CountryResponseDTO countryResponseDTO = new CountryResponseDTO();

	        countryResponseDTO.setId(cityVO.getCountry().getId());
	        countryResponseDTO.setCountry(cityVO.getCountry().getCountryName());
	        countryResponseDTO.setCountryCode(cityVO.getCountry().getCountryCode());

	        cityResponseDTO.setCountry(countryResponseDTO);
	    }

	    // Map StateResponseDTO
	    if (cityVO.getState() != null) {

	        StateDetailResponseDTO stateResponseDTO = new StateDetailResponseDTO();

	        stateResponseDTO.setId(cityVO.getState().getId());
	        stateResponseDTO.setStateCode(cityVO.getState().getStateCode());
	        stateResponseDTO.setStateName(cityVO.getState().getStateName());
	       

	        cityResponseDTO.setState(stateResponseDTO);
	    }

	    return cityResponseDTO;
	}
	@Override
	public void deleteCity(Long cityid) {
		cityRepo.deleteById(cityid);
	}

	// Company

	@Override
	public List<CompanyVO> getAllCompany() {
		return companyRepo.findAll();
	}

	@Override
	public List<CompanyVO> getCompanyById(Long companyid) {
		return companyRepo.findByCompany(companyid);
	}

	@Override
	@Transactional
	public CompanyVO createCompany(CompanyDTO companyDTO) throws Exception {

		if (companyRepo.existsByCompanyCodeAndCompanyNameAndEmployeeCodeAndEmailAndPhoneAndId(
				companyDTO.getCompanyCode(), companyDTO.getCompanyName(), companyDTO.getEmployeeCode(),
				companyDTO.getEmail(), companyDTO.getPhone(), companyDTO.getId())) {

			String errorMessage = String.format(
					"The CompanyCode : %s And CompanyName : %s And EmployeeCode : %s And Email : %s And PhoneNumber : %s Already Exists"
							+ "This Organization",
					companyDTO.getCompanyCode(), companyDTO.getCompanyName(), companyDTO.getEmployeeCode(),
					companyDTO.getEmail(), companyDTO.getPhone());
			throw new ApplicationException(errorMessage);
		}

		if (companyRepo.existsByCompanyCodeAndId(companyDTO.getCompanyCode(), companyDTO.getId())) {

			String errorMessage = String.format("The CompanyCode : %s Already Exists This Organization",
					companyDTO.getCompanyCode());
			throw new ApplicationException(errorMessage);
		}

		if (companyRepo.existsByCompanyNameAndId(companyDTO.getCompanyName(), companyDTO.getId())) {
			String errorMessage = String.format("The CompanyName : %s Already Exists This Organization",
					companyDTO.getCompanyName());
			throw new ApplicationException(errorMessage);
		}

		if (companyRepo.existsByEmployeeCodeAndId(companyDTO.getEmployeeCode(), companyDTO.getId())) {
			String errorMessage = String.format("The EmployeeCode : %s Already Exists This Organization",
					companyDTO.getEmployeeCode());
			throw new ApplicationException(errorMessage);
		}

		if (companyRepo.existsByEmailAndId(companyDTO.getEmail(), companyDTO.getId())) {
			String errorMessage = String.format("The Email : %s Already Exists This Organization",
					companyDTO.getEmail());
			throw new ApplicationException(errorMessage);
		}

		if (companyRepo.existsByPhoneAndId(companyDTO.getPhone(), companyDTO.getId())) {
			String errorMessage = String.format("The PhoneNumber : %s Already Exists This Organization",
					companyDTO.getPhone());
			throw new ApplicationException(errorMessage);
		}

		CompanyVO companyVO = new CompanyVO();
		getCompanyVOFromCompanyDTO(companyVO, companyDTO);
		companyRepo.save(companyVO);

		EmployeeVO employeeVO = new EmployeeVO();
		employeeVO.setEmployeeName(companyVO.getEmployeeName());
		employeeVO.setEmployeeCode(companyVO.getEmployeeCode());
		employeeVO.setActive(true);
		employeeVO.setOrgId(companyVO.getId());
		employeeRepo.save(employeeVO);

		UserVO userVO = new UserVO();
		userVO.setUserName(companyVO.getEmployeeCode());
		userVO.setEmployeeName(companyVO.getEmployeeName());
		userVO.setEmail(companyVO.getEmail());
		userVO.setMobileNo(companyVO.getPhone());
		userVO.setRole(Role.ROLE_USER);
		userVO.setUserType("admin");
		userVO.setOrgId(companyVO.getId());
		userVO.setCreatedby(companyVO.getCreatedBy());
		userVO.setUpdatedby(companyVO.getCreatedBy());
		userVO.setActive(true);
		userVO.setLoginStatus(false);
		userVO.setCompanyVO(companyVO);

		try {
			userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(companyDTO.getPassword())));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException("Unable To Encode Password");
		}

		userRepo.save(userVO);

		return companyVO;
	}

	private void getCompanyVOFromCompanyDTO(CompanyVO companyVO, CompanyDTO companyDTO) {
		companyVO.setCompanyCode(companyDTO.getCompanyCode());
		companyVO.setCompanyName(companyDTO.getCompanyName());
		companyVO.setCountry(companyDTO.getCountry());
		companyVO.setCurrency(companyDTO.getCurrency());
		companyVO.setMainCurrency(companyDTO.getMainCurrency());
		companyVO.setAddress(companyDTO.getAddress());
		companyVO.setZip(companyDTO.getZip());
		companyVO.setCity(companyDTO.getCity());
		companyVO.setState(companyDTO.getState());
		companyVO.setPhone(companyDTO.getPhone());
		companyVO.setEmail(companyDTO.getEmail());
		companyVO.setWebSite(companyDTO.getWebSite());
		companyVO.setNote(companyDTO.getNote());
		companyVO.setEmployeeCode(companyDTO.getEmployeeCode());
		companyVO.setEmployeeName(companyDTO.getEmployeeName());
		companyVO.setCreatedBy(companyDTO.getCreatedBy());
		companyVO.setUpdatedBy(companyDTO.getCreatedBy());
		companyVO.setActive(companyDTO.isActive());
		companyVO.setCancel(companyDTO.isCancel());
		companyVO.setGst(companyDTO.getGst());
		companyVO.setCeo(companyDTO.getCeo());

		try {
			companyVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(companyDTO.getPassword())));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException("Unable To Encode Password");
		}
	}

	public CompanyVO updateCompany(CompanyDTO companyDTO) throws ApplicationException {

		if (ObjectUtils.isEmpty(companyDTO.getId())) {
			throw new ApplicationException("Invalid Company Id");
		}

		CompanyVO companyVO = companyRepo.findById(companyDTO.getId())
				.orElseThrow(() -> new ApplicationException("Company not found for Id: " + companyDTO.getId()));

		mapCompanyDTOToCompanyVO(companyVO, companyDTO);

		return companyRepo.save(companyVO);
	}

	private void mapCompanyDTOToCompanyVO(CompanyVO companyVO, CompanyDTO companyDTO) {
		companyVO.setCompanyCode(companyDTO.getCompanyCode());
		companyVO.setCompanyName(companyDTO.getCompanyName());
		companyVO.setCountry(companyDTO.getCountry());
		companyVO.setCurrency(companyDTO.getCurrency());
		companyVO.setMainCurrency(companyDTO.getMainCurrency());
		companyVO.setAddress(companyDTO.getAddress());
		companyVO.setZip(companyDTO.getZip());
		companyVO.setCity(companyDTO.getCity());
		companyVO.setState(companyDTO.getState());
		companyVO.setPhone(companyDTO.getPhone());
		companyVO.setEmail(companyDTO.getEmail());
		companyVO.setWebSite(companyDTO.getWebSite());
		companyVO.setNote(companyDTO.getNote());
//		companyVO.setEmployeeCode(companyDTO.getEmployeeCode());
//		companyVO.setEmployeeName(companyDTO.getEmployeeName());
		companyVO.setCreatedBy(companyDTO.getCreatedBy());
		companyVO.setUpdatedBy(companyDTO.getUpdatedBy());
		companyVO.setActive(companyDTO.isActive());
		companyVO.setCancel(companyDTO.isCancel());
		companyVO.setRole(companyDTO.getRole());
		companyVO.setGst(companyDTO.getGst());
		companyVO.setCeo(companyDTO.getCeo());
	}

	@Override
	public void deleteCompany(Long companyid) {
		companyRepo.deleteById(companyid);
	}

// Currency 
	@Override
	public List<CurrencyVO> getAllCurrency(Long orgid) {

		return currencyRepo.findAll(orgid);
	}

	@Override
	public Optional<CurrencyVO> getCurrencyById(Long currencyid) {

		return currencyRepo.findById(currencyid);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateCurrency(CurrencyDTO currencyDTO) throws ApplicationException {

		CurrencyVO currencyVO;
		String message = null;

		if (ObjectUtils.isEmpty(currencyDTO.getId())) {
			if (currencyRepo.existsByCurrencyAndOrgId(currencyDTO.getCurrency(), currencyDTO.getOrgId())) {
				String errorMessage = String.format("This Currency:%s Already Exists in This Organization.",
						currencyDTO.getCurrency());
				throw new ApplicationException(errorMessage);
			}
			if (currencyRepo.existsByCurrencySymbolAndOrgId(currencyDTO.getCurrencySymbol(), currencyDTO.getOrgId())) {
				String errorMessage = String.format("This CurrencySymbol:%s Already Exists in This Organization.",
						currencyDTO.getCurrencySymbol());
				throw new ApplicationException(errorMessage);
			}
			if (currencyRepo.existsBySubCurrencyAndOrgId(currencyDTO.getSubCurrency(), currencyDTO.getOrgId())) {
				String errorMessage = String.format("This SubCurrency:%s Already Exists in This Organization.",
						currencyDTO.getSubCurrency());
				throw new ApplicationException(errorMessage);
			}

			// Create new currency
			currencyVO = new CurrencyVO();
			currencyVO.setCreatedBy(currencyDTO.getCreatedBy());
			currencyVO.setUpdatedBy(currencyDTO.getCreatedBy());
			message = "Currency Created Successfully";
		} else {
			// Update existing currency
			currencyVO = currencyRepo.findById(currencyDTO.getId()).orElseThrow(
					() -> new ApplicationException("This Id Is Not Found Any Information: " + currencyDTO.getId()));
			currencyVO.setUpdatedBy(currencyDTO.getCreatedBy());

			if (!currencyVO.getCurrency().equalsIgnoreCase(currencyDTO.getCurrency())) {
				if (currencyRepo.existsByCurrencyAndOrgId(currencyDTO.getCurrency(), currencyDTO.getOrgId())) {
					String errorMessage = String.format("This Currency:%s Already Exists in This Organization.",
							currencyDTO.getCurrency());
					throw new ApplicationException(errorMessage);
				}
				currencyVO.setCurrency(currencyDTO.getCurrency().toUpperCase());
			}
			if (!currencyVO.getSubCurrency().equalsIgnoreCase(currencyDTO.getSubCurrency())) {
				if (currencyRepo.existsBySubCurrencyAndOrgId(currencyDTO.getSubCurrency(), currencyDTO.getOrgId())) {
					String errorMessage = String.format("This SubCurrency:%s Already Exists in This Organization.",
							currencyDTO.getSubCurrency());
					throw new ApplicationException(errorMessage);
				}
				currencyVO.setSubCurrency(currencyDTO.getSubCurrency().toUpperCase());
			}
			if (!currencyVO.getCurrencySymbol().equalsIgnoreCase(currencyDTO.getCurrencySymbol())) {
				if (currencyRepo.existsByCurrencySymbolAndOrgId(currencyDTO.getCurrencySymbol(),
						currencyDTO.getOrgId())) {
					String errorMessage = String.format("This CurrencySymbol:%s Already Exists in This Organization.",
							currencyDTO.getCurrencySymbol());
					throw new ApplicationException(errorMessage);
				}
				currencyVO.setCurrencySymbol(currencyDTO.getCurrencySymbol().toUpperCase());
			}
			message = "Currency Updated Successfully";
		}

		getCurrencyVOFromCurrencyDTO(currencyVO, currencyDTO);
		currencyRepo.save(currencyVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("currencyVO", currencyVO);
		return response;
	}

	private void getCurrencyVOFromCurrencyDTO(CurrencyVO currencyVO, CurrencyDTO currencyDTO) {
		currencyVO.setCurrency(currencyDTO.getCurrency().toUpperCase());
		currencyVO.setSubCurrency(currencyDTO.getSubCurrency().toUpperCase());
		currencyVO.setCurrencySymbol(currencyDTO.getCurrencySymbol().toUpperCase());
		currencyVO.setActive(currencyDTO.isActive());
		currencyVO.setCancel(currencyDTO.isCancel());
		currencyVO.setCountry(currencyDTO.getCountry().toUpperCase());
		currencyVO.setOrgId(currencyDTO.getOrgId());
	}

	@Override
	public void deleteCurrency(Long currencyid) {
		currencyRepo.deleteById(currencyid);

	}

	// Global Parametre

	@Override
	public Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(Long orgid, String username) {

		return globalParameterRepo.findGlobalParamByOrgIdAndUserName(orgid, username);
	}

	@Override
	public Set<Object[]> getWarehouseNameByOrgIdAndBranchAndClient(Long orgid, String branch, String client) {
		return globalParameterRepo.findWarehouseNameByOrgIdAndBranchAndClient(orgid, branch, client);
	}

	// Change Global Parameter or update Parameters
	@Override
	public GlobalParameterResponseDTO updateGlobaParameter(GlobalParameterDTO globalParameterDTO) {

		GlobalParameterVO existingRecord = globalParameterRepo.findGlobalParam(globalParameterDTO.getOrgId(),
				globalParameterDTO.getUserid());

		GlobalParameterVO savedRecord;

		if (existingRecord != null) {

			// Update existing record
			existingRecord.setOrgId(globalParameterDTO.getOrgId());
			existingRecord.setUserid(globalParameterDTO.getUserid());

			existingRecord.setBranch(branchRepo.findById(globalParameterDTO.getBranch())
					.orElseThrow(() -> new RuntimeException("Branch Not Found")));

			existingRecord.setFinancialYear(financialYearRepo.findById(globalParameterDTO.getFinancialYear())
					.orElseThrow(() -> new RuntimeException("Financial Year Not Found")));

			savedRecord = globalParameterRepo.save(existingRecord);

		} else {

			// Create new record
			GlobalParameterVO globalParameterVO = new GlobalParameterVO();

			globalParameterVO.setOrgId(globalParameterDTO.getOrgId());
			globalParameterVO.setUserid(globalParameterDTO.getUserid());

			globalParameterVO.setBranch(branchRepo.findById(globalParameterDTO.getBranch())
					.orElseThrow(() -> new RuntimeException("Branch Not Found")));

			globalParameterVO.setFinancialYear(financialYearRepo.findById(globalParameterDTO.getFinancialYear())
					.orElseThrow(() -> new RuntimeException("Financial Year Not Found")));

			savedRecord = globalParameterRepo.save(globalParameterVO);
		}

		return convertToGlobalParameterResponse(savedRecord);
	}

	private GlobalParameterResponseDTO convertToGlobalParameterResponse(GlobalParameterVO vo) {

		GlobalParameterResponseDTO dto = new GlobalParameterResponseDTO();

		dto.setId(vo.getId());
		dto.setOrgId(vo.getOrgId());
		dto.setUserid(vo.getUserid());

		// Branch mapping
		if (vo.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranch(vo.getBranch().getBranch());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());

			dto.setBranch(branchDTO);
		}

		// Financial year mapping
		if (vo.getFinancialYear() != null) {

			FinancialYearResponseDTO financialYearDTO = new FinancialYearResponseDTO();

			financialYearDTO.setId(vo.getFinancialYear().getId());
			financialYearDTO.setFinYear(vo.getFinancialYear().getFinYear());

			dto.setFinancialYear(financialYearDTO);
		}

		return dto;
	}

	// get access Branch
	@Override
	public List<Map<String, Object>> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(
	        Long orgid, String userName) {

	    List<Object[]> result = userBranchAccessRepo
	            .findGlobalParametersBranchByUserName(orgid, userName);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] row : result) {

	        Map<String, Object> map = new HashMap<>();

	        map.put("branchId", ((Number) row[0]).longValue());
	        map.put("branch", row[1]);
	        map.put("branchCode", row[2]);

	        responseList.add(map);
	    }

	    return responseList;
	}

//	@Override
//	public Set<Object[]> getAllAccessCustomerForLogin(Long orgid, String userName, String branchcode) {
//
//		return customerRepo.findAllAccessCustomerByUserName(orgid, userName, branchcode);
//	}
//
//	@Override
//	public Set<Object[]> getAllAccessClientForLogin(Long orgid, String userName, String branchcode, String customer) {
//		// TODO Auto-generated method stub
//		return clientRepo.findAllAccessClientByUserName(orgid, userName, branchcode, customer);
//	}

	@Override
	public Map<String, Object> createUpdateScreenNames(ScreenNamesDTO screenNamesDTO) throws ApplicationException {
		ScreenNamesVO screenNamesVO = new ScreenNamesVO();
		String message = null;

		if (ObjectUtils.isEmpty(screenNamesDTO.getId())) {

			// Validate if responsibility already exists by responsibility name
			if (screenNamesRepo.existsByScreenName(screenNamesDTO.getScreenName())) {
				throw new ApplicationException("Screen Name already exists");
			}
			if (screenNamesRepo.existsByScreenCode(screenNamesDTO.getScreenCode())) {
				throw new ApplicationException("Screen Code already exists");
			}

			screenNamesVO.setCreatedBy(screenNamesDTO.getCreatedBy());
			screenNamesVO.setUpdatedBy(screenNamesDTO.getCreatedBy());
			screenNamesVO.setActive(screenNamesDTO.isActive());
			screenNamesVO.setScreenCode(screenNamesDTO.getScreenCode());
			screenNamesVO.setScreenName(screenNamesDTO.getScreenName());
			// Set the values from screenNamesDTO to responsibilityVO
			message = "ScreenName Created successfully";

		} else {

			// Retrieve the existing ResponsibilityVO from the repository
			screenNamesVO = screenNamesRepo.findById(screenNamesDTO.getId())
					.orElseThrow(() -> new ApplicationException("Screen Name not found"));

			// Validate and update unique fields if changed
			if (!screenNamesVO.getScreenName().equalsIgnoreCase(screenNamesDTO.getScreenName())) {
				if (screenNamesRepo.existsByScreenName(screenNamesDTO.getScreenName())) {
					throw new ApplicationException("Screen Name already exists");
				}
				screenNamesVO.setScreenName(screenNamesDTO.getScreenName());
			}
			if (!screenNamesVO.getScreenCode().equalsIgnoreCase(screenNamesDTO.getScreenCode())) {
				if (screenNamesRepo.existsByScreenCode(screenNamesDTO.getScreenCode())) {
					throw new ApplicationException("Screen Code already exists");
				}
				screenNamesVO.setScreenCode(screenNamesDTO.getScreenCode());
			}
			screenNamesVO.setActive(screenNamesDTO.isActive());
			screenNamesVO.setUpdatedBy(screenNamesDTO.getCreatedBy());
			// Update the remaining fields from screenNamesDTO to responsibilityVO
			message = "ScreenName Updated successfully";
		}

		screenNamesRepo.save(screenNamesVO);
		Map<String, Object> response = new HashMap<>();
		response.put("screenNamesVO", screenNamesVO);
		response.put("message", message);
		return response;
	}

	@Override
	public List<ScreenNamesVO> getAllScreenNames() {

		return screenNamesRepo.findAll();
	}

	@Override
	public ScreenNamesVO getScreenNamesById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Company Id");
		}

		ScreenNamesVO screenNamesVO = screenNamesRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Screen Name not found for Id: " + id));

		return screenNamesVO;
	}

	// Designation

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> createUpdateDesignation(DesignationDTO designationDTO) throws ApplicationException {
		DesignationVO designationVO;
		String messages = null;
		if (ObjectUtils.isEmpty(designationDTO.getId())) {
			if (designationRepo.existsByDesignationAndOrgId(designationDTO.getDesignation(),
					designationDTO.getOrgId())) {
				String errorMessage = String.format("This Designation:%s Is Already Exists This Organization .",
						designationDTO.getDesignation());
				throw new ApplicationException(errorMessage);
			}
			messages = "Designation Creation Suceesfully .";
		}
		if (designationDTO.getId() != null) {
			designationVO = designationRepo.findById(designationDTO.getId()).orElseThrow(
					() -> new ApplicationException("This Id Not Found Any Information ." + designationDTO.getId()));
			designationVO.setUpdatedBy(designationDTO.getCreatedBy());
			if (!designationVO.getDesignation().equalsIgnoreCase(designationDTO.getDesignation())) {
				if (designationRepo.existsByDesignationAndOrgId(designationDTO.getDesignation(),
						designationDTO.getOrgId())) {
					String errorMessage = String.format("This Designation:%s Is Already Exists This Organization .",
							designationDTO.getDesignation());
					throw new ApplicationException(errorMessage);
				}
				designationVO.setDesignation(designationDTO.getDesignation());
				messages = "Designation Updation Suceesfully .";
			}
		} else {
			designationVO = new DesignationVO();
			designationVO.setCreatedBy(designationDTO.getCreatedBy());
			designationVO.setUpdatedBy(designationDTO.getCreatedBy());
		}

		getDesignationVOFromDesignationDTO(designationVO, designationDTO);
		designationRepo.save(designationVO);
		Map<String, Object> response = new HashMap<>();
		response.put("designationVO", designationVO);
		response.put("messages", messages);
		return response;
	}

	private void getDesignationVOFromDesignationDTO(DesignationVO designationVO, DesignationDTO designationDTO) {
		designationVO.setDesignation(designationDTO.getDesignation().toUpperCase());
		designationVO.setActive(designationDTO.isActive());
		designationVO.setCancel(designationDTO.isCancel());
		designationVO.setOrgId(designationDTO.getOrgId());

	}

	@Override
	public List<DesignationVO> getAllDesignation() {
		return designationRepo.findAll();
	}

	@Override
	public List<DesignationVO> getAllDesignationByOrgId(Long OrdId) {
		return designationRepo.getAllDesignationByOrgId(OrdId);
	}

	@Override
	public Optional<DesignationVO> getAllDesignationById(Long id) {
		// TODO Auto-generated method stub
		return designationRepo.getAllDesignationById(id);
	}

	@Override
	public Map<String, Object> createUpdateFinYear(FinancialYearDTO financialYearDTO) throws ApplicationException {
		FinancialYearVO financialYearVO = null;
		String message;

		if (ObjectUtils.isEmpty(financialYearDTO.getId())) {
			if (financialYearRepo.existsByFinYearAndOrgId(financialYearDTO.getFinYear(), financialYearDTO.getOrgId())) {
				String errorMessage = String.format("ThiS finyear:%s Already Exists This Organization .",
						financialYearDTO.getFinYear());
				throw new ApplicationException(errorMessage);
			}

			if (financialYearRepo.existsByFinYearIdentifierAndOrgId(financialYearDTO.getFinYearIdentifier(),
					financialYearDTO.getOrgId())) {
				String errorMessage = String.format("ThiS finyearidentifier:%s Already Exists This Organization .",
						financialYearDTO.getFinYearIdentifier());
				throw new ApplicationException(errorMessage);
			}

			if (financialYearRepo.existsByFinYearIdAndOrgId(financialYearDTO.getFinYearId(),
					financialYearDTO.getOrgId())) {
				String errorMessage = String.format("ThiS finyearid:%s Already Exists This Organization .",
						financialYearDTO.getFinYearId());
				throw new ApplicationException(errorMessage);
			}

			financialYearVO = new FinancialYearVO();
			financialYearVO.setCreatedBy(financialYearDTO.getCreatedBy());
			financialYearVO.setUpdatedBy(financialYearDTO.getCreatedBy());
			message = "Financial Year Creation Successfully";

		} else {
			financialYearVO = financialYearRepo.findById(financialYearDTO.getId())
					.orElseThrow(() -> new ApplicationException(String
							.format("This Id Is Not Found Any Information, Invalid Id: %s", financialYearDTO.getId())));

			if (financialYearVO.getFinYear() != financialYearDTO.getFinYear()) {
				if (financialYearRepo.existsByFinYearAndOrgId(financialYearDTO.getFinYear(),
						financialYearDTO.getOrgId())) {
					String errorMessage = String.format("This finyear: %s already exists for this organization.",
							financialYearDTO.getFinYear());
					throw new ApplicationException(errorMessage);
				}
				financialYearVO.setFinYear(financialYearDTO.getFinYear());
			}

			if (!financialYearVO.getFinYearIdentifier().equals(financialYearDTO.getFinYearIdentifier())) {
				if (financialYearRepo.existsByFinYearIdentifierAndOrgId(financialYearDTO.getFinYearIdentifier(),
						financialYearDTO.getOrgId())) {
					String errorMessage = String.format(
							"This finyearIdentifier: %s already exists for this organization.",
							financialYearDTO.getFinYearIdentifier());
					throw new ApplicationException(errorMessage);
				}
				financialYearVO.setFinYearIdentifier(financialYearDTO.getFinYearIdentifier());
			}

			if (financialYearVO.getFinYearId() != financialYearDTO.getFinYearId()) {
				if (financialYearRepo.existsByFinYearIdAndOrgId(financialYearDTO.getFinYearId(),
						financialYearDTO.getOrgId())) {
					String errorMessage = String.format("This finyearId: %s already exists for this organization.",
							financialYearDTO.getFinYearId());
					throw new ApplicationException(errorMessage);
				}
				financialYearVO.setFinYearId(financialYearDTO.getFinYearId());
			}

			financialYearVO.setUpdatedBy(financialYearDTO.getCreatedBy());
			message = "Financial Year Updation Successfully";

		}
		getFinancialYearVOFromFinancialYearDTO(financialYearVO, financialYearDTO);
		financialYearRepo.save(financialYearVO);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("financialYearVO", financialYearVO);
		response.put("message", response);
		return response;

	}

	private void getFinancialYearVOFromFinancialYearDTO(FinancialYearVO financialYearVO,
			FinancialYearDTO financialYearDTO) {
		financialYearVO.setFinYear(financialYearDTO.getFinYear());
		financialYearVO.setFinYearId(financialYearDTO.getFinYearId());
		financialYearVO.setFinYearIdentifier(financialYearDTO.getFinYearIdentifier());
		financialYearVO.setStartDate(financialYearDTO.getStartDate());
		financialYearVO.setEndDate(financialYearDTO.getEndDate());
		financialYearVO.setCurrentFinYear(financialYearDTO.isCurrentFinYear());
		financialYearVO.setClosed(financialYearDTO.isClosed());
		financialYearVO.setOrgId(financialYearDTO.getOrgId());
		financialYearVO.setActive(financialYearDTO.isActive());
	}

	@Override
	public List<FinancialYearVO> getAllActiveFInYear(Long orgId) {
		return financialYearRepo.findAllActiveFinYear(orgId);
	}

	@Override
	public List<FinancialYearVO> getAllFInYearByOrgId(Long orgId) {
		return financialYearRepo.findFinancialYearByOrgId(orgId);
	}

	@Override
	public Optional<FinancialYearVO> getAllFInYearById(Long id) {
		return financialYearRepo.findById(id);
	}

	// Department

	@Override
	public Map<String, Object> createUpdateDepartment(DepartmentDTO departmentDTO) throws ApplicationException {
		DepartmentVO departmentVO;
		String message = null;
		if (ObjectUtils.isEmpty(departmentDTO.getId())) {

			if (departmentRepo.existsByCodeAndOrgId(departmentDTO.getCode(), departmentDTO.getOrgId())) {
				String errorMessage = String.format("ThiS DeptCode:%s Already Exists This Organization .",
						departmentDTO.getCode());
				throw new ApplicationException(errorMessage);
			}
			if (departmentRepo.existsByDepartmentNameAndOrgId(departmentDTO.getDepartmentName(),
					departmentDTO.getOrgId())) {
				String errorMessage = String.format("ThiS DepartmentName:%s Already Exists This Organization .",
						departmentDTO.getDepartmentName());
				throw new ApplicationException(errorMessage);
			}

			departmentVO = new DepartmentVO();
			departmentVO.setCreatedBy(departmentDTO.getCreatedBy());
			departmentVO.setUpdatedBy(departmentDTO.getCreatedBy());
			message = "Department Creation Successfully";
		} else {
			departmentVO = departmentRepo.findById(departmentDTO.getId()).orElseThrow(() -> new ApplicationException(
					"This Id Is Not Found Any Information,Invalid Id . " + departmentDTO.getId()));

			if (!departmentVO.getCode().equalsIgnoreCase(departmentDTO.getCode())) {

				if (departmentRepo.existsByCodeAndOrgId(departmentDTO.getCode(), departmentDTO.getOrgId())) {
					String errorMessage = String.format("ThiS DeptCode:%s Already Exists This Organization .",
							departmentDTO.getCode());
					throw new ApplicationException(errorMessage);
				}
				departmentVO.setCode(departmentDTO.getCode());
			}
			if (!departmentVO.getDepartmentName().equalsIgnoreCase(departmentDTO.getDepartmentName())) {
				if (departmentRepo.existsByDepartmentNameAndOrgId(departmentDTO.getDepartmentName(),
						departmentDTO.getOrgId())) {
					String errorMessage = String.format("ThiS DepartmentName:%s Already Exists This Organization .",
							departmentDTO.getDepartmentName());
					throw new ApplicationException(errorMessage);
				}
				if (departmentRepo.existsByDepartmentNameAndOrgId(departmentDTO.getDepartmentName(),
						departmentDTO.getOrgId())) {
					String errorMessage = String.format("ThiS DepartmentName:%s Already Exists This Organization .",
							departmentDTO.getDepartmentName());
					throw new ApplicationException(errorMessage);
				}
				departmentVO.setDepartmentName(departmentDTO.getDepartmentName());
			}

			departmentVO.setUpdatedBy(departmentDTO.getCreatedBy());
			message = "Department Updation Successfully";
		}

		getDepartmentVOFromDepartmentDTO(departmentVO, departmentDTO);
		departmentRepo.save(departmentVO);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("departmentVO", departmentVO);
		return response;

	}

	private void getDepartmentVOFromDepartmentDTO(DepartmentVO departmentVO, DepartmentDTO departmentDTO) {
		departmentVO.setDepartmentName(departmentDTO.getDepartmentName());
		departmentVO.setCode(departmentDTO.getCode());
		departmentVO.setActive(departmentDTO.isActive());
		departmentVO.setOrgId(departmentDTO.getOrgId());
		departmentVO.setCancel(departmentDTO.isCancel());
	}

	@Override
	public List<DepartmentVO> getAllDepartmentByOrgId(Long orgId) {
		return departmentRepo.findDepartmentByOrgId(orgId);
	}

	@Override
	public List<DepartmentVO> getAllDepartmentById(Long id) {
		return departmentRepo.findDepartmentById(id);
	}

	@Override
	public Map<String, Object> createUpdateUserProfileInformation(UserProfileInformationDTO userProfileInformationDTO)
			throws ApplicationException {

		UserProfileInformationVO userProfileInformationVO = new UserProfileInformationVO();
		String message;

		if (ObjectUtils.isNotEmpty(userProfileInformationDTO.getId())) {
			userProfileInformationVO = userProfileInformationRepo.findById(userProfileInformationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid ExpenseClaims details"));

			userProfileInformationVO.setUpdatedBy(userProfileInformationVO.getCreatedBy());

			message = "UserProfile Updated Successfully";
		} else {

			userProfileInformationVO.setCreatedBy(userProfileInformationDTO.getCreatedBy());
			userProfileInformationVO.setUpdatedBy(userProfileInformationDTO.getCreatedBy());
			message = "ExpenseClaims Created Successfully";
		}

		createUpdateUserProfileInformationByUserProfileInformationDTO(userProfileInformationVO,
				userProfileInformationDTO);
		userProfileInformationRepo.save(userProfileInformationVO);
		Map<String, Object> response = new HashMap<>();
		response.put("userProfileInformationVO", userProfileInformationVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateUserProfileInformationByUserProfileInformationDTO(
			UserProfileInformationVO userProfileInformationVO, UserProfileInformationDTO userProfileInformationDTO) {

		userProfileInformationVO.setFullName(userProfileInformationDTO.getFullName());
		userProfileInformationVO.setEmail(userProfileInformationDTO.getEmail());
		userProfileInformationVO.setPhoneNumber(userProfileInformationDTO.getPhoneNumber());
		userProfileInformationVO.setCompanyName(userProfileInformationDTO.getCompanyName());
		userProfileInformationVO.setAddress(userProfileInformationDTO.getAddress());
		userProfileInformationVO.setBioInformation(userProfileInformationDTO.getBioInformation());
		userProfileInformationVO.setCreatedBy(userProfileInformationDTO.getCreatedBy());
		userProfileInformationVO.setUserId(userProfileInformationDTO.getUserId());
	}

	@Override
	public UserProfileInformationVO getUserProfileInformationById(Long id) {
		return userProfileInformationRepo.getUserProfileInformationById(id);
	}

	@Override
	public UserProfileInformationVO uploadExpenseUserProfileInBloob(MultipartFile file, Long id)
			throws IOException, java.io.IOException {
		UserProfileInformationVO userProfileInformationVO = userProfileInformationRepo.findById(id).get();
		if (file != null && !file.isEmpty()) {
			userProfileInformationVO.setUserProfileImage(file.getBytes());

		}
		return userProfileInformationRepo.save(userProfileInformationVO);
	}

	@Override
	public UserProfileInformationVO getProfileInformationByUserId(Long id) {
		return userProfileInformationRepo.getProfileInformationByUserId(id);
	}

}
