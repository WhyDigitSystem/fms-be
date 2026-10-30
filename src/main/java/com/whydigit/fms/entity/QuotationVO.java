package com.whydigit.fms.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.fms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quotation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quotationgen")
    @SequenceGenerator(name = "quotationgen", sequenceName = "quotationseq",
            initialValue = 1000000001, allocationSize = 1)
    @Column(name = "quotation_id")
    private Long id;

    // Quotation Information
    @Column(name = "doc_id", length = 20)
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @Column(name = "enquiry_no", length = 20)
    private String enquiryNo;
    @Column(name = "enquiry_date")
    private LocalDate enquiryDate;
    
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "company_name", length = 150, nullable = false)
    private String companyName;

    @Column(name = "agent_name", length = 100)
    private String agentName;

    @Column(name = "movement")
    private String movement; // Import / Export

    // Shipment Mode
    @Column(name = "shipment_mode")
    private String shipmentMode; // Sea / Air / Road
    // Route

 // Origin City
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_city", nullable = false)
    private CityVO originCity;

    // Destination City
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_city", nullable = false)
    private CityVO destinationCity;

    // Origin Port
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_port", nullable = false)
    private PortVO originPort;

    // Destination Port
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_port", nullable = false)
    private PortVO destinationPort;

    // Commercial Information
    @Column(name = "payment_terms", length = 100, nullable = false)
    private String paymentTerms;

    @Column(name = "revision_no")
    private int revisionNo;
    @Column(name = "incoterm", length = 10, nullable = false)
    private String incoterm;

    @Column(name = "service_type", length = 50, nullable = false)
    private String serviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency", nullable = false)
    private CurrencyVO currency;
    
    // Audit Fields
    @Column(name = "org_id")
    private Long orgId;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "createdby", length = 25)
    private String createdBy;

    @Column(name = "modifiedby", length = 25)
    private String updatedBy;

    // Customer Information
 // Customer Information
    @JsonManagedReference
    @OneToMany(mappedBy = "quotationVO",
            cascade = CascadeType.ALL)
    private List<QuotationCustomerInformationVO> customerInformation =
            new ArrayList<>();

    // Shipping Details
    @JsonManagedReference
    @OneToMany(mappedBy = "quotationVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<QuatationShippingDetailsVO> shippingDetails =
            new ArrayList<>();

    // Origin Charges
    @JsonManagedReference
    @OneToMany(mappedBy = "quotationVO",
            cascade = CascadeType.ALL)
    private List<QuotationOriginChargesVO> originCharges =
            new ArrayList<>();

    // Airfreight Rates
    @JsonManagedReference
    @OneToMany(mappedBy = "quotationVO",
            cascade = CascadeType.ALL)
    private List<QuotationAirFreightRatesVO> airFreightRates =
            new ArrayList<>();

    // Commercial Terms
    @JsonManagedReference
    @OneToMany(mappedBy = "quotationVO",
            cascade = CascadeType.ALL)
    private List<QuatationCommercialTermsVO> commercialTerms =
            new ArrayList<>();

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}