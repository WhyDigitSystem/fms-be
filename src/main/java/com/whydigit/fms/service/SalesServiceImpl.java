
package com.whydigit.fms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.whydigit.fms.ResponseDTO.EnquiryResponseDTO;
import com.whydigit.fms.dto.EnquiryDTO;
import com.whydigit.fms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.fms.entity.EnquiryVO;
import com.whydigit.fms.exception.ApplicationException;
import com.whydigit.fms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.fms.repo.EnquiryRepo;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    EnquiryRepo enquiryRepo;

    @Autowired
    DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

    @Override
    @Transactional
    public Map<String, Object> createUpdateEnquiry(EnquiryDTO dto)
            throws ApplicationException {

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
                    .orElseThrow(() ->
                            new ApplicationException("Enquiry Not Found"));

            enquiryVO.setUpdatedBy(dto.getCreatedBy());

            message = "Enquiry Updated Successfully";
        }

        // HEADER MAPPING
        getEnquiryVOFromDTO(dto, enquiryVO);

        // SAVE
        enquiryVO = enquiryRepo.saveAndFlush(enquiryVO);

        // RESPONSE
        EnquiryResponseDTO responseDTO =
                convertToResponse(enquiryVO);

        response.put("message", message);
        response.put("enquiryVO", responseDTO);

        return response;
    }

    // ================= VALIDATION =================

    private void validateEnquiry(EnquiryDTO dto)
            throws ApplicationException {

        if (dto == null) {
            throw new ApplicationException(
                    "Enquiry Details Are Required");
        }

        // Customer
        if (!StringUtils.hasText(dto.getCustomer())) {
            throw new ApplicationException(
                    "Customer Is Required");
        }

        // Organisation
        if (dto.getOrgId() == null || dto.getOrgId() <= 0) {
            throw new ApplicationException(
                    "Valid Organisation ID Is Required");
        }

        // Movement
        if (!StringUtils.hasText(dto.getMovement())) {
            throw new ApplicationException(
                    "Movement Is Required");
        }

        String movement = dto.getMovement().trim();

        if (!movement.equalsIgnoreCase("IMPORT")
                && !movement.equalsIgnoreCase("EXPORT")) {
            throw new ApplicationException(
                    "Movement Must Be IMPORT or EXPORT");
        }

        // Shipment Mode
        if (!StringUtils.hasText(dto.getShipmentMode())) {
            throw new ApplicationException(
                    "Shipment Mode Is Required");
        }

        String mode = dto.getShipmentMode().trim();

        if (!mode.equalsIgnoreCase("SEA")
                && !mode.equalsIgnoreCase("AIR")
                && !mode.equalsIgnoreCase("ROAD")) {
            throw new ApplicationException(
                    "Shipment Mode Must Be SEA, AIR or ROAD");
        }

        // Origin
        if (!StringUtils.hasText(dto.getFromLocation())) {
            throw new ApplicationException(
                    "Origin Location Is Required");
        }

        // Destination
        if (!StringUtils.hasText(dto.getToLocation())) {
            throw new ApplicationException(
                    "Destination Location Is Required");
        }

        // Cargo description
        if (!StringUtils.hasText(dto.getCargoDescription())) {
            throw new ApplicationException(
                    "Cargo Description Is Required");
        }

        // Validate numeric values when provided
        validatePositiveIfProvided(
                dto.getVolumeCbm(), "Cargo Volume (CBM)");

        validatePositiveIfProvided(
                dto.getGrossWeightKg(), "Gross Weight (KG)");

        validatePositiveIfProvided(
                dto.getLengthCm(), "Length (CM)");

        validatePositiveIfProvided(
                dto.getWidthCm(), "Width (CM)");

        validatePositiveIfProvided(
                dto.getHeightCm(), "Height (CM)");

        if (dto.getNoOfPackages() < 0) {
            throw new ApplicationException(
                    "Number Of Packages Cannot Be Negative");
        }

        // SEA VALIDATION
        if (mode.equalsIgnoreCase("SEA")) {

            if (!StringUtils.hasText(dto.getCargoType())) {
                throw new ApplicationException(
                        "Cargo Type Is Required For Sea Shipment");
            }

            if (dto.getVolumeCbm() == null
                    || dto.getVolumeCbm().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ApplicationException(
                        "Valid Cargo Volume (CBM) Is Required For Sea Shipment");
            }

            if (dto.getGrossWeightKg() == null
                    || dto.getGrossWeightKg()
                            .compareTo(BigDecimal.ZERO) <= 0) {
                throw new ApplicationException(
                        "Valid Gross Weight Is Required For Sea Shipment");
            }
        }

        // AIR VALIDATION
        else if (mode.equalsIgnoreCase("AIR")) {

            if (dto.getNoOfPackages() <= 0) {
                throw new ApplicationException(
                        "Number Of Packages Must Be Greater Than Zero For Air Shipment");
            }

            if (dto.getGrossWeightKg() == null
                    || dto.getGrossWeightKg()
                            .compareTo(BigDecimal.ZERO) <= 0) {
                throw new ApplicationException(
                        "Valid Gross Weight Is Required For Air Shipment");
            }

            if (!isPositive(dto.getLengthCm())) {
                throw new ApplicationException(
                        "Valid Length Is Required For Air Shipment");
            }

            if (!isPositive(dto.getWidthCm())) {
                throw new ApplicationException(
                        "Valid Width Is Required For Air Shipment");
            }

            if (!isPositive(dto.getHeightCm())) {
                throw new ApplicationException(
                        "Valid Height Is Required For Air Shipment");
            }
        }

        // ROAD VALIDATION
        else if (mode.equalsIgnoreCase("ROAD")) {

            if (!StringUtils.hasText(dto.getVehicleType())) {
                throw new ApplicationException(
                        "Vehicle Type Is Required For Road Shipment");
            }

            if (dto.getGrossWeightKg() == null
                    || dto.getGrossWeightKg()
                            .compareTo(BigDecimal.ZERO) <= 0) {
                throw new ApplicationException(
                        "Valid Gross Weight Is Required For Road Shipment");
            }

            if (dto.getNoOfPackages() <= 0) {
                throw new ApplicationException(
                        "Number Of Packages Must Be Greater Than Zero For Road Shipment");
            }
        }
    }

    private void validatePositiveIfProvided(
            BigDecimal value, String fieldName)
            throws ApplicationException {

        if (value != null && value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApplicationException(
                    fieldName + " Must Be Greater Than Zero");
        }
    }

    private boolean isPositive(BigDecimal value) {
        return value != null
                && value.compareTo(BigDecimal.ZERO) > 0;
    }

    // ================= HEADER MAPPING =================

    private void getEnquiryVOFromDTO(
            EnquiryDTO dto,
            EnquiryVO vo) {

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
    public EnquiryResponseDTO getEnquiryById(Long id)
            throws ApplicationException {

        EnquiryVO enquiryVO = enquiryRepo.findById(id)
                .orElseThrow(() ->
                        new ApplicationException("Enquiry Not Found"));

        return convertToResponse(enquiryVO);
    }


    @Override
    public List<EnquiryResponseDTO> getEnquiryByOrgId(
            Long orgId, String branch) throws ApplicationException {

        List<EnquiryVO> enquiryList =
                enquiryRepo.findByOrgIdAndBranch(orgId, branch);

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
    public String getEnquiryDocId(Long orgId)
            throws ApplicationException {

        String screenCode = "ENQ";

        String result = enquiryRepo.getEnquiryDocId(orgId, screenCode);

        if (!org.springframework.util.StringUtils.hasText(result)) {
            throw new ApplicationException(
                    "Enquiry Doc ID Generation Failed");
        }

        return result;
    }
}
