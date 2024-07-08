package com.expense.tracker.user.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.expense.tracker.user.dto.AuthRequest;
import com.expense.tracker.user.dto.BaseOutput;
import com.expense.tracker.user.dto.RegisterResponse;
import com.expense.tracker.user.entity.DoctorDetailsEntity;
import com.expense.tracker.user.entity.UserCredentials;
import com.expense.tracker.user.services.UserService;

@RestController
@RequestMapping("/expense/user")
public class UserController {

	@Autowired
	UserService serv;

	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/auth/register")
	ResponseEntity<RegisterResponse> registerUser(@RequestBody UserCredentials creds) {

		RegisterResponse response = new RegisterResponse();

		response = serv.registerUser(creds);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/add/doctor")
	ResponseEntity<BaseOutput> addDoctorDetails(@RequestBody DoctorDetailsEntity details) {
		BaseOutput baseOutput = new BaseOutput();

		baseOutput = serv.addDoctorDetails(details);

		return ResponseEntity.status(HttpStatus.OK).body(baseOutput);
	}

	@PostMapping("/auth/generateToken")
	ResponseEntity<String> generateToken(@RequestBody AuthRequest creds) {
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword()));
		String token = "";
		if (authenticate.isAuthenticated()) {

			token = serv.generateToken(creds);
		} else {
			throw new RuntimeException("Invalid access");
		}
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

	@PostMapping("/auth/validateToken/{token}")
	ResponseEntity<String> validateToken(@PathVariable String token) {

		serv.validateToken(token);

		return ResponseEntity.status(HttpStatus.OK).body("token is valid");
	}

	@GetMapping("/auth/checkUserAccess")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	String checkUser() {
		return "validated";
	}

}
