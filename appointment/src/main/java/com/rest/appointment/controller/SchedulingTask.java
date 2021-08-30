package com.rest.appointment.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.rest.appointment.model.Appointment;
import com.rest.appointment.service.AppointmentService;

@Configuration
@EnableScheduling
public class SchedulingTask {
	@Autowired
	AppointmentService service;
	
	@Scheduled(fixedRate = 10000)
	public void scheduleFixedRateTask() {
		Appointment appt = new Appointment("TBD", "TBD", 0.0, new Date(), "New");
		service.save(appt);
	    System.out.println(
	      "New appointment created - " + appt.toString());
	}

}
