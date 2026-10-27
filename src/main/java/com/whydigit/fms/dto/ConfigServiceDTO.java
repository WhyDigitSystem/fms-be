package com.whydigit.fms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigServiceDTO {

    private Long id;

    private Long configId;

    // ID of an existing ServiceVO
    private Long service;

    private String mode;

    private Boolean active;
}
