package com.whydigit.fms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.fms.ResponseDTO.BranchResponsesDTO;
import com.whydigit.fms.ResponseDTO.ProductResponseDTO;
import com.whydigit.fms.ResponseDTO.ServiceResponseDTO;
import com.whydigit.fms.dto.ProductDTO;
import com.whydigit.fms.dto.ServiceDTO;
import com.whydigit.fms.entity.BranchVO;
import com.whydigit.fms.entity.ProductVO;
import com.whydigit.fms.entity.ServiceVO;
import com.whydigit.fms.repo.BranchRepo;
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
}