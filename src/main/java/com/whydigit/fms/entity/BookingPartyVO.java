package com.whydigit.fms.entity;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking_party")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingPartyVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_partygen")
    @SequenceGenerator(
            name = "booking_partygen",
            sequenceName = "booking_partyseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "booking_party_id")
    private Long id;

    // Party Information
    @Column(name = "shipper", length = 150)
    private String shipper;

    @Column(name = "consignee", length = 150)
    private String consignee;

    @Column(name = "notify_party", length = 150)
    private String notifyParty;

    @Column(name = "billing_customer", length = 150)
    private String billingCustomer;

    // Address Information
    @Column(name = "pickup_address", length = 1000)
    private String pickupAddress;

    @Column(name = "delivery_address", length = 1000)
    private String deliveryAddress;

    // Contact Information
    @Column(name = "contact_person", length = 100)
    private String contactPerson;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "phone", length = 30)
    private String phone;

    // Booking Reference
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private BookingVO bookingVO;

}
