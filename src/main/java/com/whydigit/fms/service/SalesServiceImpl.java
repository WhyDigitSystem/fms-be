
package com.whydigit.fms.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.whydigit.fms.ResponseDTO.BookingCargoResponseDTO;
import com.whydigit.fms.ResponseDTO.BookingCommercialResponseDTO;
import com.whydigit.fms.ResponseDTO.BookingEquipmentResponseDTO;
import com.whydigit.fms.ResponseDTO.BookingInstructionResponseDTO;
import com.whydigit.fms.ResponseDTO.BookingPartyResponseDTO;
import com.whydigit.fms.ResponseDTO.BookingResponseDTO;
import com.whydigit.fms.ResponseDTO.BookingRoutingResponseDTO;
import com.whydigit.fms.ResponseDTO.BranchResponseDTO;
import com.whydigit.fms.ResponseDTO.CityResponseDetailsDTO;
import com.whydigit.fms.ResponseDTO.CurrencyResponseDTO;
import com.whydigit.fms.ResponseDTO.EnquiryResponseDTO;
import com.whydigit.fms.ResponseDTO.PortResponseDetailsDTO;
import com.whydigit.fms.ResponseDTO.QuatationCommercialTermsResponseDTO;
import com.whydigit.fms.ResponseDTO.QuatationShippingDetailsResponseDTO;
import com.whydigit.fms.ResponseDTO.QuotationAirFreightRatesResponseDTO;
import com.whydigit.fms.ResponseDTO.QuotationCustomerInformationResponseDTO;
import com.whydigit.fms.ResponseDTO.QuotationOriginChargesResponseDTO;
import com.whydigit.fms.ResponseDTO.QuotationResponseDTO;
import com.whydigit.fms.dto.BookingCargoDTO;
import com.whydigit.fms.dto.BookingCommercialDTO;
import com.whydigit.fms.dto.BookingDTO;
import com.whydigit.fms.dto.BookingEquipmentDTO;
import com.whydigit.fms.dto.BookingInstructionDTO;
import com.whydigit.fms.dto.BookingPartyDTO;
import com.whydigit.fms.dto.BookingRoutingDTO;
import com.whydigit.fms.dto.EnquiryDTO;
import com.whydigit.fms.dto.QuatationCommercialTermsDTO;
import com.whydigit.fms.dto.QuatationShippingDetailsDTO;
import com.whydigit.fms.dto.QuotationAirFreightRatesDTO;
import com.whydigit.fms.dto.QuotationCustomerInformationDTO;
import com.whydigit.fms.dto.QuotationDTO;
import com.whydigit.fms.dto.QuotationOriginChargesDTO;
import com.whydigit.fms.entity.BookingCargoVO;
import com.whydigit.fms.entity.BookingCommercialVO;
import com.whydigit.fms.entity.BookingEquipmentVO;
import com.whydigit.fms.entity.BookingInstructionVO;
import com.whydigit.fms.entity.BookingPartyVO;
import com.whydigit.fms.entity.BookingRoutingVO;
import com.whydigit.fms.entity.BookingVO;
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
import com.whydigit.fms.repo.BookingCargoRepo;
import com.whydigit.fms.repo.BookingCommercialRepo;
import com.whydigit.fms.repo.BookingEquipmentRepo;
import com.whydigit.fms.repo.BookingInstructionRepo;
import com.whydigit.fms.repo.BookingPartyRepo;
import com.whydigit.fms.repo.BookingRepo;
import com.whydigit.fms.repo.BookingRoutingRepo;
import com.whydigit.fms.repo.BranchRepo;
import com.whydigit.fms.repo.CityRepo;
import com.whydigit.fms.repo.CurrencyRepo;
import com.whydigit.fms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.fms.repo.EnquiryRepo;
import com.whydigit.fms.repo.PortRepo;
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

	@Autowired
	private BookingRepo bookingRepo;
	
	@Autowired
	private BookingRoutingRepo bookingRoutingRepo;

	@Autowired
	private BookingPartyRepo bookingPartyRepo;

	@Autowired
	private BookingCargoRepo bookingCargoRepo;

	@Autowired
	private BookingEquipmentRepo bookingEquipmentRepo;

	@Autowired
	private BookingCommercialRepo bookingCommercialRepo;

	@Autowired
	private BookingInstructionRepo bookingInstructionRepo;
	
	@Autowired
    PortRepo portRepo;
	
	@Autowired
	CityRepo cityRepo;

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
            String docId = enquiryRepo.getEnquiryDocId(
                    dto.getOrgId(), screenCode);

            if (!StringUtils.hasText(docId)) {
                throw new ApplicationException(
                        "Enquiry Doc ID Generation Failed");
            }

            enquiryVO.setDocId(docId);

            // Update document sequence
            DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO =
                    documentTypeMappingDetailsRepo
                            .findByOrgIdAndScreenCode(
                                    dto.getOrgId(), screenCode);

            if (documentTypeMappingDetailsVO == null) {
                throw new ApplicationException(
                        "Document Type Mapping Not Found for Enquiry");
            }

            if (documentTypeMappingDetailsVO.getLastNo() == 0) {
                throw new ApplicationException(
                        "Enquiry Document Sequence Number Not Configured");
            }

            documentTypeMappingDetailsVO.setLastNo(
                    documentTypeMappingDetailsVO.getLastNo() + 1);

            documentTypeMappingDetailsRepo.save(
                    documentTypeMappingDetailsVO);

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
		if (!StringUtils.hasText(dto.getCompany())) {
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

		// Origin Location
		if (dto.getFromLocation() == null ) {
		    throw new ApplicationException("Origin Location Is Required");
		}

		// Destination Location
		if (dto.getToLocation() == null ) {
		    throw new ApplicationException("Destination Location Is Required");
		}

		// Cargo description
		if (!StringUtils.hasText(dto.getCargoDescription())) {
			throw new ApplicationException("Cargo Description Is Required");
		}

		// Validate numeric values when provided
//		validatePositiveIfProvided(dto.getVolumeCbm(), "Cargo Volume (CBM)");

		validatePositiveIfProvided(dto.getGrossWeightKg(), "Gross Weight (KG)");

//		validatePositiveIfProvided(dto.getLengthCm(), "Length (CM)");
//
//		validatePositiveIfProvided(dto.getWidthCm(), "Width (CM)");
//
//		validatePositiveIfProvided(dto.getHeightCm(), "Height (CM)");

//		if (dto.getNoOfPackages() < 0) {
//			throw new ApplicationException("Number Of Packages Cannot Be Negative");
//		}

		// SEA VALIDATION
		if (mode.equalsIgnoreCase("SEA")) {

			if (!StringUtils.hasText(dto.getCargoType())) {
				throw new ApplicationException("Cargo Type Is Required For Sea Shipment");
			}

//			if (dto.getVolumeCbm() == null || dto.getVolumeCbm().compareTo(BigDecimal.ZERO) <= 0) {
//				throw new ApplicationException("Valid Cargo Volume (CBM) Is Required For Sea Shipment");
//			}

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

	private void getEnquiryVOFromDTO(EnquiryDTO dto, EnquiryVO vo) throws ApplicationException {

		vo.setCompany(dto.getCompany().trim());
		vo.setMovement(dto.getMovement().trim().toUpperCase());
		vo.setShipmentMode(dto.getShipmentMode().trim().toUpperCase());

		// Shipment Route
		vo.setFromLocation(
		    cityRepo.findById(dto.getFromLocation())
		        .orElseThrow(() -> new ApplicationException("Origin Location Not Found"))
		);

		vo.setToLocation(
		    cityRepo.findById(dto.getToLocation())
		        .orElseThrow(() -> new ApplicationException("Destination Location Not Found"))
		);

		// Sea Shipment
		vo.setOriginPort(
		    portRepo.findById(dto.getOriginPort())
		        .orElseThrow(() -> new ApplicationException("Origin Port Not Found"))
		);

		vo.setDestinationPort(
		    portRepo.findById(dto.getDestinationPort())
		        .orElseThrow(() -> new ApplicationException("Destination Port Not Found"))
		);

		// Air Shipment
		vo.setOriginAirport(
		    portRepo.findById(dto.getOriginAirport())
		        .orElseThrow(() -> new ApplicationException("Origin Airport Not Found"))
		);

		vo.setDestinationAirport(
		    portRepo.findById(dto.getDestinationAirport())
		        .orElseThrow(() -> new ApplicationException("Destination Airport Not Found"))
		);

		// Road Shipment
		vo.setOriginTerminal(
		    portRepo.findById(dto.getOriginTerminal())
		        .orElseThrow(() -> new ApplicationException("Origin Terminal Not Found"))
		);

		vo.setDestinationTerminal(
		    portRepo.findById(dto.getDestinationTerminal())
		        .orElseThrow(() -> new ApplicationException("Destination Terminal Not Found"))
		);
		
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
		BranchVO branchVO = branchRepo.findById(dto.getBranch())
		        .orElseThrow(() -> new ApplicationException("Branch Not Found"));

		vo.setBranch(branchVO);
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

		dto.setCompany(vo.getCompany());
		dto.setMovement(vo.getMovement());
		dto.setShipmentMode(vo.getShipmentMode());

		// Shipment Route
		if (vo.getFromLocation() != null) {
		    CityResponseDetailsDTO fromLocation = new CityResponseDetailsDTO();
		    fromLocation.setId(vo.getFromLocation().getId());
		    fromLocation.setCity(vo.getFromLocation().getCityName());
		    fromLocation.setCityCode(vo.getFromLocation().getCityCode());

		    dto.setFromLocation(fromLocation);
		}

		if (vo.getToLocation() != null) {
		    CityResponseDetailsDTO toLocation = new CityResponseDetailsDTO();
		    toLocation.setId(vo.getToLocation().getId());
		    toLocation.setCity(vo.getToLocation().getCityName());
		    toLocation.setCityCode(vo.getToLocation().getCityCode());

		    dto.setToLocation(toLocation);
		}


		// Sea Shipment
		if (vo.getOriginPort() != null) {
		    PortResponseDetailsDTO originPort = new PortResponseDetailsDTO();
		    originPort.setId(vo.getOriginPort().getId());
		    originPort.setPortType(vo.getOriginPort().getPortType());
		    originPort.setPortCode(vo.getOriginPort().getPortCode());
		    originPort.setPortName(vo.getOriginPort().getPortName());

		    dto.setOriginPort(originPort);
		}

		if (vo.getDestinationPort() != null) {
		    PortResponseDetailsDTO destinationPort = new PortResponseDetailsDTO();
		    destinationPort.setId(vo.getDestinationPort().getId());
		    destinationPort.setPortType(vo.getDestinationPort().getPortType());
		    destinationPort.setPortCode(vo.getDestinationPort().getPortCode());
		    destinationPort.setPortName(vo.getDestinationPort().getPortName());

		    dto.setDestinationPort(destinationPort);
		}


		// Air Shipment
		if (vo.getOriginAirport() != null) {
		    PortResponseDetailsDTO originAirport = new PortResponseDetailsDTO();
		    originAirport.setId(vo.getOriginAirport().getId());
		    originAirport.setPortType(vo.getOriginAirport().getPortType());
		    originAirport.setPortCode(vo.getOriginAirport().getPortCode());
		    originAirport.setPortName(vo.getOriginAirport().getPortName());

		    dto.setOriginAirport(originAirport);
		}

		if (vo.getDestinationAirport() != null) {
		    PortResponseDetailsDTO destinationAirport = new PortResponseDetailsDTO();
		    destinationAirport.setId(vo.getDestinationAirport().getId());
		    destinationAirport.setPortType(vo.getDestinationAirport().getPortType());
		    destinationAirport.setPortCode(vo.getDestinationAirport().getPortCode());
		    destinationAirport.setPortName(vo.getDestinationAirport().getPortName());

		    dto.setDestinationAirport(destinationAirport);
		}


		// Road Shipment
		if (vo.getOriginTerminal() != null) {
		    PortResponseDetailsDTO originTerminal = new PortResponseDetailsDTO();
		    originTerminal.setId(vo.getOriginTerminal().getId());
		    originTerminal.setPortType(vo.getOriginTerminal().getPortType());
		    originTerminal.setPortCode(vo.getOriginTerminal().getPortCode());
		    originTerminal.setPortName(vo.getOriginTerminal().getPortName());

		    dto.setOriginTerminal(originTerminal);
		}

		if (vo.getDestinationTerminal() != null) {
		    PortResponseDetailsDTO destinationTerminal = new PortResponseDetailsDTO();
		    destinationTerminal.setId(vo.getDestinationTerminal().getId());
		    destinationTerminal.setPortType(vo.getDestinationTerminal().getPortType());
		    destinationTerminal.setPortCode(vo.getDestinationTerminal().getPortCode());
		    destinationTerminal.setPortName(vo.getDestinationTerminal().getPortName());

		    dto.setDestinationTerminal(destinationTerminal);
		}
		
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
		if (vo.getBranch() != null) {
		    BranchResponseDTO branchDTO = new BranchResponseDTO();

		    branchDTO.setId(vo.getBranch().getId());
		    branchDTO.setBranch(vo.getBranch().getBranch());
		    branchDTO.setBranchCode(vo.getBranch().getBranchCode());

		    dto.setBranch(branchDTO);
		}
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
	public List<EnquiryResponseDTO> getEnquiryByOrgId(Long orgId, Long branch) throws ApplicationException {

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
	public String getQuotationDocId(Long orgId) throws ApplicationException {

		String screenCode = "QUO";

		String result = quotationRepo.getQuotationDocId(orgId, screenCode);

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

			String docId = quotationRepo.getQuotationDocId(dto.getOrgId(), screenCode);

			if (!StringUtils.hasText(docId)) {
				throw new ApplicationException("Quotation Document ID Generation Failed");
			}

			quotationVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(dto.getOrgId(), screenCode);

			if (documentTypeMappingDetailsVO != null) {
				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			}

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

		// Origin City
		if (dto.getOriginCity() == null) {
		    throw new ApplicationException("Origin City Is Required");
		}

		// Destination City
		if (dto.getDestinationCity() == null) {
		    throw new ApplicationException("Destination City Is Required");
		}

		// Origin Port
		if (dto.getOriginPort() == null) {
		    throw new ApplicationException("Origin Port Is Required");
		}

		// Destination Port
		if (dto.getDestinationPort() == null) {
		    throw new ApplicationException("Destination Port Is Required");
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

		quotationVO.setEnquiryNo(dto.getEnquiryNo());
		quotationVO.setEnquiryDate(dto.getEnquiryDate());

		quotationVO.setExpirationDate(dto.getExpirationDate());
		quotationVO.setCompanyName(dto.getCompanyName());
		quotationVO.setAgentName(dto.getAgentName());

		// Origin City
		quotationVO.setOriginCity(
		    cityRepo.findById(dto.getOriginCity())
		        .orElseThrow(() -> new ApplicationException("Origin City Not Found"))
		);

		// Destination City
		quotationVO.setDestinationCity(
		    cityRepo.findById(dto.getDestinationCity())
		        .orElseThrow(() -> new ApplicationException("Destination City Not Found"))
		);

		// Origin Port
		quotationVO.setOriginPort(
		    portRepo.findById(dto.getOriginPort())
		        .orElseThrow(() -> new ApplicationException("Origin Port Not Found"))
		);

		// Destination Port
		quotationVO.setDestinationPort(
		    portRepo.findById(dto.getDestinationPort())
		        .orElseThrow(() -> new ApplicationException("Destination Port Not Found"))
		);
		

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
	public QuotationResponseDTO getQuotationById(Long id) throws ApplicationException {

		QuotationVO quotationVO = quotationRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Quotation Not Found"));

		return convertToResponse(quotationVO);
	}

	// =========================================================
	// GET QUOTATION BY ORG AND BRANCH
	// =========================================================

	@Override
	@Transactional
	public List<QuotationResponseDTO> getQuotationByOrgIdAndBranch(Long orgId, Long branch)
			throws ApplicationException {

		List<QuotationVO> quotationList = quotationRepo.findByOrgIdAndBranch(orgId, branch);

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
        dto.setEnquiryNo(vo.getEnquiryNo());
        dto.setEnquiryDate(vo.getEnquiryDate());
        dto.setCompanyName(vo.getCompanyName());
		dto.setAgentName(vo.getAgentName());

		// Origin City
		if (vo.getOriginCity() != null) {
		    CityResponseDetailsDTO originCity = new CityResponseDetailsDTO();

		    originCity.setId(vo.getOriginCity().getId());
		    originCity.setCity(vo.getOriginCity().getCityName());
		    originCity.setCityCode(vo.getOriginCity().getCityCode());

		    dto.setOriginCity(originCity);
		}

		// Destination City
		if (vo.getDestinationCity() != null) {
		    CityResponseDetailsDTO destinationCity = new CityResponseDetailsDTO();

		    destinationCity.setId(vo.getDestinationCity().getId());
		    destinationCity.setCity(vo.getDestinationCity().getCityName());
		    destinationCity.setCityCode(vo.getDestinationCity().getCityCode());

		    dto.setDestinationCity(destinationCity);
		}

		// Origin Port
		if (vo.getOriginPort() != null) {
		    PortResponseDetailsDTO originPort = new PortResponseDetailsDTO();

		    originPort.setId(vo.getOriginPort().getId());
		    originPort.setPortType(vo.getOriginPort().getPortType());
		    originPort.setPortCode(vo.getOriginPort().getPortCode());
		    originPort.setPortName(vo.getOriginPort().getPortName());

		    dto.setOriginPort(originPort);
		}

		// Destination Port
		if (vo.getDestinationPort() != null) {
		    PortResponseDetailsDTO destinationPort = new PortResponseDetailsDTO();

		    destinationPort.setId(vo.getDestinationPort().getId());
		    destinationPort.setPortType(vo.getDestinationPort().getPortType());
		    destinationPort.setPortCode(vo.getDestinationPort().getPortCode());
		    destinationPort.setPortName(vo.getDestinationPort().getPortName());

		    dto.setDestinationPort(destinationPort);
		}
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

	
	//Booking
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Map<String, Object> createUpdateBooking(BookingDTO dto, MultipartFile[] files)
	        throws ApplicationException {

	    if (dto == null) {
	        throw new ApplicationException("Booking Details Are Required");
	    }

	    BookingVO bookingVO;
	    String message;

	    if (ObjectUtils.isNotEmpty(dto.getId())) {
	        bookingVO = bookingRepo.findById(dto.getId())
	                .orElseThrow(() -> new ApplicationException("Booking Not Found"));
	        bookingVO.setUpdatedBy(dto.getCreatedBy());
	        
	        // Delete old routing details
		     List<BookingRoutingVO> oldRoutingList = bookingRoutingRepo.findByBookingVO(bookingVO);
		     bookingRoutingRepo.deleteAll(oldRoutingList);

		     // Delete old party details
		     List<BookingPartyVO> oldPartyList = bookingPartyRepo.findByBookingVO(bookingVO);
		     bookingPartyRepo.deleteAll(oldPartyList);

		     // Delete old cargo details
		     List<BookingCargoVO> oldCargoList = bookingCargoRepo.findByBookingVO(bookingVO);
		     bookingCargoRepo.deleteAll(oldCargoList);

		     // Delete old equipment details
		     List<BookingEquipmentVO> oldEquipmentList = bookingEquipmentRepo.findByBookingVO(bookingVO);
		     bookingEquipmentRepo.deleteAll(oldEquipmentList);

		     // Delete old commercial details
		     List<BookingCommercialVO> oldCommercialList = bookingCommercialRepo.findByBookingVO(bookingVO);
		     bookingCommercialRepo.deleteAll(oldCommercialList);

//		     // Delete old instruction details
//		     List<BookingInstructionVO> oldInstructionList = bookingInstructionRepo.findByBookingVO(bookingVO);
//		     bookingInstructionRepo.deleteAll(oldInstructionList);
		    

		        // 7. Delete old instruction files and database records
		        List<BookingInstructionVO> oldInstructionList =
		                bookingInstructionRepo.findByBookingVO(bookingVO);

		        if (oldInstructionList != null && !oldInstructionList.isEmpty()) {

		            for (BookingInstructionVO old : oldInstructionList) {
		            	if (old.getFileName() != null
		            	        && !old.getFileName().trim().isEmpty()) {

		                    Path oldFilePath = Paths.get(
		                            uploadPath,
		                            bookingVO.getId().toString(),
		                            old.getFileName()
		                    );

		                    try {
		                        System.out.println("Old file path: "
		                                + oldFilePath.toAbsolutePath());

		                        boolean deleted =
		                                Files.deleteIfExists(oldFilePath);

		                        System.out.println("Old file deleted: " + deleted);

		                    } catch (IOException e) {
		                        throw new RuntimeException(
		                                "Failed to delete old instruction file: "
		                                        + old.getFileName(), e);
		                    }
		                }
		            }

		            bookingInstructionRepo.deleteAll(oldInstructionList);
		            bookingInstructionRepo.flush();
		        }

		     
	        message = "Booking Updated Successfully";
	    } else {
	        bookingVO = new BookingVO();
	        String screenCode = "BKG";

	        String docId = bookingRepo.getBookingDocId(dto.getOrgId(), screenCode);
	        if (!StringUtils.hasText(docId)) {
	            throw new ApplicationException("Booking Document ID Generation Failed");
	        }
	        bookingVO.setDocId(docId);

	        DocumentTypeMappingDetailsVO mapping = documentTypeMappingDetailsRepo
	                .findByOrgIdAndScreenCode(dto.getOrgId(),  screenCode);
	        if (mapping == null) {
	            throw new ApplicationException("Document Type Mapping Details Not Found");
	        }
	        mapping.setLastNo(mapping.getLastNo() + 1);
	        documentTypeMappingDetailsRepo.save(mapping);

	   
	        bookingVO.setCreatedBy(dto.getCreatedBy());
	        bookingVO.setUpdatedBy(dto.getCreatedBy());
	        message = "Booking Created Successfully";
	    }

	    setBookingValues(dto, bookingVO);
	 // 1. Save booking first to ensure it has an ID
	    bookingVO = bookingRepo.saveAndFlush(bookingVO);

	   
	    // 4. Upload new files
	    saveInstructionFiles(files, bookingVO);

	    // 5. Save booking
	    bookingVO = bookingRepo.saveAndFlush(bookingVO);

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", message);
	    response.put("bookingVO", buildBookingResponse(bookingVO));
	    return response;
	}
	
	
	private void setBookingValues(BookingDTO dto, BookingVO vo) throws ApplicationException {

	    // ---------- validation ----------
	    if (dto.getOrgId() == null || dto.getOrgId() <= 0) {
	        throw new ApplicationException("Valid Organisation ID Is Required");
	    }
	    if (!StringUtils.hasText(dto.getCustomer())) {
	        throw new ApplicationException("Customer Is Required");
	    }
	    if (!StringUtils.hasText(dto.getBookingType())) {
	        throw new ApplicationException("Booking Type Is Required");
	    }
	    if (!StringUtils.hasText(dto.getTransportMode())) {
	        throw new ApplicationException("Transport Mode Is Required");
	    }
	    if (!StringUtils.hasText(dto.getServiceType())) {
	        throw new ApplicationException("Service Type Is Required");
	    }

	    // ---------- header ----------
	    vo.setBookingType(dto.getBookingType());
	    vo.setTransportMode(dto.getTransportMode());
	    vo.setServiceType(dto.getServiceType());
	    vo.setCustomer(dto.getCustomer());
	    vo.setCustomerReference(dto.getCustomerReference());
	    vo.setSalesperson(dto.getSalesperson());
	    vo.setOperationsOwner(dto.getOperationsOwner());
	    vo.setOrgId(dto.getOrgId());
	    vo.setCancelRemarks(dto.getCancelRemarks());
	    vo.setRemarks(dto.getRemarks());

	    if (StringUtils.hasText(dto.getBookingStatus())) {
	        vo.setBookingStatus(dto.getBookingStatus());
	    } else if (!StringUtils.hasText(vo.getBookingStatus())) {
	        vo.setBookingStatus("Draft");
	    }

	    // ---------- master ----------
	    if (dto.getBranch() != null && dto.getBranch() > 0) {
	        BranchVO branch = branchRepo.findById(dto.getBranch())
	                .orElseThrow(() -> new ApplicationException("Branch Not Found"));
	        vo.setBranch(branch);
	    } else {
	        throw new ApplicationException("Branch Is Required");
	    }



	   
	    // ---------- routing ----------
	    List<BookingRoutingVO> routingList = new ArrayList<>();
	    if (dto.getRoutingDetails() != null) {
	        for (BookingRoutingDTO c : dto.getRoutingDetails()) {
	            BookingRoutingVO child = new BookingRoutingVO();
	            child.setPlaceOfReceipt(c.getPlaceOfReceipt());
	            child.setOrigin(c.getOrigin());
	            child.setDestination(c.getDestination());
	            child.setPlaceOfDelivery(c.getPlaceOfDelivery());
	            child.setRequestedETD(c.getRequestedETD());
	            child.setRequestedETA(c.getRequestedETA());
	            child.setCarrierPreference(c.getCarrierPreference());
	            child.setRoutingPreference(c.getRoutingPreference());
	            child.setIncoterm(c.getIncoterm());
	            child.setBookingVO(vo);
	            routingList.add(child);
	        }
	    }
	    vo.setRoutingDetails(routingList);

	    // ---------- party ----------
	    List<BookingPartyVO> partyList = new ArrayList<>();
	    if (dto.getPartyDetails() != null) {
	        for (BookingPartyDTO c : dto.getPartyDetails()) {
	            BookingPartyVO child = new BookingPartyVO();
	            child.setShipper(c.getShipper());
	            child.setConsignee(c.getConsignee());
	            child.setNotifyParty(c.getNotifyParty());
	            child.setBillingCustomer(c.getBillingCustomer());
	            child.setPickupAddress(c.getPickupAddress());
	            child.setDeliveryAddress(c.getDeliveryAddress());
	            child.setContactPerson(c.getContactPerson());
	            child.setEmail(c.getEmail());
	            child.setPhone(c.getPhone());
	            child.setBookingVO(vo);
	            partyList.add(child);
	        }
	    }
	    vo.setPartyDetails(partyList);

	    // ---------- cargo ----------
	    List<BookingCargoVO> cargoList = new ArrayList<>();
	    if (dto.getCargoDetails() != null) {
	        for (BookingCargoDTO c : dto.getCargoDetails()) {
	            BookingCargoVO child = new BookingCargoVO();
	            child.setCommodityDescription(c.getCommodityDescription());
	            child.setPackageType(c.getPackageType());
	            child.setPackageCount(c.getPackageCount());
	            child.setGrossWeight(c.getGrossWeight());
	            child.setNetWeight(c.getNetWeight());
	            child.setWeightUnit(c.getWeightUnit());
	            child.setVolume(c.getVolume());
	            child.setVolumeUnit(c.getVolumeUnit());
	            child.setLength(c.getLength());
	            child.setWidth(c.getWidth());
	            child.setHeight(c.getHeight());
	            child.setDimensionUnit(c.getDimensionUnit());
	            child.setMarksAndNumbers(c.getMarksAndNumbers());
	            child.setHazardousClass(c.getHazardousClass());
	            child.setUnNumber(c.getUnNumber());
	            child.setRemarks(c.getRemarks());
	            child.setBookingVO(vo);
	            cargoList.add(child);
	        }
	    }
	    vo.setCargoDetails(cargoList);

	    // ---------- equipment ----------
	    List<BookingEquipmentVO> equipmentList = new ArrayList<>();
	    if (dto.getEquipmentDetails() != null) {
	        for (BookingEquipmentDTO c : dto.getEquipmentDetails()) {
	            BookingEquipmentVO child = new BookingEquipmentVO();
	            child.setEquipmentType(c.getEquipmentType());
	            child.setEquipmentSize(c.getEquipmentSize());
	            child.setQuantity(c.getQuantity());
	            child.setContainerType(c.getContainerType());
	            child.setContainerNumber(c.getContainerNumber());
	            child.setIsoCode(c.getIsoCode());
	            child.setTemperatureRequired(c.isTemperatureRequired());
	            child.setTemperatureMin(c.getTemperatureMin());
	            child.setTemperatureMax(c.getTemperatureMax());
	            child.setVentilationRequired(c.isVentilationRequired());
	            child.setVentilationValue(c.getVentilationValue());
	            child.setHumidityRequired(c.isHumidityRequired());
	            child.setHumidityValue(c.getHumidityValue());
	            child.setSpecialEquipment(c.isSpecialEquipment());
	            child.setRemarks(c.getRemarks());
	            child.setBookingVO(vo);
	            equipmentList.add(child);
	        }
	    }
	    vo.setEquipmentDetails(equipmentList);

	    // ---------- commercial ----------
	    List<BookingCommercialVO> commercialList = new ArrayList<>();
	    if (dto.getCommercialDetails() != null) {
	        for (BookingCommercialDTO c : dto.getCommercialDetails()) {
	            BookingCommercialVO child = new BookingCommercialVO();
	            child.setFreightPaymentTerms(c.getFreightPaymentTerms());
	            child.setQuotationReference(c.getQuotationReference());
	            child.setSellRate(c.getSellRate());
	            child.setSellCurrency(c.getSellCurrency());
	            child.setBuyRate(c.getBuyRate());
	            child.setBuyCurrency(c.getBuyCurrency());
	            child.setPaymentTerms(c.getPaymentTerms());
	            child.setCreditCheckStatus(c.getCreditCheckStatus());
	            child.setCommercialRemarks(c.getCommercialRemarks());
	            child.setBookingVO(vo);
	            commercialList.add(child);
	        }
	    }
	    vo.setCommercialDetails(commercialList);

	    // ---------- instruction (with file details carried over) ----------
	    List<BookingInstructionVO> instructionList = new ArrayList<>();
	    if (dto.getInstructionDetails() != null) {
	        for (BookingInstructionDTO c : dto.getInstructionDetails()) {
	            BookingInstructionVO child = new BookingInstructionVO();
	            child.setDocumentType(c.getDocumentType());
	            child.setPickupInstructions(c.getPickupInstructions());
	            child.setDeliveryInstructions(c.getDeliveryInstructions());
	            child.setInternalRemarks(c.getInternalRemarks());
	            child.setBookingVO(vo);
	            instructionList.add(child);
	        }
	    }
	    vo.setInstructionDetails(instructionList);
	}
	
	
	@Value("${booking.upload.path}")
	private String uploadPath;

	private void saveInstructionFiles(MultipartFile[] files, BookingVO bookingVO)
	        throws ApplicationException {

	    if (files == null || files.length == 0) {
	        return;
	    }

	    try {
	        Path bookingFolder = Paths.get(
	                uploadPath, bookingVO.getId().toString()
	        );

	        Files.createDirectories(bookingFolder);

	        List<BookingInstructionVO> instructionList =
	                bookingVO.getInstructionDetails();

	        if (instructionList == null || instructionList.isEmpty()) {
	            return;
	        }

	        int fileIndex = 0;

	        for (BookingInstructionVO row : instructionList) {

	            if (fileIndex >= files.length) {
	                break;
	            }

	            MultipartFile file = files[fileIndex++];

	            // Keep the existing document if no new file is uploaded.
	            if (file == null || file.isEmpty()) {
	                continue;
	            }

	            String originalName = file.getOriginalFilename();

	            if (originalName == null || originalName.trim().isEmpty()) {
	                originalName = "file";
	            }

	            originalName = Paths.get(originalName)
	                    .getFileName().toString()
	                    .replaceAll("\\s+", "_");

	            String extension = "";
	            String baseName = originalName;

	            if (originalName.contains(".")) {
	                int dotIndex = originalName.lastIndexOf(".");
	                extension = originalName.substring(dotIndex);
	                baseName = originalName.substring(0, dotIndex);
	            }

	            String fileName = baseName + "_"
	                    + bookingVO.getId() + extension;

	            Path filePath = bookingFolder.resolve(fileName);

	            try (InputStream inputStream = file.getInputStream()) {
	                Files.copy(
	                        inputStream,
	                        filePath,
	                        StandardCopyOption.REPLACE_EXISTING
	                );
	            }

	            String baseUrl = ServletUriComponentsBuilder
	                    .fromCurrentContextPath()
	                    .path("/api/sales/viewBookingFile/")
	                    .toUriString();

	            String relativePath = bookingVO.getId()
	                    + "/" + fileName;

	            String publicUrl = baseUrl + relativePath;

	            row.setDocument(publicUrl);
	            row.setFileName(fileName);
	            row.setOriginalName(originalName);
	            row.setFileSize(file.getSize());
	            row.setContentType(file.getContentType());
	            row.setUploadOn(LocalDateTime.now());
	        }

	        bookingRepo.saveAndFlush(bookingVO);

	    } catch (IOException e) {
	        throw new ApplicationException(
	                "File Upload Failed: " + e.getMessage()
	        );
	    }
	}

	
	private void createDirectory(Path path) throws IOException {
	    if (!Files.exists(path)) {
	        Files.createDirectories(path);
	    }
	}

	@Override
	public ResponseEntity<byte[]> viewBookingFile(HttpServletRequest request) throws IOException {
	    return serveFile(request, "/api/sales/viewBookingFile/", uploadPath);
	}

	private ResponseEntity<byte[]> serveFile(HttpServletRequest request, String apiPrefix, String uploadBasePath)
	        throws IOException {
	    String uri = request.getRequestURI();
	    String relativePath = uri.replace(apiPrefix, "");
	    relativePath = URLDecoder.decode(relativePath, "UTF-8");
	    
	    if (relativePath.startsWith("uploads/")) {
	        relativePath = relativePath.substring("uploads/".length());
	    }

	    Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();
	    Path filePath = baseDir.resolve(relativePath).normalize();

	    if (!filePath.startsWith(baseDir)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
	    if (!Files.exists(filePath)) {
	        return ResponseEntity.notFound().build();
	    }

	    String contentType = Files.probeContentType(filePath);
	    if (contentType == null) {
	        contentType = "application/octet-stream";
	    }

	    byte[] data = Files.readAllBytes(filePath);

	    return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
	            .header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
	}
	
	
	private BookingResponseDTO buildBookingResponse(BookingVO vo) {

	    BookingResponseDTO dto = new BookingResponseDTO();

	    // ---- header ----
	    dto.setId(vo.getId());
	    dto.setDocId(vo.getDocId());
	    dto.setDocDate(vo.getDocDate());
	    dto.setBookingType(vo.getBookingType());
	    dto.setTransportMode(vo.getTransportMode());
	    dto.setServiceType(vo.getServiceType());
	    dto.setCustomer(vo.getCustomer());
	    dto.setCustomerReference(vo.getCustomerReference());
	    dto.setSalesperson(vo.getSalesperson());
	    dto.setOperationsOwner(vo.getOperationsOwner());
	    dto.setBookingStatus(vo.getBookingStatus());
	    dto.setOrgId(vo.getOrgId());
	    dto.setActive(vo.getActive());
	    dto.setCancel(vo.getCancel());
	    dto.setCancelRemarks(vo.getCancelRemarks());
	    dto.setRemarks(vo.getRemarks());
	    dto.setCreatedBy(vo.getCreatedBy());
	    dto.setUpdatedBy(vo.getUpdatedBy());

	    if (vo.getBranch() != null) {
	        BranchResponseDTO branchDTO = new BranchResponseDTO();
	        branchDTO.setId(vo.getBranch().getId());
	        branchDTO.setBranch(vo.getBranch().getBranch());
	        branchDTO.setBranchCode(vo.getBranch().getBranchCode());
	        dto.setBranch(branchDTO);
	    }

	    // ---- routing ----
	    List<BookingRoutingResponseDTO> routingList = new ArrayList<>();
	    if (vo.getRoutingDetails() != null) {
	        for (BookingRoutingVO child : vo.getRoutingDetails()) {
	            BookingRoutingResponseDTO r = new BookingRoutingResponseDTO();
	            r.setId(child.getId());
	            r.setPlaceOfReceipt(child.getPlaceOfReceipt());
	            r.setOrigin(child.getOrigin());
	            r.setDestination(child.getDestination());
	            r.setPlaceOfDelivery(child.getPlaceOfDelivery());
	            r.setRequestedETD(child.getRequestedETD());
	            r.setRequestedETA(child.getRequestedETA());
	            r.setCarrierPreference(child.getCarrierPreference());
	            r.setRoutingPreference(child.getRoutingPreference());
	            r.setIncoterm(child.getIncoterm());
	            routingList.add(r);
	        }
	    }
	    dto.setRoutingDetails(routingList);

	    // ---- party ----
	    List<BookingPartyResponseDTO> partyList = new ArrayList<>();
	    if (vo.getPartyDetails() != null) {
	        for (BookingPartyVO child : vo.getPartyDetails()) {
	            BookingPartyResponseDTO p = new BookingPartyResponseDTO();
	            p.setId(child.getId());
	            p.setShipper(child.getShipper());
	            p.setConsignee(child.getConsignee());
	            p.setNotifyParty(child.getNotifyParty());
	            p.setBillingCustomer(child.getBillingCustomer());
	            p.setPickupAddress(child.getPickupAddress());
	            p.setDeliveryAddress(child.getDeliveryAddress());
	            p.setContactPerson(child.getContactPerson());
	            p.setEmail(child.getEmail());
	            p.setPhone(child.getPhone());
	            partyList.add(p);
	        }
	    }
	    dto.setPartyDetails(partyList);

	    // ---- cargo ----
	    List<BookingCargoResponseDTO> cargoList = new ArrayList<>();
	    if (vo.getCargoDetails() != null) {
	        for (BookingCargoVO child : vo.getCargoDetails()) {
	            BookingCargoResponseDTO g = new BookingCargoResponseDTO();
	            g.setId(child.getId());
	            g.setCommodityDescription(child.getCommodityDescription());
	            g.setPackageType(child.getPackageType());
	            g.setPackageCount(child.getPackageCount());
	            g.setGrossWeight(child.getGrossWeight());
	            g.setNetWeight(child.getNetWeight());
	            g.setWeightUnit(child.getWeightUnit());
	            g.setVolume(child.getVolume());
	            g.setVolumeUnit(child.getVolumeUnit());
	            g.setLength(child.getLength());
	            g.setWidth(child.getWidth());
	            g.setHeight(child.getHeight());
	            g.setDimensionUnit(child.getDimensionUnit());
	            g.setMarksAndNumbers(child.getMarksAndNumbers());
	            g.setHazardous(child.getHazardous());
	            g.setHazardousClass(child.getHazardousClass());
	            g.setUnNumber(child.getUnNumber());
	            g.setRemarks(child.getRemarks());
	            cargoList.add(g);
	        }
	    }
	    dto.setCargoDetails(cargoList);

	    // ---- equipment ----
	    List<BookingEquipmentResponseDTO> equipmentList = new ArrayList<>();
	    if (vo.getEquipmentDetails() != null) {
	        for (BookingEquipmentVO child : vo.getEquipmentDetails()) {
	            BookingEquipmentResponseDTO e = new BookingEquipmentResponseDTO();
	            e.setId(child.getId());
	            e.setEquipmentType(child.getEquipmentType());
	            e.setEquipmentSize(child.getEquipmentSize());
	            e.setQuantity(child.getQuantity());
	            e.setContainerType(child.getContainerType());
	            e.setContainerNumber(child.getContainerNumber());
	            e.setIsoCode(child.getIsoCode());
	            e.setTemperatureRequired(child.isTemperatureRequired());
	            e.setTemperatureMin(child.getTemperatureMin());
	            e.setTemperatureMax(child.getTemperatureMax());
	            e.setVentilationRequired(child.isVentilationRequired());
	            e.setVentilationValue(child.getVentilationValue());
	            e.setHumidityRequired(child.isHumidityRequired());
	            e.setHumidityValue(child.getHumidityValue());
	            e.setSpecialEquipment(child.isSpecialEquipment());
	            e.setRemarks(child.getRemarks());
	            equipmentList.add(e);
	        }
	    }
	    dto.setEquipmentDetails(equipmentList);

	    // ---- commercial ----
	    List<BookingCommercialResponseDTO> commercialList = new ArrayList<>();
	    if (vo.getCommercialDetails() != null) {
	        for (BookingCommercialVO child : vo.getCommercialDetails()) {
	            BookingCommercialResponseDTO m = new BookingCommercialResponseDTO();
	            m.setId(child.getId());
	            m.setFreightPaymentTerms(child.getFreightPaymentTerms());
	            m.setQuotationReference(child.getQuotationReference());
	            m.setSellRate(child.getSellRate());
	            m.setSellCurrency(child.getSellCurrency());
	            m.setBuyRate(child.getBuyRate());
	            m.setBuyCurrency(child.getBuyCurrency());
	            m.setPaymentTerms(child.getPaymentTerms());
	            m.setCreditCheckStatus(child.getCreditCheckStatus());
	            m.setCommercialRemarks(child.getCommercialRemarks());
	            commercialList.add(m);
	        }
	    }
	    dto.setCommercialDetails(commercialList);

	    // ---- instruction (with file details) ----
	    List<BookingInstructionResponseDTO> instructionList = new ArrayList<>();
	    if (vo.getInstructionDetails() != null) {
	        for (BookingInstructionVO child : vo.getInstructionDetails()) {
	            BookingInstructionResponseDTO n = new BookingInstructionResponseDTO();
	            n.setId(child.getId());
	            n.setDocumentType(child.getDocumentType());
	            n.setDocument(child.getDocument());
	            n.setFileName(child.getFileName());
	            n.setOriginalName(child.getOriginalName());
	            n.setFileSize(child.getFileSize());
	            n.setContentType(child.getContentType());
	            n.setUploadOn(child.getUploadOn());
	            n.setPickupInstructions(child.getPickupInstructions());
	            n.setDeliveryInstructions(child.getDeliveryInstructions());
	            n.setInternalRemarks(child.getInternalRemarks());
	            instructionList.add(n);
	        }
	    }
	    dto.setInstructionDetails(instructionList);

	    return dto;
	}
	
	
	

    // =====================================================
    // HEADER + CHILD MAPPING
    // =====================================================

//    private void getBookingVOFromDTO(
//            BookingDTO dto, BookingVO bookingVO)
//            throws ApplicationException {
//
//        // =====================================================
//        // VALIDATION
//        // =====================================================
//
//        if (dto.getOrgId() == null || dto.getOrgId() <= 0) {
//            throw new ApplicationException(
//                    "Valid Organisation ID Is Required");
//        }
//
//        if (!StringUtils.hasText(dto.getCustomer())) {
//            throw new ApplicationException("Customer Is Required");
//        }
//
//        if (!StringUtils.hasText(dto.getBookingType())) {
//            throw new ApplicationException("Booking Type Is Required");
//        }
//
//        if (!StringUtils.hasText(dto.getTransportMode())) {
//            throw new ApplicationException("Transport Mode Is Required");
//        }
//
//        if (!StringUtils.hasText(dto.getServiceType())) {
//            throw new ApplicationException("Service Type Is Required");
//        }
//
//        if (dto.getBranch() == null) {
//            throw new ApplicationException("Branch Is Required");
//        }
//
//        // =====================================================
//        // MASTER MAPPING
//        // =====================================================
//
//        BranchVO branch = branchRepo.findById(dto.getBranch())
//                .orElseThrow(() ->
//                        new ApplicationException("Branch Not Found"));
//
//        // =====================================================
//        // HEADER FIELDS
//        // =====================================================
//
//
//        bookingVO.setBookingType(dto.getBookingType());
//        bookingVO.setTransportMode(dto.getTransportMode());
//        bookingVO.setServiceType(dto.getServiceType());
//
//        bookingVO.setCustomer(dto.getCustomer());
//        bookingVO.setCustomerReference(dto.getCustomerReference());
//        bookingVO.setSalesperson(dto.getSalesperson());
//        bookingVO.setOperationsOwner(dto.getOperationsOwner());
//
//        if (StringUtils.hasText(dto.getBookingStatus())) {
//            bookingVO.setBookingStatus(dto.getBookingStatus());
//        } else if (!StringUtils.hasText(bookingVO.getBookingStatus())) {
//            bookingVO.setBookingStatus("Draft");
//        }
//
//        bookingVO.setOrgId(dto.getOrgId());
//        bookingVO.setBranch(branch);
//        bookingVO.setCancelRemarks(dto.getCancelRemarks());
//        bookingVO.setRemarks(dto.getRemarks());
//
//        // =====================================================
//        // ROUTING DETAILS
//        // =====================================================
//
//        if (dto.getRoutingDetails() != null) {
//            for (BookingRoutingDTO childDTO : dto.getRoutingDetails()) {
//
//                BookingRoutingVO childVO = new BookingRoutingVO();
//
//                childVO.setPlaceOfReceipt(childDTO.getPlaceOfReceipt());
//                childVO.setOrigin(childDTO.getOrigin());
//                childVO.setDestination(childDTO.getDestination());
//                childVO.setPlaceOfDelivery(childDTO.getPlaceOfDelivery());
//                childVO.setRequestedETD(childDTO.getRequestedETD());
//                childVO.setRequestedETA(childDTO.getRequestedETA());
//                childVO.setCarrierPreference(childDTO.getCarrierPreference());
//                childVO.setRoutingPreference(childDTO.getRoutingPreference());
//                childVO.setIncoterm(childDTO.getIncoterm());
//                childVO.setBookingVO(bookingVO);
//
//                bookingVO.getRoutingDetails().add(childVO);
//            }
//        }
//
//        // =====================================================
//        // PARTY DETAILS
//        // =====================================================
//
//        if (dto.getPartyDetails() != null) {
//            for (BookingPartyDTO childDTO : dto.getPartyDetails()) {
//
//                BookingPartyVO childVO = new BookingPartyVO();
//
//                childVO.setShipper(childDTO.getShipper());
//                childVO.setConsignee(childDTO.getConsignee());
//                childVO.setNotifyParty(childDTO.getNotifyParty());
//                childVO.setBillingCustomer(childDTO.getBillingCustomer());
//                childVO.setPickupAddress(childDTO.getPickupAddress());
//                childVO.setDeliveryAddress(childDTO.getDeliveryAddress());
//                childVO.setContactPerson(childDTO.getContactPerson());
//                childVO.setEmail(childDTO.getEmail());
//                childVO.setPhone(childDTO.getPhone());
//                childVO.setBookingVO(bookingVO);
//
//                bookingVO.getPartyDetails().add(childVO);
//            }
//        }
//
//        // =====================================================
//        // CARGO DETAILS
//        // =====================================================
//
//        if (dto.getCargoDetails() != null) {
//            for (BookingCargoDTO childDTO : dto.getCargoDetails()) {
//
//                BookingCargoVO childVO = new BookingCargoVO();
//
//                childVO.setCommodityDescription(
//                        childDTO.getCommodityDescription());
//                childVO.setPackageType(childDTO.getPackageType());
//                childVO.setPackageCount(childDTO.getPackageCount());
//                childVO.setGrossWeight(childDTO.getGrossWeight());
//                childVO.setNetWeight(childDTO.getNetWeight());
//                childVO.setWeightUnit(childDTO.getWeightUnit());
//                childVO.setVolume(childDTO.getVolume());
//                childVO.setVolumeUnit(childDTO.getVolumeUnit());
//                childVO.setLength(childDTO.getLength());
//                childVO.setWidth(childDTO.getWidth());
//                childVO.setHeight(childDTO.getHeight());
//                childVO.setDimensionUnit(childDTO.getDimensionUnit());
//                childVO.setMarksAndNumbers(childDTO.getMarksAndNumbers());
//                childVO.setHazardousClass(childDTO.getHazardousClass());
//                childVO.setUnNumber(childDTO.getUnNumber());
//                childVO.setRemarks(childDTO.getRemarks());
//                childVO.setBookingVO(bookingVO);
//
//                bookingVO.getCargoDetails().add(childVO);
//            }
//        }
//
//        // =====================================================
//        // EQUIPMENT DETAILS
//        // =====================================================
//
//        if (dto.getEquipmentDetails() != null) {
//            for (BookingEquipmentDTO childDTO : dto.getEquipmentDetails()) {
//
//                BookingEquipmentVO childVO = new BookingEquipmentVO();
//
//                childVO.setEquipmentType(childDTO.getEquipmentType());
//                childVO.setEquipmentSize(childDTO.getEquipmentSize());
//                childVO.setQuantity(childDTO.getQuantity());
//                childVO.setContainerType(childDTO.getContainerType());
//                childVO.setContainerNumber(childDTO.getContainerNumber());
//                childVO.setIsoCode(childDTO.getIsoCode());
//                childVO.setTemperatureRequired(
//                        childDTO.isTemperatureRequired());
//                childVO.setTemperatureMin(childDTO.getTemperatureMin());
//                childVO.setTemperatureMax(childDTO.getTemperatureMax());
//                childVO.setVentilationRequired(
//                        childDTO.isVentilationRequired());
//                childVO.setVentilationValue(childDTO.getVentilationValue());
//                childVO.setHumidityRequired(childDTO.isHumidityRequired());
//                childVO.setHumidityValue(childDTO.getHumidityValue());
//                childVO.setSpecialEquipment(childDTO.isSpecialEquipment());
//                childVO.setRemarks(childDTO.getRemarks());
//                childVO.setBookingVO(bookingVO);
//
//                bookingVO.getEquipmentDetails().add(childVO);
//            }
//        }
//
//        // =====================================================
//        // COMMERCIAL DETAILS
//        // =====================================================
//
//        if (dto.getCommercialDetails() != null) {
//            for (BookingCommercialDTO childDTO
//                    : dto.getCommercialDetails()) {
//
//                BookingCommercialVO childVO = new BookingCommercialVO();
//
//                childVO.setFreightPaymentTerms(
//                        childDTO.getFreightPaymentTerms());
//                childVO.setQuotationReference(
//                        childDTO.getQuotationReference());
//                childVO.setSellRate(childDTO.getSellRate());
//                childVO.setSellCurrency(childDTO.getSellCurrency());
//                childVO.setBuyRate(childDTO.getBuyRate());
//                childVO.setBuyCurrency(childDTO.getBuyCurrency());
//                childVO.setPaymentTerms(childDTO.getPaymentTerms());
//                childVO.setCreditCheckStatus(childDTO.getCreditCheckStatus());
//                childVO.setCommercialRemarks(childDTO.getCommercialRemarks());
//                childVO.setBookingVO(bookingVO);
//
//                bookingVO.getCommercialDetails().add(childVO);
//            }
//        }
//
//        // =====================================================
//        // INSTRUCTION DETAILS
//        // =====================================================
//
//        if (dto.getInstructionDetails() != null) {
//            for (BookingInstructionDTO childDTO
//                    : dto.getInstructionDetails()) {
//
//                BookingInstructionVO childVO = new BookingInstructionVO();
//
//                childVO.setDocumentType(childDTO.getDocumentType());
//                childVO.setPickupInstructions(
//                        childDTO.getPickupInstructions());
//                childVO.setDeliveryInstructions(
//                        childDTO.getDeliveryInstructions());
//                childVO.setInternalRemarks(childDTO.getInternalRemarks());
//                childVO.setBookingVO(bookingVO);
//
//                bookingVO.getInstructionDetails().add(childVO);
//            }
//        }
//    }
//
//    // =====================================================
//    // CONVERT ENTITY TO RESPONSE DTO
//    // =====================================================
//
//    private BookingResponseDTO convertToResponse(BookingVO vo) {
//
//        BookingResponseDTO dto = new BookingResponseDTO();
//
//        // HEADER
//
//        dto.setId(vo.getId());
//        dto.setDocId(vo.getDocId());
//        dto.setDocDate(vo.getDocDate());
//        dto.setBookingType(vo.getBookingType());
//        dto.setTransportMode(vo.getTransportMode());
//        dto.setServiceType(vo.getServiceType());
//        dto.setCustomer(vo.getCustomer());
//        dto.setCustomerReference(vo.getCustomerReference());
//        dto.setSalesperson(vo.getSalesperson());
//        dto.setOperationsOwner(vo.getOperationsOwner());
//        dto.setBookingStatus(vo.getBookingStatus());
//        dto.setOrgId(vo.getOrgId());
//        dto.setActive(vo.getActive());
//        dto.setCancel(vo.getCancel());
//        dto.setCancelRemarks(vo.getCancelRemarks());
//        dto.setRemarks(vo.getRemarks());
//        dto.setCreatedBy(vo.getCreatedBy());
//        dto.setUpdatedBy(vo.getUpdatedBy());
//
//        if (vo.getBranch() != null) {
//            BranchResponseDTO branchDTO = new BranchResponseDTO();
//            branchDTO.setId(vo.getBranch().getId());
//            branchDTO.setBranch(vo.getBranch().getBranch());
//            branchDTO.setBranchCode(vo.getBranch().getBranchCode());
//            dto.setBranch(branchDTO);
//        }
//
//        // ROUTING DETAILS
//
//        if (vo.getRoutingDetails() != null) {
//            for (BookingRoutingVO child : vo.getRoutingDetails()) {
//                BookingRoutingResponseDTO childDTO =
//                        new BookingRoutingResponseDTO();
//
//                childDTO.setId(child.getId());
//                childDTO.setPlaceOfReceipt(child.getPlaceOfReceipt());
//                childDTO.setOrigin(child.getOrigin());
//                childDTO.setDestination(child.getDestination());
//                childDTO.setPlaceOfDelivery(child.getPlaceOfDelivery());
//                childDTO.setRequestedETD(child.getRequestedETD());
//                childDTO.setRequestedETA(child.getRequestedETA());
//                childDTO.setCarrierPreference(child.getCarrierPreference());
//                childDTO.setRoutingPreference(child.getRoutingPreference());
//                childDTO.setIncoterm(child.getIncoterm());
//
//                dto.getRoutingDetails().add(childDTO);
//            }
//        }
//
//        // PARTY DETAILS
//
//        if (vo.getPartyDetails() != null) {
//            for (BookingPartyVO child : vo.getPartyDetails()) {
//                BookingPartyResponseDTO childDTO =
//                        new BookingPartyResponseDTO();
//
//                childDTO.setId(child.getId());
//                childDTO.setShipper(child.getShipper());
//                childDTO.setConsignee(child.getConsignee());
//                childDTO.setNotifyParty(child.getNotifyParty());
//                childDTO.setBillingCustomer(child.getBillingCustomer());
//                childDTO.setPickupAddress(child.getPickupAddress());
//                childDTO.setDeliveryAddress(child.getDeliveryAddress());
//                childDTO.setContactPerson(child.getContactPerson());
//                childDTO.setEmail(child.getEmail());
//                childDTO.setPhone(child.getPhone());
//
//                dto.getPartyDetails().add(childDTO);
//            }
//        }
//
//        // CARGO DETAILS
//
//        if (vo.getCargoDetails() != null) {
//            for (BookingCargoVO child : vo.getCargoDetails()) {
//                BookingCargoResponseDTO childDTO =
//                        new BookingCargoResponseDTO();
//
//                childDTO.setId(child.getId());
//                childDTO.setCommodityDescription(
//                        child.getCommodityDescription());
//                childDTO.setPackageType(child.getPackageType());
//                childDTO.setPackageCount(child.getPackageCount());
//                childDTO.setGrossWeight(child.getGrossWeight());
//                childDTO.setNetWeight(child.getNetWeight());
//                childDTO.setWeightUnit(child.getWeightUnit());
//                childDTO.setVolume(child.getVolume());
//                childDTO.setVolumeUnit(child.getVolumeUnit());
//                childDTO.setLength(child.getLength());
//                childDTO.setWidth(child.getWidth());
//                childDTO.setHeight(child.getHeight());
//                childDTO.setDimensionUnit(child.getDimensionUnit());
//                childDTO.setMarksAndNumbers(child.getMarksAndNumbers());
//                childDTO.setHazardous(child.getHazardous());
//                childDTO.setHazardousClass(child.getHazardousClass());
//                childDTO.setUnNumber(child.getUnNumber());
//                childDTO.setRemarks(child.getRemarks());
//
//                dto.getCargoDetails().add(childDTO);
//            }
//        }
//
//        // EQUIPMENT DETAILS
//
//        if (vo.getEquipmentDetails() != null) {
//            for (BookingEquipmentVO child : vo.getEquipmentDetails()) {
//                BookingEquipmentResponseDTO childDTO =
//                        new BookingEquipmentResponseDTO();
//
//                childDTO.setId(child.getId());
//                childDTO.setEquipmentType(child.getEquipmentType());
//                childDTO.setEquipmentSize(child.getEquipmentSize());
//                childDTO.setQuantity(child.getQuantity());
//                childDTO.setContainerType(child.getContainerType());
//                childDTO.setContainerNumber(child.getContainerNumber());
//                childDTO.setIsoCode(child.getIsoCode());
//                childDTO.setTemperatureRequired(
//                        child.isTemperatureRequired());
//                childDTO.setTemperatureMin(child.getTemperatureMin());
//                childDTO.setTemperatureMax(child.getTemperatureMax());
//                childDTO.setVentilationRequired(
//                        child.isVentilationRequired());
//                childDTO.setVentilationValue(child.getVentilationValue());
//                childDTO.setHumidityRequired(child.isHumidityRequired());
//                childDTO.setHumidityValue(child.getHumidityValue());
//                childDTO.setSpecialEquipment(child.isSpecialEquipment());
//                childDTO.setRemarks(child.getRemarks());
//
//                dto.getEquipmentDetails().add(childDTO);
//            }
//        }
//
//        // COMMERCIAL DETAILS
//
//        if (vo.getCommercialDetails() != null) {
//            for (BookingCommercialVO child : vo.getCommercialDetails()) {
//                BookingCommercialResponseDTO childDTO =
//                        new BookingCommercialResponseDTO();
//
//                childDTO.setId(child.getId());
//                childDTO.setFreightPaymentTerms(
//                        child.getFreightPaymentTerms());
//                childDTO.setQuotationReference(
//                        child.getQuotationReference());
//                childDTO.setSellRate(child.getSellRate());
//                childDTO.setSellCurrency(child.getSellCurrency());
//                childDTO.setBuyRate(child.getBuyRate());
//                childDTO.setBuyCurrency(child.getBuyCurrency());
//                childDTO.setPaymentTerms(child.getPaymentTerms());
//                childDTO.setCreditCheckStatus(child.getCreditCheckStatus());
//                childDTO.setCommercialRemarks(child.getCommercialRemarks());
//
//                dto.getCommercialDetails().add(childDTO);
//            }
//        }
//
//        // INSTRUCTION DETAILS
//
//        if (vo.getInstructionDetails() != null) {
//            for (BookingInstructionVO child : vo.getInstructionDetails()) {
//                BookingInstructionResponseDTO childDTO =
//                        new BookingInstructionResponseDTO();
//
//                childDTO.setId(child.getId());
//                
//                childDTO.setDocumentType(
//                        child.getDocumentType());
//                childDTO.setDocument(
//                        child.getDocument());
//                childDTO.setPickupInstructions(
//                        child.getPickupInstructions());
//                childDTO.setDeliveryInstructions(
//                        child.getDeliveryInstructions());
//                childDTO.setInternalRemarks(child.getInternalRemarks());
//
//                dto.getInstructionDetails().add(childDTO);
//            }
//        }
//
//        return dto;
//    }
//    
    
    @Override
    public List<BookingResponseDTO> getBookingByOrgId(
            Long orgId, Long branch) throws ApplicationException {

        List<BookingVO> bookingList =
                bookingRepo.findByOrgIdAndBranch(orgId, branch);

        if (bookingList == null || bookingList.isEmpty()) {
            throw new ApplicationException("Booking Not Found");
        }

        List<BookingResponseDTO> responseList = new ArrayList<>();

        for (BookingVO bookingVO : bookingList) {
            responseList.add(buildBookingResponse(bookingVO));
        }

        return responseList;
    }


    @Override
    public String getBookingDocId(Long orgId) throws ApplicationException {

        String screenCode = "BKG";

        String result = bookingRepo.getBookingDocId(orgId, screenCode);

        if (!org.springframework.util.StringUtils.hasText(result)) {
            throw new ApplicationException("Booking Doc ID Generation Failed");
        }

        return result;
    }
    

    // =====================================================
    // GET BOOKING BY ID
    // =====================================================

    @Override
    @Transactional
    public BookingResponseDTO getBookingById(Long id)
            throws ApplicationException {

        BookingVO bookingVO = bookingRepo.findById(id)
                .orElseThrow(() ->
                        new ApplicationException("Booking Not Found"));

        return buildBookingResponse(bookingVO);
    }

    
}
