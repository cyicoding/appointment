package com.rest.appointment.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rest.appointment.model.Appointment;
import com.rest.appointment.repository.AppointmentRepository;

@Repository
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private AppointmentRepository repository;
	  
	@Override
	public Optional<Appointment> findById(Long id) {		
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Appointment save(Appointment appointment) {
		return repository.save(appointment);
	}

	@Override
	public List<Appointment> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Appointment> findAll(Date dateFrom, Date dateTo) {
		
		TypedQuery<Appointment> query = em.createQuery("select a from Appointment a "
				+ "where a.time >= ?1 and a.time <= ?2 order by price asc",
				Appointment.class);
		query.setParameter(1, dateFrom);
		query.setParameter(2, dateTo);
		
		return query.getResultList();
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);	
	}

}
