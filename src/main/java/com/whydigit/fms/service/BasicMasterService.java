package com.whydigit.fms.service;

import java.util.List;
import java.util.Map;

import com.whydigit.fms.ResponseDTO.ProductResponseDTO;
import com.whydigit.fms.ResponseDTO.ServiceResponseDTO;
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
}