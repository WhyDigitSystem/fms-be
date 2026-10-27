
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
@Table(name = "booking_instruction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingInstructionVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_instructiongen")
    @SequenceGenerator(
            name = "booking_instructiongen",
            sequenceName = "booking_instructionseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "booking_instruction_id")
    private Long id;

    // Document Information
    @Column(name = "customer_reference_document", length = 500)
    private String customerReferenceDocument;

    @Column(name = "packing_list", length = 500)
    private String packingList;

    @Column(name = "commercial_invoice", length = 500)
    private String commercialInvoice;

    @Column(name = "dangerous_goods_document", length = 500)
    private String dangerousGoodsDocument;

    // Instructions
    @Column(name = "pickup_instructions", length = 2000)
    private String pickupInstructions;

    @Column(name = "delivery_instructions", length = 2000)
    private String deliveryInstructions;

    @Column(name = "internal_remarks", length = 2000)
    private String internalRemarks;

    // Booking Reference
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private BookingVO bookingVO;

}
