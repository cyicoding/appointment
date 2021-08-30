package com.rest.appointment.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.rest.appointment.model.Appointment;

public interface AppointmentService {
	
	Optional<Appointment> findById(Long id);
	Appointment save(Appointment appointment);
	List<Appointment> findAll();
	List<Appointment> findAll(Date dateFrom, Date dateTo);
	void deleteById(Long id);

}
