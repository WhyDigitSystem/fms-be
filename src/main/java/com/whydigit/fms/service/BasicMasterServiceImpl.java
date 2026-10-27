package com.whydigit.fms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whydigit.fms.ResponseDTO.BranchResponsesDTO;
import com.whydigit.fms.ResponseDTO.ConfigModeResponsesDTO;
import com.whydigit.fms.ResponseDTO.ConfigProductResponsesDTO;
import com.whydigit.fms.ResponseDTO.ConfigResponseDTO;
import com.whydigit.fms.ResponseDTO.ConfigServiceResponsesDTO;
import com.whydigit.fms.ResponseDTO.CountryResponseDTO;
import com.whydigit.fms.ResponseDTO.PortResponseDTO;
import com.whydigit.fms.ResponseDTO.ProductResponseDTO;
import com.whydigit.fms.ResponseDTO.ServiceResponseDTO;
import com.whydigit.fms.dto.ConfigDTO;
import com.whydigit.fms.dto.ConfigModeDTO;
import com.whydigit.fms.dto.ConfigProductDTO;
import com.whydigit.fms.dto.ConfigServiceDTO;
import com.whydigit.fms.dto.PortDTO;
import com.whydigit.fms.dto.ProductDTO;
import com.whydigit.fms.dto.ServiceDTO;
import com.whydigit.fms.entity.BranchVO;
import com.whydigit.fms.entity.ConfigModeVO;
import com.whydigit.fms.entity.ConfigProductVO;
import com.whydigit.fms.entity.ConfigServiceVO;
import com.whydigit.fms.entity.ConfigVO;
import com.whydigit.fms.entity.CountryVO;
import com.whydigit.fms.entity.PortVO;
import com.whydigit.fms.entity.ProductVO;
import com.whydigit.fms.entity.ServiceVO;
import com.whydigit.fms.repo.BranchRepo;
import com.whydigit.fms.repo.ConfigModeRepo;
import com.whydigit.fms.repo.ConfigProductRepo;
import com.whydigit.fms.repo.ConfigRepo;
import com.whydigit.fms.repo.ConfigServiceRepo;
import com.whydigit.fms.repo.CountryRepository;
import com.whydigit.fms.repo.PortRepo;
import com.whydigit.fms.repo.ProductRepo;
import com.whydigit.fms.repo.ServiceRepo;

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

	@Override
	@Transactional
	public Map<String, Object> createUpdateConfig(ConfigDTO dto) {

		Map<String, Object> response = new HashMap<>();

		String message;
		ConfigVO configVO;

		// =====================================================
		// CREATE
		// =====================================================

		if (dto.getId() == null) {

			configVO = new ConfigVO();

			configVO.setCreatedBy(dto.getCreatedBy());
			configVO.setUpdatedBy(dto.getCreatedBy());

			message = "Configuration Created Successfully";

		} else {

			// =====================================================
			// UPDATE
			// =====================================================

			configVO = configRepository.findById(dto.getId())
					.orElseThrow(() -> new RuntimeException("Configuration Not Found with id: " + dto.getId()));

			// Delete old configuration modes.
			List<ConfigModeVO> oldModes = configModeRepository.findByConfig(configVO);

			configModeRepository.deleteAll(oldModes);

			// Delete old configuration products.
			List<ConfigProductVO> oldProducts = configProductRepository.findByConfig(configVO);

			configProductRepository.deleteAll(oldProducts);

			// Delete old configuration services.
			List<ConfigServiceVO> oldServices = configServiceRepository.findByConfig(configVO);

			configServiceRepository.deleteAll(oldServices);

			configVO.setUpdatedBy(dto.getCreatedBy());

			message = "Configuration Updated Successfully";
		}

		// =====================================================
		// HEADER + CHILD MAPPING
		// =====================================================

		getConfigVOFromDTO(dto, configVO);

		// =====================================================
		// SAVE HEADER
		// =====================================================

		configVO = configRepository.saveAndFlush(configVO);

		// =====================================================
		// RESPONSE
		// =====================================================

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

}