package com.rest.appointment.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.appointment.model.Appointment;
import com.rest.appointment.service.AppointmentService;

@RestController
public class AppointmentController {
	
	@Autowired
	private AppointmentService service;
	
	/*
	 * Get all appointments
	 */
	@GetMapping("/appointments")
	List<Appointment> getAll() {
		return service.findAll();
	}
	
	/*
	 * Get all appointments within a date rage and sorted by price
	 */
	@GetMapping("/appointments/{dateFrom}/{dateTo}")
	List<Appointment> getAllWithDateRange(@PathVariable("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom, 
			@PathVariable("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd")Date dateTo) {
		return service.findAll(dateFrom, dateTo);
	}
	
	/*
	 * Get an appointment by Id
	 */
	@GetMapping("/appointments/{id}")
	Appointment getOne(@PathVariable Long id) {
		Optional<Appointment> opptionalAppointment = service.findById(id);
		Appointment a = opptionalAppointment.isPresent() ? opptionalAppointment.get() : null;
		return a;
	}
	
	/*
	 * Create new appointments
	 */
	@PostMapping("/appointments")
	ResponseEntity<String> createAppointments(@RequestBody List<Appointment> appts) {
		if (appts == null) {
			return ResponseEntity.badRequest().body("Appointment can't be null!");
		}
		for (Appointment appt : appts) {
			appt.setId(null);
			service.save(appt);
		}
		return ResponseEntity.ok().body(appts.size() + " new appointments have been added.");
	}
	
	/*
	 * Update an appointment
	 */
	@PutMapping("/appointments/{id}") 
	ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment updatedAppt, @PathVariable Long id) {
		if (updatedAppt == null || id == null) {
			return ResponseEntity.badRequest().build();
		}
		Optional<Appointment> optionalRecord = service.findById(id);
	    if (!optionalRecord.isPresent()) {
	    	return ResponseEntity.badRequest().build();
	    }
	    Appointment existingRecord = optionalRecord.get();
	    
	    existingRecord.setCarVIN(updatedAppt.getCarVIN());
	    existingRecord.setCustomer(updatedAppt.getCustomer());
	    existingRecord.setTime(updatedAppt.getTime());
	    existingRecord.setPrice(updatedAppt.getPrice());
	    existingRecord.setStatus(updatedAppt.getStatus());
		
	    service.save(existingRecord);
	    return ResponseEntity.ok().body(existingRecord);
		
	}
	
	/*
	 * Update the status of an appointment
	 */
	@PutMapping("/appointments/{id}/{status}") 
	ResponseEntity<Appointment> updateAppointmentStatus(@PathVariable Long id, @PathVariable String status) {
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}
		Optional<Appointment> optionalRecord = service.findById(id);
	    if (!optionalRecord.isPresent()) {
	    	return ResponseEntity.badRequest().build();
	    }
	    Appointment existingRecord = optionalRecord.get();
	    existingRecord.setStatus(status);
		
	    service.save(existingRecord);
	    return ResponseEntity.ok().body(existingRecord);
		
	}
	
	/*
	 * Get an appointment by Id
	 */
	@DeleteMapping("/appointments/{id}")
	ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
		Optional<Appointment> optionalRecord = service.findById(id);
		if (!optionalRecord.isPresent()) {
			return ResponseEntity.badRequest().body("Appointment " + id + " doesn't exist!");
	    }
		service.deleteById(id);
		return ResponseEntity.ok().body("Appointment " + id + " has been deleted.");
	}	
	
	/*
	 * Delete appointments
	 */
	@DeleteMapping("/appointments")
	ResponseEntity<String> deleteAppointments(@RequestBody List<Long> ids) {
		if (ids == null)
			return ResponseEntity.badRequest().build();
		String deleted = "";
		for (Long id : ids) {
			Optional<Appointment> optionalRecord = service.findById(id);
			if (optionalRecord.isPresent()) {
				service.deleteById(id);
				deleted += id + " ";
		    }		
		}
		return ResponseEntity.ok().body("Appointments " + deleted + "have been deleted.");
	}	
	
}


