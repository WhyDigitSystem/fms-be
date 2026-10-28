package com.whydigit.fms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigModeDTO 
{

    private Long id;

    private Long configId;

    private String mode;

    private Boolean active;
}