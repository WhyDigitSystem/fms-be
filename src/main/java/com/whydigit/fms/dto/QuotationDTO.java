package com.whydigit.fms.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationDTO {

	private Long id;

	private String docId;

	// Quotation Information
//    private String docId;
//    private LocalDate docDate;
	private LocalDate expirationDate;
	private String companyName;
	private String agentName;
	private String movement; // Import / Export
	private int revisionNo;

	// Shipment Mode
	private String shipmentMode; // Sea / Air / Road

	private String enquiryNo;
	private LocalDate enquiryDate;
	// Route
	private Long originCity;

	// Destination City
	private Long destinationCity;

	// Origin Port
	private Long originPort;

	// Destination Port
	private Long destinationPort;

	// Commercial Information
	private String paymentTerms;
	private String incoterm;
	private String serviceType;

	// Normalized References
	private Long currency;
	private Long branch;

	// Audit Fields
	private Long orgId;
	private String cancelRemarks;
	private String remarks;
	private String createdBy;

	// Child Details
	private List<QuotationCustomerInformationDTO> customerInformation = new ArrayList<>();

	private List<QuatationShippingDetailsDTO> shippingDetails = new ArrayList<>();

	private List<QuotationOriginChargesDTO> originCharges = new ArrayList<>();

	private List<QuotationAirFreightRatesDTO> airFreightRates = new ArrayList<>();

	private List<QuatationCommercialTermsDTO> commercialTerms = new ArrayList<>();
}