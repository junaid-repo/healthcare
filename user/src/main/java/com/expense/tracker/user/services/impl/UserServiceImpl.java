package com.expense.tracker.user.services.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.expense.tracker.user.dto.AuthRequest;
import com.expense.tracker.user.dto.BaseOutput;
import com.expense.tracker.user.dto.RegisterResponse;
import com.expense.tracker.user.entity.DoctorDetailsEntity;
import com.expense.tracker.user.entity.UserCredentials;
import com.expense.tracker.user.repository.DoctorRepository;
import com.expense.tracker.user.repository.UserSaveRepository;
import com.expense.tracker.user.services.UserService;
import com.expense.tracker.user.utility.SendEmail;
import com.mailjet.client.errors.MailjetException;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	UserSaveRepository userRegRepo;

	@Autowired
	PasswordEncoder psdEnocder;

	@Autowired
	DoctorRepository drp;

	@Autowired
	JwtService jwtService;

	@Autowired
	SendEmail eml;

	@Override
	public RegisterResponse registerUser(UserCredentials user) {
		user.setPassword(psdEnocder.encode(user.getPassword()));
		UserCredentials out = new UserCredentials();
		RegisterResponse response = new RegisterResponse();
		String username = "";

		out = userRegRepo.save(user);

		if (out.getId() > 0) {
			username = user.getName().replaceAll("\\s", "").toLowerCase() + String.valueOf(out.getId());
		}

		out.setUsername(username);

		out = userRegRepo.save(out);

		if (out.getId() > 0) {
			String fromEmail = "admin" + String.valueOf(out.getId()) + "@friendsmobile.store";
			String fromName = "Bindass App";
			String toEmail = out.getEmail();
			String subject = "New User for Bindass App";
			String body = "Hi Mr. " + out.getName() + ". Your account is successfully created with username "
					+ out.getUsername();

			try {
				eml.sendSimpleEmail(fromEmail, fromName, toEmail, subject, body);
			} catch (MailjetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		response.setReturnCode("201");
		response.setReturnMsg("User Created");
		response.setUsername(username);
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public String generateToken(AuthRequest creds) {
		// TODO Auto-generated method stub
		return jwtService.generateToken(creds.getUsername());
	}

	@Override
	public void validateToken(String token) {
		// TODO Auto-generated method stub
		jwtService.validateToken(token);

	}

	@Override
	public CompletableFuture<String> registerBulkUser(MultipartFile file) throws IOException {
		Reader reader = new InputStreamReader(file.getInputStream());
		CSVReader csvReader = new CSVReaderBuilder(reader).build();
		try {
			List<String[]> rows = csvReader.readAll();
			int count = 0;
			List<UserCredentials> creds = new ArrayList<>();
			// log.info("The value from csv file is --> " + rows.toString());

			for (String[] row : rows) {
				UserCredentials cred = new UserCredentials();
				cred.setName(row[0]);
				cred.setEmail(row[1]);
				cred.setPassword(row[2]);
				cred.setRoles(row[3]);
				creds.add(cred);
				log.info("The value from csv file is --> " + cred);
			}

			creds.stream().skip(1).forEach(row -> {

				registerUser(row);

			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return CompletableFuture.completedFuture("Completed");
	}

	@Override
	public BaseOutput addDoctorDetails(DoctorDetailsEntity details) {
		drp.save(details);
		return BaseOutput.builder().returnCode(String.valueOf(HttpStatus.CREATED.value()))
				.returnMsg(HttpStatus.CREATED.getReasonPhrase()).build();
	}
}
