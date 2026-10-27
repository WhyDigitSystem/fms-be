package com.whydigit.fms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigProductDTO {

    private Long id;

    private Long configId;

    // ID of an existing ProductVO
    private Long product;

    private String mode;

    private Boolean active;
}
