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
	
	@GetMapping("/appointments")
	List<Appointment> getAll() {
		return service.findAll();
	}
	
	@GetMapping("/appointments/{dateFrom}/{dateTo}")
	List<Appointment> getAllWithDateRange(@PathVariable("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom, 
			@PathVariable("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd")Date dateTo) {
		return service.findAll(dateFrom, dateTo);
	}
	
	@GetMapping("/appointments/{id}")
	Optional<Appointment> getOne(@PathVariable Long id) {
		return service.findById(id);
	}
	
//	@PostMapping("/appointments")
//	ResponseEntity<Appointment> create(@RequestBody Appointment appt) {
//		if (appt == null) {
//			return ResponseEntity.badRequest().build();
//		}
//		service.save(appt);
//		return ResponseEntity.ok().body(appt);
//	}
	
	@PostMapping("/appointments")
	ResponseEntity<List<Appointment>> createAppointments(@RequestBody List<Appointment> appts) {
		if (appts == null) {
			return ResponseEntity.badRequest().build();
		}
		for (Appointment appt : appts) {
			service.save(appt);
		}
		return ResponseEntity.ok().body(appts);
	}
	
	@PutMapping("/appointments/{id}") 
	ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment updatedAppt, @PathVariable Long id) {
		if (updatedAppt == null || id == null) {
			return ResponseEntity.badRequest().build();
		}
		Optional<Appointment> optionalRecord = service.findById(id);
	    if (optionalRecord == null) {
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
	
	@DeleteMapping("/appointments/{id}")
	ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
		if (service.findById(id) == null) {
			return ResponseEntity.badRequest().build();
	    }
		service.deleteById(id);
		return ResponseEntity.ok().build();
	}	
	
	@DeleteMapping("/appointments")
	ResponseEntity<Void> deleteAppointments(@RequestBody List<Long> ids) {
		if (ids == null)
			return ResponseEntity.badRequest().build();
		for (Long id : ids) {
			if (service.findById(id) != null) {
				service.deleteById(id);
		    }		
		}
		return ResponseEntity.ok().build();
	}	
	
}


