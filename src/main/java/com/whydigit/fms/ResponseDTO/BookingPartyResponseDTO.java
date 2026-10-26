package com.whydigit.fms.ResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingPartyResponseDTO {

    private Long id;
    private String shipper;
    private String consignee;
    private String notifyParty;
    private String billingCustomer;

    private String pickupAddress;
    private String deliveryAddress;

    private String contactPerson;
    private String email;
    private String phone;
}