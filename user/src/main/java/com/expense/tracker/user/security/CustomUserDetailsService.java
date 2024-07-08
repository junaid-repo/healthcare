package com.expense.tracker.user.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.expense.tracker.user.entity.UserCredentials;
import com.expense.tracker.user.repository.UserSaveRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserSaveRepository userRegRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		Optional<UserCredentials> creds = userRegRepo.findByUsername(username);
		return creds.map(CustomUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));

	}
}
