package com.rest.appointment.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Appointment {
	
	private @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;
	private String customer;
	private String carVIN;
	private Double price;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
	private Date time;
	private String status;
	
	public Appointment() {}
	
	public Appointment(String customer, String carVIN, Double price, Date time, String status) {
		this.customer = customer;
		this.carVIN = carVIN;
		this.price = price;
		this.time = time;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCarVIN() {
		return carVIN;
	}
	public void setCarVIN(String carVIN) {
		this.carVIN = carVIN;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Appointment{" + "id=" + this.id + ", customer='" + this.customer + '\'' + ", carVIN='" + this.carVIN + '\'' + '}';
	}
}
