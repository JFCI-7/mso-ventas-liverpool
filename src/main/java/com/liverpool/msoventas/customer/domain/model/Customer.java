package com.liverpool.msoventas.customer.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	private String id;
	private String firstName;
	private String lastName;
	private String motherLastName;
	private String email;
	

}
