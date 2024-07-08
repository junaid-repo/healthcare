package com.expense.tracker.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expense.tracker.user.entity.DoctorDetailsEntity;

public interface DoctorRepository extends JpaRepository<DoctorDetailsEntity, String> {

}
