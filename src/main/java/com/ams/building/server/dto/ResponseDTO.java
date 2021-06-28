package com.ams.building.server.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseDTO<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long recordsTotal;
	private Long recordsFiltered;
	private List<T> data;

}
