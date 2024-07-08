package com.expense.tracker.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterResponse extends BaseOutput{
	
	private String username;

}
