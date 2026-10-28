package com.whydigit.fms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.whydigit.fms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enquiry")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnquiryVO {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enquirygen")
	@SequenceGenerator(name = "enquirygen", sequenceName = "enquiryseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "enquiry_id")
    private Long id;

    // Enquiry Information
    @Column(name = "doc_id", unique = true)
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @Column(name = "company")
    private String company;

    @Column(name = "movement")
    private String movement; // Import / Export

    // Shipment Mode
    @Column(name = "shipment_mode")
    private String shipmentMode; // Sea / Air / Road

 // Shipment Route
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_location")
    private CityVO fromLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_location")
    private CityVO toLocation;


    // Sea Shipment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_port")
    private PortVO originPort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_port")
    private PortVO destinationPort;


    // Air Shipment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_airport")
    private PortVO originAirport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_airport")
    private PortVO destinationAirport;


    // Road Shipment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_terminal")
    private PortVO originTerminal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_terminal")
    private PortVO destinationTerminal;
    
    
    // Cargo Details
    @Column(name = "cargo_description")
    private String cargoDescription;

    @Column(name = "cargo_type")
    private String cargoType; // LCL / FCL, etc.

    @Column(name = "volume_cbm", precision = 15, scale = 3)
    private BigDecimal volumeCbm;

    @Column(name = "gross_weight_kg", precision = 15, scale = 3)
    private BigDecimal grossWeightKg;

    @Column(name = "no_of_packages")
    private int noOfPackages;

    // Air dimensions
    @Column(name = "length_cm", precision = 15, scale = 2)
    private BigDecimal lengthCm;

    @Column(name = "width_cm", precision = 15, scale = 2)
    private BigDecimal widthCm;

    @Column(name = "height_cm", precision = 15, scale = 2)
    private BigDecimal heightCm;

    // Road shipment
    @Column(name = "vehicle_type")
    private String vehicleType;

    // Audit fields
    @Column(name = "org_id")
    private Long orgId;

    @ManyToOne(fetch = FetchType.LAZY)
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
    
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}