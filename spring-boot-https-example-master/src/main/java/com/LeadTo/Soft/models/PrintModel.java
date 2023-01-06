package com.LeadTo.Soft.models;

import org.springframework.stereotype.Component;

@Component
public class PrintModel {

	private String error;
	private String exito;

	public String getError() {
		return error;
	}

	public String setError(String error) {
		return this.error = error;
	}

	public String getExito() {
		return exito;
	}

	public void setExito(String exito) {
		this.exito = exito;
	}

}
