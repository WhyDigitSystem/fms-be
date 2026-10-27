package com.whydigit.fms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingPartyDTO {

    private Long id;

    // Party Information
    private String shipper;

    private String consignee;

    private String notifyParty;

    private String billingCustomer;

    // Address Information
    private String pickupAddress;

    private String deliveryAddress;

    // Contact Information
    private String contactPerson;

    private String email;

    private String phone;

}
