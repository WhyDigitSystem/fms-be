package com.whydigit.fms.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigProductResponsesDTO {

    private Long id;

//    private Long product;

    private String mode;

    private Boolean active;
}