
package com.whydigit.fms.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalParameterResponseDTO {

    private Long id;

    private Long orgId;
    private String userid;

    private BranchResponseDTO branch;
    private FinancialYearResponseDTO financialYear;
}
