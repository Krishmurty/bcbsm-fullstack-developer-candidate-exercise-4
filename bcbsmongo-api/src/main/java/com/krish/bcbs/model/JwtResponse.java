package com.krish.bcbs.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String username;
	private final String firstName;
	private final String lastName;
	
	private final String token;

	public JwtResponse(String jwttoken, String username) {
		this.username = username;
		this.firstName = username;
		this.lastName = username;
		this.token = jwttoken;		
	}
	
	public JwtResponse(String token, String username, String firstName, String lastName) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.token = token;
	}


	public String getToken() {
		return this.token;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	
}