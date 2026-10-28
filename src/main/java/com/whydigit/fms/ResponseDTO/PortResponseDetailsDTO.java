package com.whydigit.fms.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortResponseDetailsDTO {

	private Long id;
    private String portType;

    private String portCode;

    private String portName;
}
