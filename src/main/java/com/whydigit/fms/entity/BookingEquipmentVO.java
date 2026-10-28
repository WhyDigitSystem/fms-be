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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "booking_equipment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEquipmentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_equipmentgen")
    @SequenceGenerator(
            name = "booking_equipmentgen",
            sequenceName = "booking_equipmentseq", initialValue = 1000000001,
            allocationSize = 1
    )

    @Column(name = "booking_equipment_id")
    private Long id;


    @Column(name = "equipment_type", length = 100, nullable = false)
    private String equipmentType;

    @Column(name = "equipment_size", length = 50)
    private String equipmentSize;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "container_type", length = 100)
    private String containerType;

    @Column(name = "container_number", length = 50)
    private String containerNumber;

    @Column(name = "iso_code", length = 20)
    private String isoCode;

    @Column(name = "temperature_required")
    private boolean temperatureRequired = false;

    @Column(name = "temperature_min")
    private double temperatureMin;

    @Column(name = "temperature_max")
    private double temperatureMax;

    @Column(name = "ventilation_required")
    private boolean ventilationRequired = false;

    @Column(name = "ventilation_value")
    private double ventilationValue;

    @Column(name = "humidity_required")
    private boolean humidityRequired = false;

    @Column(name = "humidity_value")
    private double humidityValue;

    @Column(name = "special_equipment")
    private boolean specialEquipment = false;

    @Column(name = "remarks", length = 1000)
    private String remarks;
    

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    @JsonBackReference
    private BookingVO bookingVO;
}