package com.expense.tracker.user.services;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

import com.expense.tracker.user.dto.AuthRequest;
import com.expense.tracker.user.dto.BaseOutput;
import com.expense.tracker.user.dto.RegisterResponse;
import com.expense.tracker.user.entity.DoctorDetailsEntity;
import com.expense.tracker.user.entity.UserCredentials;

public interface UserService {

	RegisterResponse registerUser(UserCredentials creds);

	String generateToken(AuthRequest creds);

	void validateToken(String token);

	CompletableFuture<String> registerBulkUser(MultipartFile file) throws IOException;

	BaseOutput addDoctorDetails(DoctorDetailsEntity details);

}
