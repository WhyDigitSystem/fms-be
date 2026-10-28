package com.whydigit.fms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "booking_routing")
@Data
public class BookingRoutingVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_routingseq")
    @SequenceGenerator(
        name = "booking_routingseq",
        sequenceName = "booking_routingseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    @Column(name = "routing_id")
    private Long id;

    @Column(name = "place_of_receipt")
    private String placeOfReceipt;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "place_of_delivery")
    private String placeOfDelivery;

    @Column(name = "requested_etd")
    private LocalDate requestedETD;

    @Column(name = "requested_eta")
    private LocalDate requestedETA;

    @Column(name = "carrier_preference")
    private String carrierPreference;

    @Column(name = "routing_preference")
    private String routingPreference;

    @Column(name = "incoterm")
    private String incoterm;
    
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private BookingVO bookingVO;

}