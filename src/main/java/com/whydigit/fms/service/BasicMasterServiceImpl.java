package com.whydigit.fms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whydigit.fms.ResponseDTO.BranchResponsesDTO;
import com.whydigit.fms.ResponseDTO.CityResponseDetailsDTO;
import com.whydigit.fms.ResponseDTO.ConfigModeResponsesDTO;
import com.whydigit.fms.ResponseDTO.ConfigProductResponsesDTO;
import com.whydigit.fms.ResponseDTO.ConfigResponseDTO;
import com.whydigit.fms.ResponseDTO.ConfigServiceResponsesDTO;
import com.whydigit.fms.ResponseDTO.CountryResponseDTO;
import com.whydigit.fms.ResponseDTO.CustomerAddressResponseDTO;
import com.whydigit.fms.ResponseDTO.CustomerResponseDTO;
import com.whydigit.fms.ResponseDTO.PortResponseDTO;
import com.whydigit.fms.ResponseDTO.ProductResponseDTO;
import com.whydigit.fms.ResponseDTO.ServiceResponseDTO;
import com.whydigit.fms.ResponseDTO.StateDetailResponseDTO;
import com.whydigit.fms.ResponseDTO.StateResponseDTO;
import com.whydigit.fms.dto.ConfigDTO;
import com.whydigit.fms.dto.ConfigModeDTO;
import com.whydigit.fms.dto.ConfigProductDTO;
import com.whydigit.fms.dto.ConfigServiceDTO;
import com.whydigit.fms.dto.CustomerAddressDTO;
import com.whydigit.fms.dto.CustomerDTO;
import com.whydigit.fms.dto.PortDTO;
import com.whydigit.fms.dto.ProductDTO;
import com.whydigit.fms.dto.ServiceDTO;
import com.whydigit.fms.entity.BranchVO;
import com.whydigit.fms.entity.CityVO;
import com.whydigit.fms.entity.ConfigModeVO;
import com.whydigit.fms.entity.ConfigProductVO;
import com.whydigit.fms.entity.ConfigServiceVO;
import com.whydigit.fms.entity.ConfigVO;
import com.whydigit.fms.entity.CountryVO;
import com.whydigit.fms.entity.CustomerAddressVO;
import com.whydigit.fms.entity.CustomerVO;
import com.whydigit.fms.entity.PortVO;
import com.whydigit.fms.entity.ProductVO;
import com.whydigit.fms.entity.ServiceVO;
import com.whydigit.fms.entity.StateVO;
import com.whydigit.fms.repo.BranchRepo;
import com.whydigit.fms.repo.CityRepo;
import com.whydigit.fms.repo.ConfigModeRepo;
import com.whydigit.fms.repo.ConfigProductRepo;
import com.whydigit.fms.repo.ConfigRepo;
import com.whydigit.fms.repo.ConfigServiceRepo;
import com.whydigit.fms.repo.CountryRepository;
import com.whydigit.fms.repo.CustomerRepository;
import com.whydigit.fms.repo.PortRepo;
import com.whydigit.fms.repo.ProductRepo;
import com.whydigit.fms.repo.ServiceRepo;
import com.whydigit.fms.repo.StateRepo;

@Service
public class BasicMasterServiceImpl implements BasicMasterService {

	@Autowired
	private ProductRepo productRepository;

	@Autowired
	private ServiceRepo serviceRepository;

	@Autowired
	private BranchRepo branchRepository;

	@Autowired
	private PortRepo portRepository;

	@Autowired
	private CountryRepository countryRepository;

	// CONFIGURATION REPOSITORIES

	@Autowired
	private ConfigRepo configRepository;

	@Autowired
	private ConfigModeRepo configModeRepository;

	@Autowired
	private ConfigProductRepo configProductRepository;

	@Autowired
	private ConfigServiceRepo configServiceRepository;

	@Autowired
	CityRepo cityRepo;
	// CUSTOMER REPOSITORIES
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private StateRepo stateRepository;

	@PersistenceContext
	private EntityManager entityManager;

	// PRODUCT - CREATE / UPDATE

	@Override
	public Map<String, Object> createUpdateProduct(ProductDTO productDTO) {

		ProductVO productVO;
		String message;

		// CREATE
		if (productDTO.getId() == null) {

			productVO = new ProductVO();

			productVO.setCreatedBy(productDTO.getCreatedBy());

			productVO.setUpdatedBy(productDTO.getCreatedBy());

			message = "Product Creation Successfully";

		} else {

			// UPDATE
			productVO = productRepository.findById(productDTO.getId())
					.orElseThrow(() -> new RuntimeException("Product not found with id: " + productDTO.getId()));

			productVO.setUpdatedBy(productDTO.getCreatedBy());

			message = "Product Updation Successfully";
		}

		mapProductDTOToVO(productVO, productDTO);

		ProductVO savedProduct = productRepository.save(productVO);

		ProductResponseDTO responseDTO = convertProductToResponseDTO(savedProduct);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("productVO", responseDTO);

		return response;
	}

	// PRODUCT DTO -> PRODUCT VO

	private void mapProductDTOToVO(ProductVO productVO, ProductDTO productDTO) {

		productVO.setProductCode(productDTO.getProductCode());

		productVO.setProductDescription(productDTO.getProductDescription());

		productVO.setOrgId(productDTO.getOrgId());

		BranchVO branchVO = branchRepository.findById(productDTO.getBranch())
				.orElseThrow(() -> new RuntimeException("Branch not found with id: " + productDTO.getBranch()));

		productVO.setBranch(branchVO);

		productVO.setCancelRemarks(productDTO.getCancelRemarks());
	}

	// PRODUCT VO -> RESPONSE DTO

	private ProductResponseDTO convertProductToResponseDTO(ProductVO productVO) {

		ProductResponseDTO response = new ProductResponseDTO();

		response.setId(productVO.getId());

		response.setProductCode(productVO.getProductCode());

		response.setProductDescription(productVO.getProductDescription());

		response.setOrgId(productVO.getOrgId());

		if (productVO.getBranch() != null) {

			BranchResponsesDTO branchResponse = new BranchResponsesDTO();

			branchResponse.setId(productVO.getBranch().getId());

			branchResponse.setBranch(productVO.getBranch().getBranch());

			branchResponse.setBranchCode(productVO.getBranch().getBranchCode());

			response.setBranch(branchResponse);
		}

		response.setActive(productVO.getActive());

		response.setCancel(productVO.getCancel());

		response.setCancelRemarks(productVO.getCancelRemarks());

		response.setCreatedBy(productVO.getCreatedBy());

		response.setUpdatedBy(productVO.getUpdatedBy());

		return response;
	}

	// PRODUCT - GET BY ID
	@Override
	public ProductResponseDTO getProductById(Long id) {

		ProductVO productVO = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

		return convertProductToResponseDTO(productVO);
	}

	// PRODUCT - GET BY ORG ID AND BRANCH
	@Override
	public List<ProductResponseDTO> getProductsByOrgIdAndBranch(Long orgId, Long branch) {

		List<ProductVO> products = productRepository.findByOrgIdAndBranch(orgId, branch);

		if (products == null || products.isEmpty()) {
			throw new RuntimeException("Product not found for OrgId: " + orgId + " and Branch: " + branch);
		}

		return products.stream().map(this::convertProductToResponseDTO).collect(Collectors.toList());
	}

	// SERVICE - CREATE / UPDATE
	@Override
	public Map<String, Object> createUpdateService(ServiceDTO serviceDTO) {

		ServiceVO serviceVO;
		String message;

		// CREATE
		if (serviceDTO.getId() == null) {

			serviceVO = new ServiceVO();

			serviceVO.setCreatedBy(serviceDTO.getCreatedBy());

			serviceVO.setUpdatedBy(serviceDTO.getCreatedBy());

			message = "Service Creation Successfully";

		} else {

			// UPDATE
			serviceVO = serviceRepository.findById(serviceDTO.getId())
					.orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceDTO.getId()));

			serviceVO.setUpdatedBy(serviceDTO.getCreatedBy());

			message = "Service Updation Successfully";
		}

		mapServiceDTOToVO(serviceVO, serviceDTO);

		ServiceVO savedService = serviceRepository.save(serviceVO);

		ServiceResponseDTO responseDTO = convertServiceToResponseDTO(savedService);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("serviceVO", responseDTO);

		return response;
	}

	// SERVICE DTO -> SERVICE VO
	private void mapServiceDTOToVO(ServiceVO serviceVO, ServiceDTO serviceDTO) {

		serviceVO.setServiceCode(serviceDTO.getServiceCode());

		serviceVO.setServiceDescription(serviceDTO.getServiceDescription());

		serviceVO.setOrgId(serviceDTO.getOrgId());

		BranchVO branchVO = branchRepository.findById(serviceDTO.getBranch())
				.orElseThrow(() -> new RuntimeException("Branch not found with id: " + serviceDTO.getBranch()));

		serviceVO.setBranch(branchVO);

		serviceVO.setCancelRemarks(serviceDTO.getCancelRemarks());
	}

	// SERVICE VO -> RESPONSE DTO
	private ServiceResponseDTO convertServiceToResponseDTO(ServiceVO serviceVO) {

		ServiceResponseDTO response = new ServiceResponseDTO();

		response.setId(serviceVO.getId());

		response.setServiceCode(serviceVO.getServiceCode());

		response.setServiceDescription(serviceVO.getServiceDescription());

		response.setOrgId(serviceVO.getOrgId());

		if (serviceVO.getBranch() != null) {

			BranchResponsesDTO branchResponse = new BranchResponsesDTO();

			branchResponse.setId(serviceVO.getBranch().getId());

			branchResponse.setBranch(serviceVO.getBranch().getBranch());

			branchResponse.setBranchCode(serviceVO.getBranch().getBranchCode());

			response.setBranch(branchResponse);
		}

		response.setActive(serviceVO.getActive());

		response.setCancel(serviceVO.getCancel());

		response.setCancelRemarks(serviceVO.getCancelRemarks());

		response.setCreatedBy(serviceVO.getCreatedBy());

		response.setUpdatedBy(serviceVO.getUpdatedBy());

		return response;
	}

	// SERVICE - GET BY ID
	@Override
	public ServiceResponseDTO getServiceById(Long id) {

		ServiceVO serviceVO = serviceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Service not found with id: " + id));

		return convertServiceToResponseDTO(serviceVO);
	}

	// SERVICE - GET BY ORG ID AND BRANCH
	@Override
	public List<ServiceResponseDTO> getServicesByOrgIdAndBranch(Long orgId, Long branch) {

		List<ServiceVO> services = serviceRepository.findByOrgIdAndBranch(orgId, branch);

		if (services == null || services.isEmpty()) {
			throw new RuntimeException("Service not found for OrgId: " + orgId + " and Branch: " + branch);
		}

		return services.stream().map(this::convertServiceToResponseDTO).collect(Collectors.toList());
	}

	// PORT - CREATE / UPDATE

	@Override
	public Map<String, Object> createUpdatePort(PortDTO portDTO) {

		// PORT CODE VALIDATION
		if (portDTO.getPortCode() == null || portDTO.getPortCode().trim().isEmpty()) {

			throw new RuntimeException("Port Code is required");
		}

		// Normalize code
		portDTO.setPortCode(portDTO.getPortCode().trim().toUpperCase());

		PortVO portVO;
		String message;

		// CREATE
		if (portDTO.getId() == null) {

			Optional<PortVO> existingPort = portRepository.findByPortCode(portDTO.getPortCode());

			if (existingPort.isPresent()) {

				throw new RuntimeException("Port Code already exists: " + portDTO.getPortCode());
			}

			portVO = new PortVO();

			portVO.setCreatedBy(portDTO.getCreatedBy());

			portVO.setUpdatedBy(portDTO.getCreatedBy());

			message = "Port Creation Successfully";

		} else {

			// UPDATE
			portVO = portRepository.findById(portDTO.getId())
					.orElseThrow(() -> new RuntimeException("Port not found with id: " + portDTO.getId()));

			Optional<PortVO> existingPort = portRepository.findByPortCode(portDTO.getPortCode());

			if (existingPort.isPresent() && !existingPort.get().getId().equals(portDTO.getId())) {

				throw new RuntimeException("Port Code " + portDTO.getPortCode()
						+ " already exists for another Port ID: " + existingPort.get().getId());
			}

			portVO.setUpdatedBy(portDTO.getCreatedBy());

			message = "Port Updation Successfully";
		}

		mapPortDTOToVO(portVO, portDTO);

		PortVO savedPort = portRepository.save(portVO);

		PortResponseDTO responseDTO = convertPortToResponseDTO(savedPort);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("portVO", responseDTO);

		return response;
	}
	// PORT DTO -> VO

	private void mapPortDTOToVO(PortVO portVO, PortDTO portDTO) {

		portVO.setPortType(portDTO.getPortType());

		portVO.setPortCode(portDTO.getPortCode());

		portVO.setPortName(portDTO.getPortName());

		portVO.setOrgId(portDTO.getOrgId());

		BranchVO branchVO = branchRepository.findById(portDTO.getBranch())
				.orElseThrow(() -> new RuntimeException("Branch not found with id: " + portDTO.getBranch()));

		portVO.setBranch(branchVO);

		CountryVO countryVO = countryRepository.findById(portDTO.getCountry())
				.orElseThrow(() -> new RuntimeException("Country not found with id: " + portDTO.getCountry()));

		portVO.setCountry(countryVO);

		CityVO cityVO = cityRepo.findById(portDTO.getCity())
				.orElseThrow(() -> new RuntimeException("City not found with id: " + portDTO.getCountry()));

		portVO.setCity(cityVO);

		portVO.setActive(portDTO.isActive());

		portVO.setCancelRemarks(portDTO.getCancelRemarks());
	}

	// PORT VO -> RESPONSE DTO

	private PortResponseDTO convertPortToResponseDTO(PortVO portVO) {

		PortResponseDTO response = new PortResponseDTO();

		response.setId(portVO.getId());

		response.setPortType(portVO.getPortType());

		response.setPortCode(portVO.getPortCode());

		response.setPortName(portVO.getPortName());

		response.setOrgId(portVO.getOrgId());

		// BRANCH RESPONSE
		if (portVO.getBranch() != null) {

			BranchResponsesDTO branchResponse = new BranchResponsesDTO();

			branchResponse.setId(portVO.getBranch().getId());

			branchResponse.setBranch(portVO.getBranch().getBranch());

			branchResponse.setBranchCode(portVO.getBranch().getBranchCode());

			response.setBranch(branchResponse);
		}

		// COUNTRY RESPONSE
		if (portVO.getCountry() != null) {

			CountryResponseDTO countryResponse = new CountryResponseDTO();

			countryResponse.setId(portVO.getCountry().getId());

			countryResponse.setCountry(portVO.getCountry().getCountryName());

			countryResponse.setCountryCode(portVO.getCountry().getCountryCode());

			response.setCountry(countryResponse);
		}

		if (portVO.getCity() != null) {
			CityResponseDetailsDTO cityResponse = new CityResponseDetailsDTO();

			cityResponse.setId(portVO.getCity().getId());
			cityResponse.setCity(portVO.getCity().getCityName());
			cityResponse.setCityCode(portVO.getCity().getCityCode());

			response.setCity(cityResponse);
		}

		response.setActive(portVO.getActive());

		response.setCancel(portVO.getCancel());

		response.setCancelRemarks(portVO.getCancelRemarks());

		response.setCreatedBy(portVO.getCreatedBy());

		response.setUpdatedBy(portVO.getUpdatedBy());

		return response;
	}

	// PORT - GET BY ID

	@Override
	public PortResponseDTO getPortById(Long id) {

		PortVO portVO = portRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Port not found with id: " + id));

		return convertPortToResponseDTO(portVO);
	}

	// PORT - GET BY ORG ID AND BRANCH

	@Override
	public List<PortResponseDTO> getPortsByOrgIdAndBranch(Long orgId, Long branch) {

		List<PortVO> ports = portRepository.findByOrgIdAndBranch(orgId, branch);

		if (ports == null || ports.isEmpty()) {

			throw new RuntimeException("Port not found for OrgId: " + orgId + " and Branch: " + branch);
		}

		return ports.stream().map(this::convertPortToResponseDTO).collect(Collectors.toList());
	}

	// =========================================================
	// CREATE OR UPDATE CONFIGURATION
	// =========================================================

	// CREATE OR UPDATE CONFIGURATION
	@Override
	@Transactional
	public Map<String, Object> createUpdateConfig(ConfigDTO dto) {

		Map<String, Object> response = new HashMap<>();

		String message;
		ConfigVO configVO;
		String screenCode = "CONFIG";

		// COMPANY NAME VALIDATION BASED ON ORG ID

		if (dto.getOrgId() == null) {
			throw new RuntimeException("Organization ID is required");
		}

		if (dto.getCompany() == null || dto.getCompany().trim().isEmpty()) {
			throw new RuntimeException("Company name is required");
		}

		String company = dto.getCompany().trim();

		if (dto.getId() == null) {

			// CREATE: Check duplicate company within the same organization
			if (configRepository.existsByOrgIdAndCompanyIgnoreCase(dto.getOrgId(), company)) {

				throw new RuntimeException(
						"Company '" + company + "' already exists for organization ID: " + dto.getOrgId());
			}

		} else {

			// UPDATE: Exclude the current configuration from the duplicate check
			if (configRepository.existsByOrgIdAndCompanyIgnoreCaseAndIdNot(dto.getOrgId(), company, dto.getId())) {

				throw new RuntimeException(
						"Company '" + company + "' already exists for organization ID: " + dto.getOrgId());
			}
		}

		// CREATE
		if (dto.getId() == null) {

			configVO = new ConfigVO();

			configVO.setCreatedBy(dto.getCreatedBy());
			configVO.setUpdatedBy(dto.getCreatedBy());

			message = "Configuration Created Successfully";

		} else {

			// UPDATE

			configVO = configRepository.findById(dto.getId())
					.orElseThrow(() -> new RuntimeException("Configuration Not Found with id: " + dto.getId()));

			List<ConfigModeVO> oldModes = configModeRepository.findByConfig(configVO);

			configModeRepository.deleteAll(oldModes);

			List<ConfigProductVO> oldProducts = configProductRepository.findByConfig(configVO);

			configProductRepository.deleteAll(oldProducts);

			List<ConfigServiceVO> oldServices = configServiceRepository.findByConfig(configVO);

			configServiceRepository.deleteAll(oldServices);

			configVO.setUpdatedBy(dto.getCreatedBy());

			message = "Configuration Updated Successfully";
		}

		// HEADER + CHILD MAPPING
		getConfigVOFromDTO(dto, configVO);

		configVO = configRepository.saveAndFlush(configVO);

		ConfigResponseDTO responseDTO = convertConfigToResponseDTO(configVO);

		response.put("message", message);
		response.put("configResponseVO", responseDTO);

		return response;
	}

	// =========================================================
	// CONFIGURATION - HEADER + CHILD MAPPING
	// =========================================================

	private void getConfigVOFromDTO(ConfigDTO dto, ConfigVO configVO) {

		// =====================================================
		// HEADER MAPPING
		// =====================================================

		configVO.setCompany(dto.getCompany());
		configVO.setOrgId(dto.getOrgId());
		configVO.setCancelRemarks(dto.getCancelRemarks());

		// =====================================================
		// BRANCH MAPPING
		// =====================================================

		if (dto.getBranch() != null) {

			BranchVO branchVO = branchRepository.findById(dto.getBranch())
					.orElseThrow(() -> new RuntimeException("Branch Not Found with id: " + dto.getBranch()));

			configVO.setBranch(branchVO);
		}

		// =====================================================
		// CONFIGURATION MODES
		// =====================================================

		if (configVO.getConfigModes() == null) {
			configVO.setConfigModes(new ArrayList<>());
		} else {
			configVO.getConfigModes().clear();
		}

		if (dto.getConfigModes() != null) {

			for (ConfigModeDTO childDTO : dto.getConfigModes()) {

				ConfigModeVO modeVO = new ConfigModeVO();

				modeVO.setMode(childDTO.getMode());

				if (childDTO.getActive() != null) {
					modeVO.setActive(childDTO.getActive());
				}

				// Link child to parent.
				modeVO.setConfig(configVO);

				// Add child to parent collection.
				configVO.getConfigModes().add(modeVO);
			}
		}

		// =====================================================
		// CONFIGURATION PRODUCTS
		// =====================================================

		if (configVO.getConfigProducts() == null) {
			configVO.setConfigProducts(new ArrayList<>());
		} else {
			configVO.getConfigProducts().clear();
		}

		if (dto.getConfigProducts() != null) {

			for (ConfigProductDTO childDTO : dto.getConfigProducts()) {

				ConfigProductVO productVO = new ConfigProductVO();

				if (childDTO.getProduct() == null) {
					throw new RuntimeException("Product ID is required for configuration product");
				}

				ProductVO product = productRepository.findById(childDTO.getProduct())
						.orElseThrow(() -> new RuntimeException("Product Not Found: " + childDTO.getProduct()));

				productVO.setProduct(product);
				productVO.setMode(childDTO.getMode());

				if (childDTO.getActive() != null) {
					productVO.setActive(childDTO.getActive());
				}

				// Link child to parent.
				productVO.setConfig(configVO);

				// Add child to parent collection.
				configVO.getConfigProducts().add(productVO);
			}
		}

		// =====================================================
		// CONFIGURATION SERVICES
		// =====================================================

		if (configVO.getConfigServices() == null) {
			configVO.setConfigServices(new ArrayList<>());
		} else {
			configVO.getConfigServices().clear();
		}

		if (dto.getConfigServices() != null) {

			for (ConfigServiceDTO childDTO : dto.getConfigServices()) {

				ConfigServiceVO serviceVO = new ConfigServiceVO();

				if (childDTO.getService() == null) {
					throw new RuntimeException("Service ID is required for configuration service");
				}

				ServiceVO service = serviceRepository.findById(childDTO.getService())
						.orElseThrow(() -> new RuntimeException("Service Not Found: " + childDTO.getService()));

				serviceVO.setService(service);
				serviceVO.setMode(childDTO.getMode());

				if (childDTO.getActive() != null) {
					serviceVO.setActive(childDTO.getActive());
				}

				// Link child to parent.
				serviceVO.setConfig(configVO);

				// Add child to parent collection.
				configVO.getConfigServices().add(serviceVO);
			}
		}
	}

	// =========================================================
	// CONFIGURATION - CONVERT ENTITY TO RESPONSE DTO
	// =========================================================

	private ConfigResponseDTO convertConfigToResponseDTO(ConfigVO configVO) {

		ConfigResponseDTO responseDTO = new ConfigResponseDTO();

		// =====================================================
		// HEADER RESPONSE MAPPING
		// =====================================================

		responseDTO.setId(configVO.getId());
		responseDTO.setCompany(configVO.getCompany());
		responseDTO.setOrgId(configVO.getOrgId());

		/*
		 * These assignments assume ConfigVO getters return String, consistent with the
		 * custom JSON getter behavior you described.
		 */
		responseDTO.setActive(configVO.getActive());
		responseDTO.setCancel(configVO.getCancel());

		responseDTO.setCancelRemarks(configVO.getCancelRemarks());
		responseDTO.setCreatedBy(configVO.getCreatedBy());
		responseDTO.setUpdatedBy(configVO.getUpdatedBy());

		// =====================================================
		// BRANCH RESPONSE MAPPING
		// =====================================================

		if (configVO.getBranch() != null) {

			BranchResponsesDTO branchResponse = new BranchResponsesDTO();

			branchResponse.setId(configVO.getBranch().getId());
			branchResponse.setBranch(configVO.getBranch().getBranch());
			branchResponse.setBranchCode(configVO.getBranch().getBranchCode());

			responseDTO.setBranch(branchResponse);
		}

		// =====================================================
		// CONFIGURATION MODES RESPONSE MAPPING
		// =====================================================

		List<ConfigModeResponsesDTO> modeResponses = new ArrayList<>();

		if (configVO.getConfigModes() != null) {

			for (ConfigModeVO modeVO : configVO.getConfigModes()) {

				ConfigModeResponsesDTO modeResponse = new ConfigModeResponsesDTO();

				modeResponse.setId(modeVO.getId());
				modeResponse.setMode(modeVO.getMode());

				modeResponse.setActive(modeVO.getActive() != null && modeVO.getActive().equalsIgnoreCase("Active"));

				modeResponses.add(modeResponse);
			}
		}

		responseDTO.setConfigModes(modeResponses);
		// =====================================================
		// CONFIGURATION SERVICES RESPONSE MAPPING
		// =====================================================

		List<ConfigServiceResponsesDTO> serviceResponses = new ArrayList<>();

		if (configVO.getConfigServices() != null) {

			for (ConfigServiceVO serviceVO : configVO.getConfigServices()) {

				ConfigServiceResponsesDTO serviceResponse = new ConfigServiceResponsesDTO();

				serviceResponse.setId(serviceVO.getId());

				serviceResponse.setMode(serviceVO.getMode());

				serviceResponse
						.setActive(serviceVO.getActive() != null && serviceVO.getActive().equalsIgnoreCase("Active"));
				serviceResponses.add(serviceResponse);
			}
		}

		responseDTO.setConfigServices(serviceResponses);
		// =====================================================
		// CONFIGURATION PRODUCTS RESPONSE MAPPING
		// =====================================================

		List<ConfigProductResponsesDTO> productResponses = new ArrayList<>();

		if (configVO.getConfigProducts() != null) {

			for (ConfigProductVO productVO : configVO.getConfigProducts()) {

				ConfigProductResponsesDTO productResponse = new ConfigProductResponsesDTO();

				productResponse.setId(productVO.getId());

				productResponse.setMode(productVO.getMode());
				productResponse
						.setActive(productVO.getActive() != null && productVO.getActive().equalsIgnoreCase("Active"));
				productResponses.add(productResponse);
			}
		}

		responseDTO.setConfigProducts(productResponses);

		return responseDTO;
	}

	// =========================================================
	// CONFIGURATION - GET BY ID
	// =========================================================

	@Override
	@Transactional
	public ConfigResponseDTO getConfigById(Long id) {

		ConfigVO configVO = configRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Configuration not found with id: " + id));

		return convertConfigToResponseDTO(configVO);
	}

	// =========================================================
	// CONFIGURATION - GET BY ORG ID AND BRANCH
	// =========================================================

	@Override
	@Transactional
	public List<ConfigResponseDTO> getConfigByOrgIdAndBranch(Long orgId, Long branch) {

		List<ConfigVO> configs = configRepository.findByOrgIdAndBranch(orgId, branch);

		if (configs == null || configs.isEmpty()) {
			throw new RuntimeException("Configuration not found for OrgId: " + orgId + " and Branch: " + branch);
		}

		return configs.stream().map(this::convertConfigToResponseDTO).collect(Collectors.toList());
	}

	// CREATE UPDATE CUSTOMER
	@Override
	@Transactional
	public Map<String, Object> createUpdateCustomer(CustomerDTO customerDTO) {

		CustomerVO customerVO;
		String message;

		// ==========================================
		// 1. CREATE OR UPDATE CUSTOMER
		// ==========================================

		if (customerDTO.getId() == null) {

		    // CREATE CUSTOMER
		    customerVO = new CustomerVO();

		    String generatedCustomerCode = generateCustomerCode();

		    if (customerRepository.existsByCustomerCode(generatedCustomerCode)) {
		        throw new RuntimeException(
		                "Customer code already exists: " + generatedCustomerCode);
		    }

		    customerVO.setCustomerCode(generatedCustomerCode);
		    customerVO.setCreatedBy(customerDTO.getCreatedBy());
		    customerVO.setUpdatedBy(customerDTO.getCreatedBy());

		    message = "Customer Creation Successfully";

		} else {

		    // UPDATE EXISTING CUSTOMER
		    customerVO = customerRepository.findById(customerDTO.getId())
		            .orElseThrow(() -> new RuntimeException(
		                    "Customer not found with id: " + customerDTO.getId()));

		    // Keep the existing customer code unchanged.
		    String existingCustomerCode = customerVO.getCustomerCode();
		    String requestedCustomerCode = customerDTO.getCustomerCode();

		    if (requestedCustomerCode != null
		            && !requestedCustomerCode.trim().isEmpty()
		            && !requestedCustomerCode.equals(existingCustomerCode)) {

		        if (customerRepository.existsByCustomerCodeAndIdNot(
		                requestedCustomerCode, customerDTO.getId())) {

		            throw new RuntimeException(
		                    "Customer code already exists: " + requestedCustomerCode);
		        }

		        throw new RuntimeException(
		                "Customer code cannot be changed during update. Existing code: "
		                        + existingCustomerCode);
		    }

		    customerVO.setUpdatedBy(customerDTO.getCreatedBy());

		    message = "Customer Updation Successfully";
		}

		// ==========================================
		// 2. MAP CUSTOMER DTO TO CUSTOMER VO
		// ==========================================

		customerVO.setOrgId(customerDTO.getOrgId());
		customerVO.setCustomerName(customerDTO.getCustomerName());
		customerVO.setCustomerType(customerDTO.getCustomerType());
		customerVO.setGstin(customerDTO.getGstin());
		customerVO.setPan(customerDTO.getPan());
		customerVO.setIndustryType(customerDTO.getIndustryType());
		customerVO.setCancelRemarks(customerDTO.getCancelRemarks());

		// DTO fields are boolean
		customerVO.setActive(customerDTO.isActive());
		customerVO.setCancel(customerDTO.isCancel());

		// ==========================================
		// 3. MAP BRANCH
		// ==========================================

		if (customerDTO.getBranch() != null) {

			BranchVO branchVO = branchRepository.findById(customerDTO.getBranch())
					.orElseThrow(() -> new RuntimeException("Branch not found with id: " + customerDTO.getBranch()));

			customerVO.setBranch(branchVO);

		} else {

			customerVO.setBranch(null);
		}

		// ==========================================
		// 4. INITIALIZE CUSTOMER ADDRESSES
		// ==========================================

		if (customerVO.getCustomerAddresses() == null) {
			customerVO.setCustomerAddresses(new ArrayList<>());
		}

		// ==========================================
		// 5. MAP CUSTOMER ADDRESSES
		// ==========================================

		if (customerDTO.getCustomerAddresses() != null) {

			for (CustomerAddressDTO addressDTO : customerDTO.getCustomerAddresses()) {

				CustomerAddressVO addressVO;

				// --------------------------------------
				// UPDATE EXISTING ADDRESS
				// --------------------------------------

				if (addressDTO.getId() != null) {

					addressVO = customerVO.getCustomerAddresses().stream()
							.filter(address -> address.getId() != null && address.getId().equals(addressDTO.getId()))
							.findFirst().orElseThrow(() -> new RuntimeException(
									"Customer address not found with id: " + addressDTO.getId()));

				} else {

					// ----------------------------------
					// CREATE NEW ADDRESS
					// ----------------------------------

					addressVO = new CustomerAddressVO();

					addressVO.setCustomer(customerVO);

					customerVO.getCustomerAddresses().add(addressVO);
				}

				// --------------------------------------
				// MAP ADDRESS FIELDS
				// --------------------------------------

				addressVO.setAddress(addressDTO.getAddress());
				addressVO.setPostalCode(addressDTO.getPostalCode());
				addressVO.setContactName(addressDTO.getContactName());
				addressVO.setMobile(addressDTO.getMobile());
				addressVO.setAlternateMobile(addressDTO.getAlternateMobile());
				addressVO.setEmail(addressDTO.getEmail());
				addressVO.setLandmark(addressDTO.getLandmark());

				// --------------------------------------
				// MAP COUNTRY
				// --------------------------------------

				if (addressDTO.getCountry() != null) {

					CountryVO countryVO = countryRepository.findById(addressDTO.getCountry()).orElseThrow(
							() -> new RuntimeException("Country not found with id: " + addressDTO.getCountry()));

					addressVO.setCountry(countryVO);

				} else {

					addressVO.setCountry(null);
				}

				// --------------------------------------
				// MAP STATE
				// --------------------------------------

				if (addressDTO.getState() != null) {

					StateVO stateVO = stateRepository.findById(addressDTO.getState()).orElseThrow(
							() -> new RuntimeException("State not found with id: " + addressDTO.getState()));

					addressVO.setState(stateVO);

				} else {

					addressVO.setState(null);
				}

				// --------------------------------------
				// MAP CITY
				// --------------------------------------

				if (addressDTO.getCity() != null) {

					CityVO cityVO = cityRepo.findById(addressDTO.getCity())
							.orElseThrow(() -> new RuntimeException("City not found with id: " + addressDTO.getCity()));

					addressVO.setCity(cityVO);

				} else {

					addressVO.setCity(null);
				}

				// --------------------------------------
				// MAINTAIN PARENT-CHILD RELATIONSHIP
				// --------------------------------------

				addressVO.setCustomer(customerVO);
			}
		}

		// ==========================================
		// 6. SAVE CUSTOMER AND ADDRESSES
		// ==========================================

		CustomerVO savedCustomer = customerRepository.saveAndFlush(customerVO);

		// ==========================================
		// 7. MAP CUSTOMER TO RESPONSE DTO
		// ==========================================

		CustomerResponseDTO responseDTO = new CustomerResponseDTO();

		responseDTO.setId(savedCustomer.getId());
		responseDTO.setOrgId(savedCustomer.getOrgId());
		responseDTO.setCustomerCode(savedCustomer.getCustomerCode());
		responseDTO.setCustomerName(savedCustomer.getCustomerName());
		responseDTO.setCustomerType(savedCustomer.getCustomerType());
		responseDTO.setGstin(savedCustomer.getGstin());
		responseDTO.setPan(savedCustomer.getPan());
		responseDTO.setIndustryType(savedCustomer.getIndustryType());

		// Entity custom getters return String values
		responseDTO.setActive(savedCustomer.getActive());
		responseDTO.setCancel(savedCustomer.getCancel());

		responseDTO.setCancelRemarks(savedCustomer.getCancelRemarks());
		responseDTO.setCreatedBy(savedCustomer.getCreatedBy());
		responseDTO.setUpdatedBy(savedCustomer.getUpdatedBy());
		responseDTO.setCommonDate(savedCustomer.getCommonDate());

		// ==========================================
		// 8. MAP BRANCH RESPONSE
		// ==========================================

		if (savedCustomer.getBranch() != null) {

			BranchResponsesDTO branchResponse = new BranchResponsesDTO();

			branchResponse.setId(savedCustomer.getBranch().getId());
			branchResponse.setBranch(savedCustomer.getBranch().getBranch());
			branchResponse.setBranchCode(savedCustomer.getBranch().getBranchCode());

			responseDTO.setBranch(branchResponse);
		}

		// ==========================================
		// 9. MAP CUSTOMER ADDRESS RESPONSES
		// ==========================================

		List<CustomerAddressResponseDTO> addressResponses = new ArrayList<>();

		if (savedCustomer.getCustomerAddresses() != null) {

			for (CustomerAddressVO addressVO : savedCustomer.getCustomerAddresses()) {

				CustomerAddressResponseDTO addressResponse = new CustomerAddressResponseDTO();

				addressResponse.setId(addressVO.getId());
				addressResponse.setAddress(addressVO.getAddress());
				addressResponse.setPostalCode(addressVO.getPostalCode());
				addressResponse.setContactName(addressVO.getContactName());
				addressResponse.setMobile(addressVO.getMobile());
				addressResponse.setAlternateMobile(addressVO.getAlternateMobile());
				addressResponse.setEmail(addressVO.getEmail());
				addressResponse.setLandmark(addressVO.getLandmark());

				// --------------------------------------
				// COUNTRY RESPONSE
				// --------------------------------------

				if (addressVO.getCountry() != null) {

					CountryResponseDTO countryResponse = new CountryResponseDTO();

					countryResponse.setId(addressVO.getCountry().getId());
					countryResponse.setCountry(addressVO.getCountry().getCountryName());
					countryResponse.setCountryCode(addressVO.getCountry().getCountryCode());

					addressResponse.setCountry(countryResponse);
				}

				// --------------------------------------
				// STATE RESPONSE
				// --------------------------------------

				if (addressVO.getState() != null) {

					StateDetailResponseDTO stateResponse = new StateDetailResponseDTO();

					stateResponse.setId(addressVO.getState().getId());
					stateResponse.setStateName(addressVO.getState().getStateName());
					stateResponse.setStateCode(addressVO.getState().getStateCode());

					addressResponse.setState(stateResponse);
				}

				// --------------------------------------
				// CITY RESPONSE
				// --------------------------------------

				if (addressVO.getCity() != null) {

					CityResponseDetailsDTO cityResponse = new CityResponseDetailsDTO();

					cityResponse.setId(addressVO.getCity().getId());
					cityResponse.setCity(addressVO.getCity().getCityName());
					cityResponse.setCityCode(addressVO.getCity().getCityCode());

					addressResponse.setCity(cityResponse);
				}

				addressResponses.add(addressResponse);
			}
		}

		responseDTO.setCustomerAddresses(addressResponses);

		// ==========================================
		// 10. FINAL RESPONSE
		// ==========================================

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("customerVO", responseDTO);

		return response;
	}

	private CustomerResponseDTO convertCustomerToResponseDTO(CustomerVO customerVO) {

		CustomerResponseDTO responseDTO = new CustomerResponseDTO();

		// ==========================================
		// CUSTOMER DETAILS
		// ==========================================

		responseDTO.setId(customerVO.getId());
		responseDTO.setOrgId(customerVO.getOrgId());
		responseDTO.setCustomerName(customerVO.getCustomerName());
		responseDTO.setCustomerCode(customerVO.getCustomerCode());
		responseDTO.setCustomerType(customerVO.getCustomerType());
		responseDTO.setGstin(customerVO.getGstin());
		responseDTO.setPan(customerVO.getPan());
		responseDTO.setIndustryType(customerVO.getIndustryType());

		responseDTO.setActive(customerVO.getActive());
		responseDTO.setCancel(customerVO.getCancel());

		responseDTO.setCancelRemarks(customerVO.getCancelRemarks());
		responseDTO.setCreatedBy(customerVO.getCreatedBy());
		responseDTO.setUpdatedBy(customerVO.getUpdatedBy());
		responseDTO.setCommonDate(customerVO.getCommonDate());

		// ==========================================
		// BRANCH DETAILS
		// ==========================================

		if (customerVO.getBranch() != null) {

			BranchResponsesDTO branchResponse = new BranchResponsesDTO();

			branchResponse.setId(customerVO.getBranch().getId());
			branchResponse.setBranch(customerVO.getBranch().getBranch());
			branchResponse.setBranchCode(customerVO.getBranch().getBranchCode());

			responseDTO.setBranch(branchResponse);
		}

		// ==========================================
		// CUSTOMER ADDRESS DETAILS
		// ==========================================

		List<CustomerAddressResponseDTO> addressResponses = new ArrayList<>();

		if (customerVO.getCustomerAddresses() != null) {

			for (CustomerAddressVO addressVO : customerVO.getCustomerAddresses()) {

				CustomerAddressResponseDTO addressResponse = new CustomerAddressResponseDTO();

				addressResponse.setId(addressVO.getId());
				addressResponse.setAddress(addressVO.getAddress());
				addressResponse.setPostalCode(addressVO.getPostalCode());
				addressResponse.setContactName(addressVO.getContactName());
				addressResponse.setMobile(addressVO.getMobile());
				addressResponse.setAlternateMobile(addressVO.getAlternateMobile());
				addressResponse.setEmail(addressVO.getEmail());
				addressResponse.setLandmark(addressVO.getLandmark());

				// COUNTRY
				if (addressVO.getCountry() != null) {

					CountryResponseDTO countryResponse = new CountryResponseDTO();

					countryResponse.setId(addressVO.getCountry().getId());
					countryResponse.setCountry(addressVO.getCountry().getCountryName());
					countryResponse.setCountryCode(addressVO.getCountry().getCountryCode());

					addressResponse.setCountry(countryResponse);
				}

				// STATE
				if (addressVO.getState() != null) {

					StateDetailResponseDTO stateResponse = new StateDetailResponseDTO();

					stateResponse.setId(addressVO.getState().getId());
					stateResponse.setStateName(addressVO.getState().getStateName());
					stateResponse.setStateCode(addressVO.getState().getStateCode());

					addressResponse.setState(stateResponse);
				}

				// CITY
				if (addressVO.getCity() != null) {

					CityResponseDetailsDTO cityResponse = new CityResponseDetailsDTO();

					cityResponse.setId(addressVO.getCity().getId());
					cityResponse.setCity(addressVO.getCity().getCityName());
					cityResponse.setCityCode(addressVO.getCity().getCityCode());

					addressResponse.setCity(cityResponse);
				}

				addressResponses.add(addressResponse);
			}
		}

		responseDTO.setCustomerAddresses(addressResponses);

		return responseDTO;
	}

	private String generateCustomerCode() {

		String prefix = "CUS-";

		List<CustomerVO> customers = customerRepository.findAll();

		int maxNumber = customers.stream().map(CustomerVO::getCustomerCode)
				.filter(code -> code != null && code.matches("CUS-\\d+"))
				.map(code -> Integer.parseInt(code.substring(4))).max(Integer::compareTo).orElse(0);

		String nextCode;

		do {
			maxNumber++;
			nextCode = prefix + String.format("%03d", maxNumber);
		} while (customerRepository.existsByCustomerCode(nextCode));

		return nextCode;
	}

	@Override
	@Transactional(readOnly = true)
	public CustomerResponseDTO getCustomerById(Long id) {

		CustomerVO customerVO = customerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

		return convertCustomerToResponseDTO(customerVO);
	}
	public String getNextCustomerCode() {
	    return generateCustomerCode();
	}
	@Override
	@Transactional(readOnly = true)
	public List<CustomerResponseDTO> getCustomersByBranchAndOrgId(Long orgId, Long branchId) {

		List<CustomerVO> customers = customerRepository.findByOrgIdAndBranch_Id(orgId, branchId);

		List<CustomerResponseDTO> responseList = new ArrayList<>();

		for (CustomerVO customerVO : customers) {
			responseList.add(convertCustomerToResponseDTO(customerVO));
		}

		return responseList;
	}
}