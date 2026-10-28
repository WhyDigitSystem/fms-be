package com.whydigit.fms.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityResponseDetailsDTO {

	private Long id;
	private String city;
	private String cityCode;
}
