package com.whydigit.fms.ResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {

    private Long id;
    private String employeeCode;
    private String employeeName;
    private String gender;

    private BranchResponseDTO branch;
    private DepartmentResponseDTO department;
    private DesignationResponseDTO designation;

    private String dateOfBirth;
    private String joiningdate;
    private String createdBy;
    private String updatedBy;
    private Long orgId;
    private String cancel;
    private String cancelRemark;
    private String active;
}