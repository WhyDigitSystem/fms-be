package com.whydigit.fms.entity;

import java.math.BigDecimal;

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
@Table(name = "booking_commercial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCommercialVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_commercialgen")
    @SequenceGenerator(
            name = "booking_commercialgen",
            sequenceName = "booking_commercialseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "booking_commercial_id")
    private Long id;

    // Commercial Information
    @Column(name = "freight_payment_terms", length = 100)
    private String freightPaymentTerms;

    @Column(name = "quotation_reference", length = 50)
    private String quotationReference;

    @Column(name = "sell_rate", precision = 14, scale = 2)
    private BigDecimal sellRate;

    @Column(name = "sell_currency", length = 3)
    private String sellCurrency;

    @Column(name = "buy_rate", precision = 14, scale = 2)
    private BigDecimal buyRate;

    @Column(name = "buy_currency", length = 3)
    private String buyCurrency;

    @Column(name = "payment_terms", length = 100)
    private String paymentTerms;

    @Column(name = "credit_check_status", length = 50)
    private String creditCheckStatus;

    @Column(name = "commercial_remarks", length = 2000)
    private String commercialRemarks;

    // Booking Reference
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private BookingVO bookingVO;

}