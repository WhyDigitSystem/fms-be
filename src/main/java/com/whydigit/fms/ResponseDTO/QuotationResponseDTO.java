package com.whydigit.fms.ResponseDTO;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationResponseDTO {

    private Long id;

    private String docId;
    private LocalDate docDate;
    private LocalDate expirationDate;

    private String companyName;
    private String agentName;

    private String originCode;
    private String originCity;

    private String destinationCode;
    private String destinationCity;

    private String paymentTerms;
    private String incoterm;
    private String serviceType;

    private CurrencyResponseDTO currency;
    private BranchResponseDTO branch;
    private Long orgId;

    private boolean active;
    private boolean cancel;

    private String cancelRemarks;
    private String remarks;

    private String createdBy;
    private String updatedBy;

    // Customer Information
    private List<QuotationCustomerInformationResponseDTO>
            customerInformation = new ArrayList<>();

    // Shipping Details
    private List<QuatationShippingDetailsResponseDTO>
            shippingDetails = new ArrayList<>();

    // Origin Charges
    private List<QuotationOriginChargesResponseDTO>
            originCharges = new ArrayList<>();

    // Air Freight Rates
    private List<QuotationAirFreightRatesResponseDTO>
            airFreightRates = new ArrayList<>();

    // Commercial Terms
    private List<QuatationCommercialTermsResponseDTO>
            commercialTerms = new ArrayList<>();
}

