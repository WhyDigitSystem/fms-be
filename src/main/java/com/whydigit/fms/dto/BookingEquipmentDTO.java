package com.whydigit.fms.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEquipmentDTO {

    private Long id;

    private String equipmentType;

    private String equipmentSize;

    private int quantity;

    private String containerType;

    private String containerNumber;

    private String isoCode;

    private boolean temperatureRequired ;

    private double temperatureMin;

    private double temperatureMax;

    private boolean ventilationRequired ;

    private double ventilationValue;

    private boolean humidityRequired ;

    private double humidityValue;

    private boolean specialEquipment ;

    private String remarks;

 
}
