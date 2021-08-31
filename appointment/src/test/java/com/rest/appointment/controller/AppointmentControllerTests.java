package com.rest.appointment.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.appointment.model.Appointment;
import com.rest.appointment.service.AppointmentService;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@WebMvcTest(value = AppointmentController.class)
public class AppointmentControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    ObjectMapper mapper;
	
	@MockBean
	private AppointmentService appointmentService;
	
	Appointment apptOne = new Appointment("John", "DAGHEOU32532FDS", 123.0, new Date(), "Active");
	Appointment apptTwo = new Appointment("Alex", "YOIG73DS907DES3", 356.0, new Date(), "Active");
	Appointment apptThree = new Appointment("Emily", "SDSLEI932KHFSF7", 98.0, new Date(), "Active");
		
	
	@Test
	@Order(1)
	public void getAllAppointments_success() throws Exception {
		apptOne.setId(1l);
		apptTwo.setId(2l);
		apptThree.setId(3l);
	    List<Appointment> appointments = new ArrayList<>(Arrays.asList(apptOne, apptTwo, apptThree));
	    
	    Mockito.when(appointmentService.findAll()).thenReturn(appointments);
	    
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/appointments")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", hasSize(3)))
	            .andExpect(jsonPath("$[2].customer", is("Emily")));
	    
//	    String json = result.getResponse().getContentAsString();
//	    DocumentContext documentContext = JsonPath.parse(json);
//	    assertThat(documentContext.read("$[0].customer"), is("John"));
	    
	}
	
	@Test
	@Order(2)
	public void getAppointmentById_success() throws Exception {
		apptOne.setId(1l);		
		Mockito.when(appointmentService.findById(apptOne.getId())).thenReturn(Optional.of(apptOne));
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/appointments/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.customer", is("John")));
	}
	
	@Test
	@Order(3)
	public void getAppointmentByDateRange_success() throws Exception {
		
		String dateFromStr = "2021-07-19";
		String dateToStr = "2021-08-01";
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	     
        Date dateFrom, dateTo;         
        try {
            dateFrom = dateFormat.parse(dateFromStr);
            dateTo = dateFormat.parse(dateToStr);
        
            List<Appointment> appointments = new ArrayList<>(Arrays.asList(apptOne, apptTwo));
            Mockito.when(appointmentService.findAll(dateFrom, dateTo)).thenReturn(appointments);
		
        } catch (ParseException e) {
        	Logger log = LoggerFactory.getLogger(AppointmentControllerTests.class);
        	log.info("Cannot parse date!");
        }
		mockMvc.perform(MockMvcRequestBuilders
				.get("/appointments/2021-07-19/2021-08-01")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[1].customer", is("Alex")));;
	}
	
	@Test
	@Order(4)
	public void createAppointments_success() throws Exception {
	    Appointment newAppt = new Appointment("Sara", "92KLDKSHGS31344", 290.0, new Date(), "Active");
	    newAppt.setId(4l);
	    List<Appointment> list = new ArrayList<Appointment>();
	    list.add(newAppt);
	    Mockito.when(appointmentService.save(Mockito.any(Appointment.class))).thenReturn(newAppt);
	    String requestBody = this.mapper.writeValueAsString(list);
	    RequestBuilder mockRequest = MockMvcRequestBuilders.post("/appointments")
	            .accept(MediaType.APPLICATION_JSON)
	            .content(requestBody)
	            .contentType(MediaType.APPLICATION_JSON);

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$[0].customer", is("Sara")));
	}
	
	@Test
	@Order(5)
	public void createAppointments_null() throws Exception {
	    List<Appointment> newAppts = null;

	    RequestBuilder mockRequest = MockMvcRequestBuilders.post("/appointments")
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(newAppts))
	            .contentType(MediaType.APPLICATION_JSON);

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(6)
	public void updateAppointment_success() throws Exception {
	    Appointment updatedAppt = new Appointment("Ryan", "QEWV3290SD21SVN", 58.0, new Date(), "Active");
	    updatedAppt.setId(1l);

	    Mockito.when(appointmentService.findById(1l)).thenReturn(Optional.of(apptOne));
	    Mockito.when(appointmentService.save(Mockito.any(Appointment.class))).thenReturn(updatedAppt);

	    RequestBuilder mockRequest = MockMvcRequestBuilders.put("/appointments/1")	            
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(updatedAppt))
	            .contentType(MediaType.APPLICATION_JSON);

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.customer", is("Ryan")));
	}
	
	
	@Test
	@Order(7)
	public void updateAppointment_null() throws Exception {
	    Appointment updatedAppt = null;

	    RequestBuilder mockRequest = MockMvcRequestBuilders.put("/appointments/1")
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(updatedAppt))
	            .contentType(MediaType.APPLICATION_JSON);

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(8)
	public void updateAppointmentStatus_success() throws Exception {
	    
	    Appointment updatedAppt = apptOne;
	    updatedAppt.setStatus("Cancelled");
	    Mockito.when(appointmentService.findById(1l)).thenReturn(Optional.of(apptOne));
	    Mockito.when(appointmentService.save(Mockito.any(Appointment.class))).thenReturn(updatedAppt);

	    RequestBuilder mockRequest = MockMvcRequestBuilders.put("/appointments/1/Cancelled")	            
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(updatedAppt))
	            .contentType(MediaType.APPLICATION_JSON);

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.status", is("Cancelled")));
	}
	
	
	@Test
	@Order(9)
	public void updateAppointment_recordNotFound() throws Exception {
	    Appointment updatedAppt = new Appointment();

	    Mockito.when(appointmentService.findById(5l)).thenReturn(null);

	    RequestBuilder mockRequest = MockMvcRequestBuilders.put("/appointments/5")            
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(updatedAppt))
	            .contentType(MediaType.APPLICATION_JSON);

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isBadRequest());
}
	
	@Test
	@Order(10)
	public void deleteAppointmentById_success() throws Exception {
		apptTwo.setId(2l);
	    Mockito.when(appointmentService.findById(apptTwo.getId())).thenReturn(Optional.of(apptTwo));

	    mockMvc.perform(MockMvcRequestBuilders
	            .delete("/appointments/2")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	}

	@Test
	@Order(11)
	public void deleteAppointmentById_notFound() throws Exception {
	    Mockito.when(appointmentService.findById(5l)).thenReturn(null);

	    mockMvc.perform(MockMvcRequestBuilders
	            .delete("/appointments/5")
	            .contentType(MediaType.APPLICATION_JSON))
	    .andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(12)
	public void deleteAppointments_success() throws Exception {
		List<Long> list = new ArrayList<>();
		apptOne.setId(1l);
		apptTwo.setId(2l);
		list.add(1l);
		list.add(2l);
		Mockito.when(appointmentService.findById(1l)).thenReturn(Optional.of(apptOne));
	    Mockito.when(appointmentService.findById(2l)).thenReturn(Optional.of(apptTwo));

	    mockMvc.perform(MockMvcRequestBuilders
	            .delete("/appointments")
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(list))
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	}
	
	@Test
	@Order(13)
	public void deleteAppointments_null() throws Exception {
		List<Long> list = null;
	    mockMvc.perform(MockMvcRequestBuilders
	            .delete("/appointments")
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(list))
	            .contentType(MediaType.APPLICATION_JSON))
	    .andExpect(status().isBadRequest());
	}
	
}
