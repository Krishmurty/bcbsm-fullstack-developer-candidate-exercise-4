package com.krish.bcbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.krish.bcbs.config.JwtTokenUtil;
import com.krish.bcbs.model.JwtRequest;
import com.krish.bcbs.model.JwtResponse;
import com.krish.bcbs.model.UserDao;
import com.krish.bcbs.model.UserDto;
import com.krish.bcbs.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		//final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final UserDto userdet = userDetailsService.getUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userdet.getFirstName());

		//return ResponseEntity.ok(new JwtResponse(token,userDetails.getUsername()));
		return ResponseEntity.ok(new JwtResponse(token,userdet.getUsername(),userdet.getFirstName(),userdet.getLastName()));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
