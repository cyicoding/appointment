package com.rest.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.appointment.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
