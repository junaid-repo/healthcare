package com.expense.tracker.user.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DoctorDetails")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DoctorDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_seq_generator")
	@SequenceGenerator(name = "entity_seq_generator", sequenceName = "e_user_seq", allocationSize = 1)
	private String doctorId;
	
	private String name;
	
	private String specializations;
	
	private String experience;
	
	private  String visitDays;
	
	private String visitTimings;
	
	

}
