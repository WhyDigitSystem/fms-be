package com.whydigit.fms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalParameterDTO {

//    private Long id;

    private Long orgId;
    private String userid;

    private Long branch;
    private Long financialYear;
}