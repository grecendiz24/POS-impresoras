package com.LeadTo.Soft.models;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.LeadTo.Soft.models.Productos;
@Component

public class impresion {
	private String n_impresora;
	private String folio;
	private String fecha;
	private String mesa;
	private ArrayList<Productos> productos;
	private String ip;
	
	
	public String getN_impresora() {
		return n_impresora;
	}
	public void setN_impresora(String n_impresora) {
		this.n_impresora = n_impresora;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMesa() {
		return mesa;
	}
	public void setMesa(String mesa) {
		this.mesa = mesa;
	}

	
	public ArrayList<Productos> getProductos() {
		return productos;
	}
	public void setProductos(ArrayList<Productos> productos) {
		this.productos = productos;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
