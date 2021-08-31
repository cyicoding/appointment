package com.rest.appointment;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rest.appointment.model.Appointment;
import com.rest.appointment.repository.AppointmentRepository;

@Configuration
public class PreloadData {
	
	private static final Logger log = LoggerFactory.getLogger(PreloadData.class);
	
	@Bean
	CommandLineRunner initDatabase(AppointmentRepository repo) {
		// Initialize the database with some data
		return args -> {
		      log.info("Preloading " + repo.save(new Appointment("John", "DAGHEOU32532FDS", 123.0, new Date(), "Active")));
		      log.info("Preloading " + repo.save(new Appointment("Alex", "YOIG73DS907DES3", 356.0, new Date(), "Active")));
		      log.info("Preloading " + repo.save(new Appointment("Emily", "SDSLEI932KHFSF7", 98.0, new Date(), "Active")));
		      log.info("Preloading " + repo.save(new Appointment("Sara", "92KLDKSHGS31344", 290.0, new Date(), "Active")));
		      log.info("Preloading " + repo.save(new Appointment("David", "VDEWPIEW323IF3L", 20.0, new Date(), "Active")));
		      log.info("Preloading " + repo.save(new Appointment("Ryan", "QEWV3290SD21SVN", 58.0, new Date(), "Active")));
		    };
		
	}

}
