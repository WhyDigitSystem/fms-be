
package com.whydigit.fms.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookingseq")
    @SequenceGenerator(
        name = "bookingseq",
        sequenceName = "bookingseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    @Column(name = "booking_id")
    private Long id;

    @Column(name = "doc_id", unique = true)
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate= LocalDate.now();


    @Column(name = "bookingtype")
    private String bookingType;

    @Column(name = "transportmode")
    private String transportMode;

    @Column(name = "servicetype")
    private String serviceType;

    @Column(name = "customer")
    private String customer;

    @Column(name = "customerreference")
    private String customerReference;

    @Column(name = "salesperson")
    private String salesperson;

    @Column(name = "operationsowner")
    private String operationsOwner;

    @Column(name = "bookingstatus")
//    private String bookingStatus = "Draft";
    private String bookingStatus ;

    // Audit Fields
    @Column(name = "org_id")
    private Long orgId;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "active")
    private boolean active = true ;

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

    @OneToMany(mappedBy = "bookingVO", cascade = CascadeType.ALL,
               orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookingRoutingVO> routingDetails = new ArrayList<>();

    @OneToMany(mappedBy = "bookingVO", cascade = CascadeType.ALL,
               orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookingPartyVO> partyDetails = new ArrayList<>();

    @OneToMany(mappedBy = "bookingVO", cascade = CascadeType.ALL,
               orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookingCargoVO> cargoDetails = new ArrayList<>();

    @OneToMany(mappedBy = "bookingVO", cascade = CascadeType.ALL,
               orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookingEquipmentVO> equipmentDetails = new ArrayList<>();

    @OneToMany(mappedBy = "bookingVO", cascade = CascadeType.ALL,
               orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookingCommercialVO> commercialDetails = new ArrayList<>();


    @OneToMany(mappedBy = "bookingVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
 private List<BookingInstructionVO> instructionDetails;
}
