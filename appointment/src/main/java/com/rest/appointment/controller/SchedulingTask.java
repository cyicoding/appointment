package com.rest.appointment.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.rest.appointment.model.Appointment;
import com.rest.appointment.service.AppointmentService;

/*
 * An appointment scheduler function that creates new appointments at a random interval.
 */
@Configuration
@EnableScheduling
public class SchedulingTask {
	
	private Random random = new Random();
	private int chanceToCreate = 50;
	
	private static final Logger log = LoggerFactory.getLogger(SchedulingTask.class);
	
	@Autowired
	AppointmentService service;
	
	@Scheduled(fixedDelay = 10000)
	public void randomlyCreateAppointments() {
		
		final int roll = random.nextInt(100) + 1;
		if (roll < chanceToCreate ) {
			Appointment appt = new Appointment("TBD", "TBD", 0.0, new Date(), "New");
			service.save(appt);
		    log.info("New appointment created at " + new Timestamp(System.currentTimeMillis()) + " - " + appt.toString());
		}
	}

}
