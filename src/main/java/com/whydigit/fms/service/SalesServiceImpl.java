
package com.whydigit.fms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.whydigit.fms.ResponseDTO.BranchResponseDTO;
import com.whydigit.fms.ResponseDTO.CurrencyResponseDTO;
import com.whydigit.fms.ResponseDTO.EnquiryResponseDTO;
import com.whydigit.fms.ResponseDTO.QuatationCommercialTermsResponseDTO;
import com.whydigit.fms.ResponseDTO.QuatationShippingDetailsResponseDTO;
import com.whydigit.fms.ResponseDTO.QuotationAirFreightRatesResponseDTO;
import com.whydigit.fms.ResponseDTO.QuotationCustomerInformationResponseDTO;
import com.whydigit.fms.ResponseDTO.QuotationOriginChargesResponseDTO;
import com.whydigit.fms.ResponseDTO.QuotationResponseDTO;
import com.whydigit.fms.dto.EnquiryDTO;
import com.whydigit.fms.dto.QuatationCommercialTermsDTO;
import com.whydigit.fms.dto.QuatationShippingDetailsDTO;
import com.whydigit.fms.dto.QuotationAirFreightRatesDTO;
import com.whydigit.fms.dto.QuotationCustomerInformationDTO;
import com.whydigit.fms.dto.QuotationDTO;
import com.whydigit.fms.dto.QuotationOriginChargesDTO;
import com.whydigit.fms.entity.BranchVO;
import com.whydigit.fms.entity.CurrencyVO;
import com.whydigit.fms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.fms.entity.EnquiryVO;
import com.whydigit.fms.entity.QuatationCommercialTermsVO;
import com.whydigit.fms.entity.QuatationShippingDetailsVO;
import com.whydigit.fms.entity.QuotationAirFreightRatesVO;
import com.whydigit.fms.entity.QuotationCustomerInformationVO;
import com.whydigit.fms.entity.QuotationOriginChargesVO;
import com.whydigit.fms.entity.QuotationVO;
import com.whydigit.fms.exception.ApplicationException;
import com.whydigit.fms.repo.BranchRepo;
import com.whydigit.fms.repo.CurrencyRepo;
import com.whydigit.fms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.fms.repo.EnquiryRepo;
import com.whydigit.fms.repo.QuatationCommercialTermsRepo;
import com.whydigit.fms.repo.QuatationShippingDetailsRepo;
import com.whydigit.fms.repo.QuotationAirFreightRatesRepo;
import com.whydigit.fms.repo.QuotationCustomerInformationRepo;
import com.whydigit.fms.repo.QuotationOriginChargesRepo;
import com.whydigit.fms.repo.QuotationRepo;

@Service
public class SalesServiceImpl implements SalesService {

	@Autowired
	EnquiryRepo enquiryRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	private QuotationRepo quotationRepo;

	@Autowired
	private QuotationCustomerInformationRepo quotationCustomerInformationRepo;

	@Autowired
	private QuatationShippingDetailsRepo quotationShippingDetailsRepo;

	@Autowired
	private QuotationOriginChargesRepo quotationOriginChargesRepo;

	@Autowired
	private QuotationAirFreightRatesRepo quotationAirFreightRatesRepo;

	@Autowired
	private QuatationCommercialTermsRepo quotationCommercialTermsRepo;

	@Autowired
	private CurrencyRepo currencyRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Override
	@Transactional
	public Map<String, Object> createUpdateEnquiry(EnquiryDTO dto) throws ApplicationException {

		Map<String, Object> response = new HashMap<>();
		EnquiryVO enquiryVO;
		String message;

		String screenCode = "ENQ";

		// REQUEST VALIDATION
		validateEnquiry(dto);

		// CREATE
		if (dto.getId() == null) {

			enquiryVO = new EnquiryVO();

			// Generate Doc ID
//            String docId = enquiryRepo.getEnquiryDocId(
//                    dto.getOrgId(), screenCode);
//
//            if (!StringUtils.hasText(docId)) {
//                throw new ApplicationException(
//                        "Enquiry Doc ID Generation Failed");
//            }
//
//            enquiryVO.setDocId(docId);
//
//            // Update document sequence
//            DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO =
//                    documentTypeMappingDetailsRepo
//                            .findByOrgIdAndScreenCode(
//                                    dto.getOrgId(), screenCode);
//
//            if (documentTypeMappingDetailsVO == null) {
//                throw new ApplicationException(
//                        "Document Type Mapping Not Found for Enquiry");
//            }
//
//            if (documentTypeMappingDetailsVO.getLastno() == 0) {
//                throw new ApplicationException(
//                        "Enquiry Document Sequence Number Not Configured");
//            }
//
//            documentTypeMappingDetailsVO.setLastno(
//                    documentTypeMappingDetailsVO.getLastno() + 1);
//
//            documentTypeMappingDetailsRepo.save(
//                    documentTypeMappingDetailsVO);

			enquiryVO.setCreatedBy(dto.getCreatedBy());
			enquiryVO.setUpdatedBy(dto.getCreatedBy());

			message = "Enquiry Created Successfully";

		} else {

			// UPDATE
			enquiryVO = enquiryRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Enquiry Not Found"));

			enquiryVO.setUpdatedBy(dto.getCreatedBy());

			message = "Enquiry Updated Successfully";
		}

		// HEADER MAPPING
		getEnquiryVOFromDTO(dto, enquiryVO);

		// SAVE
		enquiryVO = enquiryRepo.saveAndFlush(enquiryVO);

		// RESPONSE
		EnquiryResponseDTO responseDTO = convertToResponse(enquiryVO);

		response.put("message", message);
		response.put("enquiryVO", responseDTO);

		return response;
	}

	// ================= VALIDATION =================

	private void validateEnquiry(EnquiryDTO dto) throws ApplicationException {

		if (dto == null) {
			throw new ApplicationException("Enquiry Details Are Required");
		}

		// Customer
		if (!StringUtils.hasText(dto.getCustomer())) {
			throw new ApplicationException("Customer Is Required");
		}

		// Organisation
		if (dto.getOrgId() == null || dto.getOrgId() <= 0) {
			throw new ApplicationException("Valid Organisation ID Is Required");
		}

		// Movement
		if (!StringUtils.hasText(dto.getMovement())) {
			throw new ApplicationException("Movement Is Required");
		}

		String movement = dto.getMovement().trim();

		if (!movement.equalsIgnoreCase("IMPORT") && !movement.equalsIgnoreCase("EXPORT")) {
			throw new ApplicationException("Movement Must Be IMPORT or EXPORT");
		}

		// Shipment Mode
		if (!StringUtils.hasText(dto.getShipmentMode())) {
			throw new ApplicationException("Shipment Mode Is Required");
		}

		String mode = dto.getShipmentMode().trim();

		if (!mode.equalsIgnoreCase("SEA") && !mode.equalsIgnoreCase("AIR") && !mode.equalsIgnoreCase("ROAD")) {
			throw new ApplicationException("Shipment Mode Must Be SEA, AIR or ROAD");
		}

		// Origin
		if (!StringUtils.hasText(dto.getFromLocation())) {
			throw new ApplicationException("Origin Location Is Required");
		}

		// Destination
		if (!StringUtils.hasText(dto.getToLocation())) {
			throw new ApplicationException("Destination Location Is Required");
		}

		// Cargo description
		if (!StringUtils.hasText(dto.getCargoDescription())) {
			throw new ApplicationException("Cargo Description Is Required");
		}

		// Validate numeric values when provided
		validatePositiveIfProvided(dto.getVolumeCbm(), "Cargo Volume (CBM)");

		validatePositiveIfProvided(dto.getGrossWeightKg(), "Gross Weight (KG)");

		validatePositiveIfProvided(dto.getLengthCm(), "Length (CM)");

		validatePositiveIfProvided(dto.getWidthCm(), "Width (CM)");

		validatePositiveIfProvided(dto.getHeightCm(), "Height (CM)");

		if (dto.getNoOfPackages() < 0) {
			throw new ApplicationException("Number Of Packages Cannot Be Negative");
		}

		// SEA VALIDATION
		if (mode.equalsIgnoreCase("SEA")) {

			if (!StringUtils.hasText(dto.getCargoType())) {
				throw new ApplicationException("Cargo Type Is Required For Sea Shipment");
			}

			if (dto.getVolumeCbm() == null || dto.getVolumeCbm().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("Valid Cargo Volume (CBM) Is Required For Sea Shipment");
			}

			if (dto.getGrossWeightKg() == null || dto.getGrossWeightKg().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("Valid Gross Weight Is Required For Sea Shipment");
			}
		}

		// AIR VALIDATION
		else if (mode.equalsIgnoreCase("AIR")) {

			if (dto.getNoOfPackages() <= 0) {
				throw new ApplicationException("Number Of Packages Must Be Greater Than Zero For Air Shipment");
			}

			if (dto.getGrossWeightKg() == null || dto.getGrossWeightKg().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("Valid Gross Weight Is Required For Air Shipment");
			}

			if (!isPositive(dto.getLengthCm())) {
				throw new ApplicationException("Valid Length Is Required For Air Shipment");
			}

			if (!isPositive(dto.getWidthCm())) {
				throw new ApplicationException("Valid Width Is Required For Air Shipment");
			}

			if (!isPositive(dto.getHeightCm())) {
				throw new ApplicationException("Valid Height Is Required For Air Shipment");
			}
		}

		// ROAD VALIDATION
		else if (mode.equalsIgnoreCase("ROAD")) {

			if (!StringUtils.hasText(dto.getVehicleType())) {
				throw new ApplicationException("Vehicle Type Is Required For Road Shipment");
			}

			if (dto.getGrossWeightKg() == null || dto.getGrossWeightKg().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("Valid Gross Weight Is Required For Road Shipment");
			}

			if (dto.getNoOfPackages() <= 0) {
				throw new ApplicationException("Number Of Packages Must Be Greater Than Zero For Road Shipment");
			}
		}
	}

	private void validatePositiveIfProvided(BigDecimal value, String fieldName) throws ApplicationException {

		if (value != null && value.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ApplicationException(fieldName + " Must Be Greater Than Zero");
		}
	}

	private boolean isPositive(BigDecimal value) {
		return value != null && value.compareTo(BigDecimal.ZERO) > 0;
	}

	// ================= HEADER MAPPING =================

	private void getEnquiryVOFromDTO(EnquiryDTO dto, EnquiryVO vo) {

		vo.setCustomer(dto.getCustomer().trim());
		vo.setMovement(dto.getMovement().trim().toUpperCase());
		vo.setShipmentMode(dto.getShipmentMode().trim().toUpperCase());

		vo.setFromLocation(dto.getFromLocation().trim());
		vo.setToLocation(dto.getToLocation().trim());

		// Sea shipment
		vo.setOriginPort(dto.getOriginPort());
		vo.setDestinationPort(dto.getDestinationPort());

		// Air shipment
		vo.setOriginAirport(dto.getOriginAirport());
		vo.setDestinationAirport(dto.getDestinationAirport());

		// Road shipment
		vo.setOriginTerminal(dto.getOriginTerminal());
		vo.setDestinationTerminal(dto.getDestinationTerminal());

		// Cargo details
		vo.setCargoDescription(dto.getCargoDescription());
		vo.setCargoType(dto.getCargoType());
		vo.setVolumeCbm(dto.getVolumeCbm());
		vo.setGrossWeightKg(dto.getGrossWeightKg());
		vo.setNoOfPackages(dto.getNoOfPackages());

		// Dimensions
		vo.setLengthCm(dto.getLengthCm());
		vo.setWidthCm(dto.getWidthCm());
		vo.setHeightCm(dto.getHeightCm());

		vo.setVehicleType(dto.getVehicleType());

		// Organisation details
		vo.setOrgId(dto.getOrgId());
		vo.setBranch(dto.getBranch());

		// Remarks
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setRemarks(dto.getRemarks());
	}

	// ================= RESPONSE MAPPING =================

	private EnquiryResponseDTO convertToResponse(EnquiryVO vo) {

		EnquiryResponseDTO dto = new EnquiryResponseDTO();

		dto.setId(vo.getId());

		dto.setDocId(vo.getDocId());
		dto.setDocDate(vo.getDocDate());

		dto.setCustomer(vo.getCustomer());
		dto.setMovement(vo.getMovement());
		dto.setShipmentMode(vo.getShipmentMode());

		dto.setFromLocation(vo.getFromLocation());
		dto.setToLocation(vo.getToLocation());

		dto.setOriginPort(vo.getOriginPort());
		dto.setDestinationPort(vo.getDestinationPort());

		dto.setOriginAirport(vo.getOriginAirport());
		dto.setDestinationAirport(vo.getDestinationAirport());

		dto.setOriginTerminal(vo.getOriginTerminal());
		dto.setDestinationTerminal(vo.getDestinationTerminal());

		dto.setCargoDescription(vo.getCargoDescription());
		dto.setCargoType(vo.getCargoType());
		dto.setVolumeCbm(vo.getVolumeCbm());
		dto.setGrossWeightKg(vo.getGrossWeightKg());
		dto.setNoOfPackages(vo.getNoOfPackages());

		dto.setLengthCm(vo.getLengthCm());
		dto.setWidthCm(vo.getWidthCm());
		dto.setHeightCm(vo.getHeightCm());

		dto.setVehicleType(vo.getVehicleType());

		dto.setOrgId(vo.getOrgId());
		dto.setBranch(vo.getBranch());

		dto.setActive(vo.getActive());
		dto.setCancel(vo.getCancel());

		dto.setCancelRemarks(vo.getCancelRemarks());
		dto.setRemarks(vo.getRemarks());
		dto.setCreatedBy(vo.getCreatedBy());
		dto.setUpdatedBy(vo.getUpdatedBy());

		return dto;
	}

	@Override
	public EnquiryResponseDTO getEnquiryById(Long id) throws ApplicationException {

		EnquiryVO enquiryVO = enquiryRepo.findById(id).orElseThrow(() -> new ApplicationException("Enquiry Not Found"));

		return convertToResponse(enquiryVO);
	}

	@Override
	public List<EnquiryResponseDTO> getEnquiryByOrgId(Long orgId, String branch) throws ApplicationException {

		List<EnquiryVO> enquiryList = enquiryRepo.findByOrgIdAndBranch(orgId, branch);

		if (enquiryList == null || enquiryList.isEmpty()) {
			throw new ApplicationException("Enquiry Not Found");
		}

		List<EnquiryResponseDTO> responseList = new ArrayList<>();

		for (EnquiryVO enquiryVO : enquiryList) {
			responseList.add(convertToResponse(enquiryVO));
		}

		return responseList;
	}

	@Override
	public String getEnquiryDocId(Long orgId) throws ApplicationException {

		String screenCode = "ENQ";

		String result = enquiryRepo.getEnquiryDocId(orgId, screenCode);

		if (!org.springframework.util.StringUtils.hasText(result)) {
			throw new ApplicationException("Enquiry Doc ID Generation Failed");
		}

		return result;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateQuotation(QuotationDTO dto) throws ApplicationException {

		String screenCode = "QUO";

		Map<String, Object> response = new HashMap<>();
		String message;
		QuotationVO quotationVO;

		// =====================================================
		// CREATE
		// =====================================================

		if (ObjectUtils.isEmpty(dto.getId())) {

			quotationVO = new QuotationVO();

//			String docId = quotationRepo.getQuotationDocId(dto.getOrgId(), screenCode);
//
//			if (!StringUtils.hasText(docId)) {
//				throw new ApplicationException("Quotation Document ID Generation Failed");
//			}
//
//			quotationVO.setDocId(docId);
//
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndScreenCode(dto.getOrgId(), screenCode);
//
//			if (documentTypeMappingDetailsVO != null) {
//				documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//
//				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//			}

			quotationVO.setCreatedBy(dto.getCreatedBy());
			quotationVO.setUpdatedBy(dto.getCreatedBy());

			message = "Quotation Created Successfully";

		} else {

			// =================================================
			// UPDATE
			// =================================================

			quotationVO = quotationRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Quotation Not Found"));

			// Delete old customer information
			List<QuotationCustomerInformationVO> oldCustomerList = quotationCustomerInformationRepo
					.findByQuotationVO(quotationVO);

			quotationCustomerInformationRepo.deleteAll(oldCustomerList);

			// Delete old shipping details
			List<QuatationShippingDetailsVO> oldShippingList = quotationShippingDetailsRepo
					.findByQuotationVO(quotationVO);

			quotationShippingDetailsRepo.deleteAll(oldShippingList);

			// Delete old origin charges
			List<QuotationOriginChargesVO> oldOriginChargesList = quotationOriginChargesRepo
					.findByQuotationVO(quotationVO);

			quotationOriginChargesRepo.deleteAll(oldOriginChargesList);

			// Delete old airfreight rates
			List<QuotationAirFreightRatesVO> oldAirFreightList = quotationAirFreightRatesRepo
					.findByQuotationVO(quotationVO);

			quotationAirFreightRatesRepo.deleteAll(oldAirFreightList);

			// Delete old commercial terms
			List<QuatationCommercialTermsVO> oldCommercialTermsList = quotationCommercialTermsRepo
					.findByQuotationVO(quotationVO);

			quotationCommercialTermsRepo.deleteAll(oldCommercialTermsList);


			quotationVO.setUpdatedBy(dto.getCreatedBy());

			message = "Quotation Updated Successfully";
		}

		// =====================================================
		// HEADER + CHILD MAPPING
		// =====================================================

		getQuotationVOFromDTO(dto, quotationVO);

		// =====================================================
		// SAVE HEADER + CHILDREN
		// =====================================================

		quotationVO = quotationRepo.saveAndFlush(quotationVO);

		// =====================================================
		// RESPONSE
		// =====================================================

		QuotationResponseDTO responseDTO = convertToResponse(quotationVO);

		response.put("message", message);
		response.put("quotationVO", responseDTO);

		return response;
	}

	// =========================================================
	// HEADER + CHILD MAPPING
	// =========================================================

	private void getQuotationVOFromDTO(QuotationDTO dto, QuotationVO quotationVO) throws ApplicationException {

		// =====================================================
		// VALIDATION
		// =====================================================

		if (dto.getOrgId() == null || dto.getOrgId() <= 0) {
			throw new ApplicationException("Valid Organisation ID Is Required");
		}

		if (!StringUtils.hasText(dto.getCompanyName())) {
			throw new ApplicationException("Company Name Is Required");
		}

		if (dto.getExpirationDate() == null) {
			throw new ApplicationException("Expiration Date Is Required");
		}

		if (!StringUtils.hasText(dto.getOriginCode())) {
			throw new ApplicationException("Origin Code Is Required");
		}

		if (!StringUtils.hasText(dto.getOriginCity())) {
			throw new ApplicationException("Origin City Is Required");
		}

		if (!StringUtils.hasText(dto.getDestinationCode())) {
			throw new ApplicationException("Destination Code Is Required");
		}

		if (!StringUtils.hasText(dto.getDestinationCity())) {
			throw new ApplicationException("Destination City Is Required");
		}

		if (!StringUtils.hasText(dto.getPaymentTerms())) {
			throw new ApplicationException("Payment Terms Are Required");
		}

		if (!StringUtils.hasText(dto.getIncoterm())) {
			throw new ApplicationException("Incoterm Is Required");
		}

		if (!StringUtils.hasText(dto.getServiceType())) {
			throw new ApplicationException("Service Type Is Required");
		}

		// =====================================================
		// MASTER MAPPING
		// =====================================================

		if (dto.getCurrency() == null) {
			throw new ApplicationException("Currency Is Required");
		}

		CurrencyVO currency = currencyRepo.findById(dto.getCurrency())
				.orElseThrow(() -> new ApplicationException("Currency Not Found"));

		if (dto.getBranch() == null) {
			throw new ApplicationException("Branch Is Required");
		}

		BranchVO branch = branchRepo.findById(dto.getBranch())
				.orElseThrow(() -> new ApplicationException("Branch Not Found"));

		// =====================================================
		// HEADER FIELDS
		// =====================================================

		quotationVO.setExpirationDate(dto.getExpirationDate());
		quotationVO.setCompanyName(dto.getCompanyName());
		quotationVO.setAgentName(dto.getAgentName());

		quotationVO.setOriginCode(dto.getOriginCode());
		quotationVO.setOriginCity(dto.getOriginCity());
		quotationVO.setDestinationCode(dto.getDestinationCode());
		quotationVO.setDestinationCity(dto.getDestinationCity());

		quotationVO.setPaymentTerms(dto.getPaymentTerms());
		quotationVO.setIncoterm(dto.getIncoterm());
		quotationVO.setServiceType(dto.getServiceType());

		quotationVO.setCurrency(currency);
		quotationVO.setBranch(branch);
		quotationVO.setOrgId(dto.getOrgId());
		quotationVO.setCancelRemarks(dto.getCancelRemarks());
		quotationVO.setRemarks(dto.getRemarks());

		// =====================================================
		// CUSTOMER INFORMATION
		// =====================================================
		// =========================================================
		// CUSTOMER INFORMATION
		// =========================================================

		if (dto.getCustomerInformation() != null && !dto.getCustomerInformation().isEmpty()) {

			for (QuotationCustomerInformationDTO childDTO : dto.getCustomerInformation()) {

				QuotationCustomerInformationVO customerInformationVO = new QuotationCustomerInformationVO();

				customerInformationVO.setCustomerName(childDTO.getCustomerName());

				customerInformationVO.setCustomerPhone(childDTO.getCustomerPhone());

				customerInformationVO.setCustomerEmail(childDTO.getCustomerEmail());

				customerInformationVO.setCustomerAddress(childDTO.getCustomerAddress());

				customerInformationVO.setAgentName(childDTO.getAgentName());

				customerInformationVO.setAgentPhone(childDTO.getAgentPhone());

				customerInformationVO.setAgentEmail(childDTO.getAgentEmail());

				// Header Mapping
				customerInformationVO.setQuotationVO(quotationVO);

				// Add to Header
				quotationVO.getCustomerInformation().add(customerInformationVO);
			}
		}

		// =====================================================
		// SHIPPING DETAILS
		// =====================================================
		// =========================================================
		// SHIPPING DETAILS
		// =========================================================

		if (dto.getShippingDetails() != null && !dto.getShippingDetails().isEmpty()) {

			for (QuatationShippingDetailsDTO childDTO : dto.getShippingDetails()) {

				QuatationShippingDetailsVO shippingDetailsVO = new QuatationShippingDetailsVO();

				shippingDetailsVO.setQuantity(childDTO.getQuantity());
				shippingDetailsVO.setPackageWeight(childDTO.getPackageWeight());
				shippingDetailsVO.setWeightUnit(childDTO.getWeightUnit());

				shippingDetailsVO.setLength(childDTO.getLength());
				shippingDetailsVO.setWidth(childDTO.getWidth());
				shippingDetailsVO.setHeight(childDTO.getHeight());
				shippingDetailsVO.setDimensionUnit(childDTO.getDimensionUnit());

				shippingDetailsVO.setGrossWeight(childDTO.getGrossWeight());
				shippingDetailsVO.setVolumetricWeight(childDTO.getVolumetricWeight());
				shippingDetailsVO.setChargeableWeight(childDTO.getChargeableWeight());
				shippingDetailsVO.setTotalVolumeCbm(childDTO.getTotalVolumeCbm());

				shippingDetailsVO.setCargoDescription(childDTO.getCargoDescription());
				shippingDetailsVO.setCargoType(childDTO.getCargoType());

				// Header Mapping
				shippingDetailsVO.setQuotationVO(quotationVO);

				// Add to Header
				quotationVO.getShippingDetails().add(shippingDetailsVO);
			}
		}

		// =====================================================
		// ORIGIN CHARGES
		// =====================================================
//=========================================================
//ORIGIN CHARGES
//=========================================================

		if (dto.getOriginCharges() != null && !dto.getOriginCharges().isEmpty()) {

			for (QuotationOriginChargesDTO childDTO : dto.getOriginCharges()) {

				QuotationOriginChargesVO originChargesVO = new QuotationOriginChargesVO();

				originChargesVO.setChargeDescription(childDTO.getChargeDescription());

				originChargesVO.setRate(childDTO.getRate());
				originChargesVO.setRateUnit(childDTO.getRateUnit());

				originChargesVO.setMinimumCharge(childDTO.getMinimumCharge());

				originChargesVO.setQuantityUnits(childDTO.getQuantityUnits());

				originChargesVO.setChargeableBasis(childDTO.getChargeableBasis());

				originChargesVO.setChargeAmount(childDTO.getChargeAmount());

				originChargesVO.setCurrency(childDTO.getCurrency());

				originChargesVO.setChargeNotes(childDTO.getChargeNotes());

				// Header Mapping
				originChargesVO.setQuotationVO(quotationVO);

				// Add to Header
				quotationVO.getOriginCharges().add(originChargesVO);
			}
		}
		// =====================================================
		// AIRFREIGHT RATES
		// =====================================================

		if (dto.getAirFreightRates() != null && !dto.getAirFreightRates().isEmpty()) {

			for (QuotationAirFreightRatesDTO childDTO : dto.getAirFreightRates()) {

				QuotationAirFreightRatesVO airFreightRatesVO = new QuotationAirFreightRatesVO();

				airFreightRatesVO.setAirlineName(childDTO.getAirlineName());
				airFreightRatesVO.setAirlineCode(childDTO.getAirlineCode());
				airFreightRatesVO.setProductName(childDTO.getProductName());
				airFreightRatesVO.setDestinationCode(childDTO.getDestinationCode());
				airFreightRatesVO.setTransitVia(childDTO.getTransitVia());
				airFreightRatesVO.setFreightRateType(childDTO.getFreightRateType());

				airFreightRatesVO.setMinimumWeight(childDTO.getMinimumWeight());
				airFreightRatesVO.setRatePerKg(childDTO.getRatePerKg());
				airFreightRatesVO.setFuelRate(childDTO.getFuelRate());
				airFreightRatesVO.setRiskRate(childDTO.getRiskRate());
				airFreightRatesVO.setFreightAmount(childDTO.getFreightAmount());
				airFreightRatesVO.setSurcharges(childDTO.getSurcharges());
				airFreightRatesVO.setAirfreightTotal(childDTO.getAirfreightTotal());
				airFreightRatesVO.setRateRemarks(childDTO.getRateRemarks());

				// Header Mapping
				airFreightRatesVO.setQuotationVO(quotationVO);

				// Add to Header
				quotationVO.getAirFreightRates().add(airFreightRatesVO);
			}
		}
		// =====================================================
		// COMMERCIAL TERMS
		// =====================================================

		// =========================================================
		// COMMERCIAL TERMS
		// =========================================================

		if (dto.getCommercialTerms() != null && !dto.getCommercialTerms().isEmpty()) {

			for (QuatationCommercialTermsDTO childDTO : dto.getCommercialTerms()) {

				QuatationCommercialTermsVO commercialTermsVO = new QuatationCommercialTermsVO();

				commercialTermsVO.setAirlineQuotationTotal(childDTO.getAirlineQuotationTotal());

				commercialTermsVO.setOriginChargeTotal(childDTO.getOriginChargeTotal());

				commercialTermsVO.setInsurancePercentage(childDTO.getInsurancePercentage());

				commercialTermsVO.setCargoValue(childDTO.getCargoValue());

				commercialTermsVO.setProfitSharingPercentage(childDTO.getProfitSharingPercentage());

				commercialTermsVO.setBankExchangePercentage(childDTO.getBankExchangePercentage());

				commercialTermsVO.setFreeStorageDays(childDTO.getFreeStorageDays());

				commercialTermsVO.setStorageRate(childDTO.getStorageRate());

				commercialTermsVO.setStorageMinimumCharge(childDTO.getStorageMinimumCharge());

				commercialTermsVO.setRateValidityRemarks(childDTO.getRateValidityRemarks());

				commercialTermsVO.setTermsAndConditions(childDTO.getTermsAndConditions());

				commercialTermsVO.setGeneralRemarks(childDTO.getGeneralRemarks());

				// Header Mapping
				commercialTermsVO.setQuotationVO(quotationVO);

				// Add to Header
				quotationVO.getCommercialTerms().add(commercialTermsVO);
			}
		}

	}

	// =========================================================
	// GET QUOTATION BY ID
	// =========================================================
	@Override
	@Transactional
	public QuotationResponseDTO getQuotationById(Long id)
	        throws ApplicationException {

	    QuotationVO quotationVO = quotationRepo.findById(id)
	            .orElseThrow(() -> new ApplicationException("Quotation Not Found"));

	    return convertToResponse(quotationVO);
	}

	// =========================================================
	// GET QUOTATION BY ORG AND BRANCH
	// =========================================================

	@Override
	@Transactional
	public List<QuotationResponseDTO> getQuotationByOrgIdAndBranch(
	        Long orgId, Long branch) throws ApplicationException {

	    List<QuotationVO> quotationList =
	            quotationRepo.findByOrgIdAndBranch(orgId, branch);

	    List<QuotationResponseDTO> responseList = new ArrayList<>();

	    for (QuotationVO quotationVO : quotationList) {
	        responseList.add(convertToResponse(quotationVO));
	    }

	    return responseList;
	}

	// =========================================================
	// CONVERT ENTITY TO RESPONSE DTO
	// =========================================================

	private QuotationResponseDTO convertToResponse(QuotationVO vo) {

		QuotationResponseDTO dto = new QuotationResponseDTO();

		// =========================================================
		// HEADER
		// =========================================================
		dto.setId(vo.getId());
		dto.setDocId(vo.getDocId());
		dto.setDocDate(vo.getDocDate());
		dto.setExpirationDate(vo.getExpirationDate());

		dto.setCompanyName(vo.getCompanyName());
		dto.setAgentName(vo.getAgentName());

		dto.setOriginCode(vo.getOriginCode());
		dto.setOriginCity(vo.getOriginCity());
		dto.setDestinationCode(vo.getDestinationCode());
		dto.setDestinationCity(vo.getDestinationCity());

		dto.setPaymentTerms(vo.getPaymentTerms());
		dto.setIncoterm(vo.getIncoterm());
		dto.setServiceType(vo.getServiceType());

		// CURRENCY
		if (vo.getCurrency() != null) {

		    CurrencyResponseDTO currencyDTO = new CurrencyResponseDTO();

		    currencyDTO.setId(vo.getCurrency().getId());
		    currencyDTO.setCurrencyName(vo.getCurrency().getCurrency());

		    dto.setCurrency(currencyDTO);
		}

		// BRANCH
		if (vo.getBranch() != null) {

		    BranchResponseDTO branchDTO = new BranchResponseDTO();

		    branchDTO.setId(vo.getBranch().getId());
		    branchDTO.setBranch(vo.getBranch().getBranch());
		    branchDTO.setBranchCode(vo.getBranch().getBranchCode());

		    dto.setBranch(branchDTO);
		}

		dto.setOrgId(vo.getOrgId());
		dto.setActive(vo.isActive());
		dto.setCancel(vo.isCancel());
		dto.setCancelRemarks(vo.getCancelRemarks());
		dto.setRemarks(vo.getRemarks());
		dto.setCreatedBy(vo.getCreatedBy());

		// =========================================================
		// CUSTOMER INFORMATION
		// =========================================================
		if (vo.getCustomerInformation() != null) {

			for (QuotationCustomerInformationVO child : vo.getCustomerInformation()) {

				QuotationCustomerInformationResponseDTO childDTO = new QuotationCustomerInformationResponseDTO();

				childDTO.setId(child.getId());
				childDTO.setCustomerName(child.getCustomerName());
				childDTO.setCustomerPhone(child.getCustomerPhone());
				childDTO.setCustomerEmail(child.getCustomerEmail());
				childDTO.setCustomerAddress(child.getCustomerAddress());

				childDTO.setAgentName(child.getAgentName());
				childDTO.setAgentPhone(child.getAgentPhone());
				childDTO.setAgentEmail(child.getAgentEmail());

				dto.getCustomerInformation().add(childDTO);
			}
		}

		// =========================================================
		// SHIPPING DETAILS
		// =========================================================
		if (vo.getShippingDetails() != null) {

			for (QuatationShippingDetailsVO child : vo.getShippingDetails()) {

				QuatationShippingDetailsResponseDTO childDTO = new QuatationShippingDetailsResponseDTO();

				childDTO.setId(child.getId());
				childDTO.setQuantity(child.getQuantity());
				childDTO.setPackageWeight(child.getPackageWeight());
				childDTO.setWeightUnit(child.getWeightUnit());

				childDTO.setLength(child.getLength());
				childDTO.setWidth(child.getWidth());
				childDTO.setHeight(child.getHeight());
				childDTO.setDimensionUnit(child.getDimensionUnit());

				childDTO.setGrossWeight(child.getGrossWeight());
				childDTO.setVolumetricWeight(child.getVolumetricWeight());
				childDTO.setChargeableWeight(child.getChargeableWeight());
				childDTO.setTotalVolumeCbm(child.getTotalVolumeCbm());

				childDTO.setCargoDescription(child.getCargoDescription());
				childDTO.setCargoType(child.getCargoType());

				dto.getShippingDetails().add(childDTO);
			}
		}

		// =========================================================
		// ORIGIN CHARGES
		// =========================================================
		if (vo.getOriginCharges() != null) {

			for (QuotationOriginChargesVO child : vo.getOriginCharges()) {

				QuotationOriginChargesResponseDTO childDTO = new QuotationOriginChargesResponseDTO();

				childDTO.setId(child.getId());
				childDTO.setChargeDescription(child.getChargeDescription());
				childDTO.setRate(child.getRate());
				childDTO.setRateUnit(child.getRateUnit());
				childDTO.setMinimumCharge(child.getMinimumCharge());

				childDTO.setQuantityUnits(child.getQuantityUnits());
				childDTO.setChargeableBasis(child.getChargeableBasis());
				childDTO.setChargeAmount(child.getChargeAmount());
				childDTO.setCurrency(child.getCurrency());
				childDTO.setChargeNotes(child.getChargeNotes());

				dto.getOriginCharges().add(childDTO);
			}
		}

		// =========================================================
		// AIR FREIGHT RATES
		// =========================================================
		if (vo.getAirFreightRates() != null) {

			for (QuotationAirFreightRatesVO child : vo.getAirFreightRates()) {

				QuotationAirFreightRatesResponseDTO childDTO = new QuotationAirFreightRatesResponseDTO();

				childDTO.setId(child.getId());
				childDTO.setAirlineName(child.getAirlineName());
				childDTO.setAirlineCode(child.getAirlineCode());
				childDTO.setProductName(child.getProductName());

				childDTO.setDestinationCode(child.getDestinationCode());
				childDTO.setTransitVia(child.getTransitVia());
				childDTO.setFreightRateType(child.getFreightRateType());

				childDTO.setMinimumWeight(child.getMinimumWeight());
				childDTO.setRatePerKg(child.getRatePerKg());
				childDTO.setFuelRate(child.getFuelRate());
				childDTO.setRiskRate(child.getRiskRate());

				childDTO.setFreightAmount(child.getFreightAmount());
				childDTO.setSurcharges(child.getSurcharges());
				childDTO.setAirfreightTotal(child.getAirfreightTotal());
				childDTO.setRateRemarks(child.getRateRemarks());

				dto.getAirFreightRates().add(childDTO);
			}
		}

		// =========================================================
		// COMMERCIAL TERMS
		// =========================================================
		if (vo.getCommercialTerms() != null) {

			for (QuatationCommercialTermsVO child : vo.getCommercialTerms()) {

				QuatationCommercialTermsResponseDTO childDTO = new QuatationCommercialTermsResponseDTO();

				childDTO.setId(child.getId());
				childDTO.setAirlineQuotationTotal(child.getAirlineQuotationTotal());
				childDTO.setOriginChargeTotal(child.getOriginChargeTotal());

				childDTO.setInsurancePercentage(child.getInsurancePercentage());
				childDTO.setCargoValue(child.getCargoValue());

				childDTO.setProfitSharingPercentage(child.getProfitSharingPercentage());
				childDTO.setBankExchangePercentage(child.getBankExchangePercentage());

				childDTO.setFreeStorageDays(child.getFreeStorageDays());
				childDTO.setStorageRate(child.getStorageRate());
				childDTO.setStorageMinimumCharge(child.getStorageMinimumCharge());

				childDTO.setRateValidityRemarks(child.getRateValidityRemarks());
				childDTO.setTermsAndConditions(child.getTermsAndConditions());
				childDTO.setGeneralRemarks(child.getGeneralRemarks());

				dto.getCommercialTerms().add(childDTO);
			}
		}

		return dto;
	}

}
