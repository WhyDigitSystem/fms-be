package com.whydigit.fms.service;

import java.util.List;
import java.util.Map;

import com.whydigit.fms.ResponseDTO.ConfigResponseDTO;
import com.whydigit.fms.ResponseDTO.CustomerResponseDTO;
import com.whydigit.fms.ResponseDTO.PortResponseDTO;
import com.whydigit.fms.ResponseDTO.ProductResponseDTO;
import com.whydigit.fms.ResponseDTO.ServiceResponseDTO;
import com.whydigit.fms.dto.ConfigDTO;
import com.whydigit.fms.dto.CustomerDTO;
import com.whydigit.fms.dto.PortDTO;
import com.whydigit.fms.dto.ProductDTO;
import com.whydigit.fms.dto.ServiceDTO;

public interface BasicMasterService {

	// PRODUCT
	Map<String, Object> createUpdateProduct(ProductDTO productDTO);

	ProductResponseDTO getProductById(Long id);

	List<ProductResponseDTO> getProductsByOrgIdAndBranch(Long orgId, Long branch);

	// SERVICE
	Map<String, Object> createUpdateService(ServiceDTO serviceDTO);

	ServiceResponseDTO getServiceById(Long id);

	List<ServiceResponseDTO> getServicesByOrgIdAndBranch(Long orgId, Long branch);

	// PORT
	Map<String, Object> createUpdatePort(PortDTO portDTO);

	PortResponseDTO getPortById(Long id);

	List<PortResponseDTO> getPortsByOrgIdAndBranch(Long orgId, Long branch);

	// CONFIGURATION

	Map<String, Object> createUpdateConfig(ConfigDTO configDTO);

	ConfigResponseDTO getConfigById(Long id);

	List<ConfigResponseDTO> getConfigByOrgIdAndBranch(Long orgId, Long branch);

	// CUSTOMER
	Map<String, Object> createUpdateCustomer(CustomerDTO customerDTO);

	CustomerResponseDTO getCustomerById(Long id);

	List<CustomerResponseDTO> getCustomersByBranchAndOrgId(Long orgId, Long branchId);
}