package com.whydigit.fms.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.fms.ResponseDTO.CityResponseDTO;
import com.whydigit.fms.ResponseDTO.DocumentTypeMappingResponseDTO;
import com.whydigit.fms.ResponseDTO.GlobalParameterResponseDTO;
import com.whydigit.fms.ResponseDTO.StateResponseDTO;
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
import com.whydigit.fms.exception.ApplicationException;

@Service
public interface CommonMasterService {

	// Country

	List<CountryVO> getAllCountry(Long orgid); // Method names should be in camelCase

	Optional<CountryVO> getCountryById(Long countryid);

	Map<String, Object> createUpdateCountry(CountryDTO countryDTO) throws ApplicationException; // Return the created
																								// entity

	void deleteCountry(Long countryid);

	// State

	List<StateResponseDTO> getAllStateByOrgId(Long orgid);

	Optional<StateResponseDTO> getStateById(Long stateid);

	List<StateResponseDTO> getStatesByCountry(Long orgid, Long country);

	Map<String, Object> createUpdateState(StateDTO stateDTO) throws ApplicationException;

	void deleteState(Long stateid);

	// city

	List<CityResponseDTO> getAllCityByOrgId(Long orgid);
	
	List<CityResponseDTO> getCityByOrgIdAndstate(Long orgid, Long state);

	Optional<CityResponseDTO> getCityById(Long cityid);

	Map<String, Object> createUpdateCity(CityDTO cityDTO) throws ApplicationException;

	void deleteCity(Long cityid);

	// Currency

	List<CurrencyVO> getAllCurrency(Long orgid);

	Optional<CurrencyVO> getCurrencyById(Long currencyid);

	Map<String, Object> createUpdateCurrency(CurrencyDTO currencyDTO) throws ApplicationException;

	void deleteCurrency(Long currencyid);


	// Company

	List<CompanyVO> getAllCompany();

	List<CompanyVO> getCompanyById(Long companyid);

	CompanyVO createCompany(CompanyDTO companyDTO) throws Exception;

	CompanyVO updateCompany(CompanyDTO companyDTO) throws ApplicationException;

	void deleteCompany(Long companyid);

	// Global Parameter

	Set<Object[]> getWarehouseNameByOrgIdAndBranchAndClient(Long orgid, String branch, String client);

	Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(Long orgid, String userId);

	GlobalParameterResponseDTO updateGlobaParameter(GlobalParameterDTO globalParameterDTO);

	// to getAcces Global Param Dteails

	List<Map<String, Object>> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid, String userName);

	
	Map<String, Object> createUpdateScreenNames(ScreenNamesDTO screenNamesDTO) throws ApplicationException;

	List<ScreenNamesVO> getAllScreenNames();

	ScreenNamesVO getScreenNamesById(Long id) throws ApplicationException;

	// Designation

	Map<String, Object> createUpdateDesignation(DesignationDTO designationDTO) throws ApplicationException;

	List<DesignationVO> getAllDesignation();

	List<DesignationVO> getAllDesignationByOrgId(Long OrdId);

	Optional<DesignationVO> getAllDesignationById(Long id);

// FINANCIAL YEAR

	Map<String, Object> createUpdateFinYear(FinancialYearDTO financialYearDTO) throws ApplicationException;

	List<FinancialYearVO> getAllActiveFInYear(Long orgId);

	List<FinancialYearVO> getAllFInYearByOrgId(Long orgId);

	Optional<FinancialYearVO> getAllFInYearById(Long id);

	// DEPARTMENT

	Map<String, Object> createUpdateDepartment(DepartmentDTO departmentDTO) throws ApplicationException;

	List<DepartmentVO> getAllDepartmentByOrgId(Long orgId);

	List<DepartmentVO> getAllDepartmentById(Long id);

	UserProfileInformationVO uploadExpenseUserProfileInBloob(MultipartFile file, Long id)
			throws IOException, IOException;

	UserProfileInformationVO getUserProfileInformationById(Long id);

	Map<String, Object> createUpdateUserProfileInformation(UserProfileInformationDTO userProfileInformationDTO)
			throws ApplicationException;

	UserProfileInformationVO getProfileInformationByUserId(Long id);

	Map<String, Object> createUpdateDocumentTypeMaster(DocumentTypeMasterDTO documentTypeMasterDTO) throws ApplicationException;

	List<DocumentTypeMasterVO> getAllDocumentTypeMasterByOrgId(Long orgId);

	DocumentTypeMasterVO getDocumentTypeMasterById(Long id) throws ApplicationException;

	
	// documenttypemapping

	Map<String, Object> updateCreateDocumentTypeMapping(DocumentTypeMappingDTO documentTypeMappingDTO)
			throws ApplicationException;

	DocumentTypeMappingResponseDTO getDocumentTypeMappingById(Long id) throws ApplicationException;

	List<DocumentTypeMappingResponseDTO> getDocumentTypeMappingByOrgId(Long orgId, Long branch)  throws ApplicationException;

	List<Map<String, Object>> getPendingDocumentTypeMapping(Long orgId, String branch, String branchCode,
			String finYear, String finYearIdentifier);







}
