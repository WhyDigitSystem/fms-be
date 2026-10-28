package com.whydigit.fms.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigModeResponsesDTO {

    private Long id;

    private String mode;

    private Boolean active;
}