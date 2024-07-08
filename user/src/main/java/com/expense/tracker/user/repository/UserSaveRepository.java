package com.expense.tracker.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expense.tracker.user.entity.UserCredentials;

public interface UserSaveRepository extends JpaRepository<UserCredentials, Integer>{

	Optional<UserCredentials> findByUsername(String username);

}
