package com.base.app.client.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.app.business.GlobalFunctions;
import com.base.app.business.services.UserService;
import com.base.app.data.entity.User;

@RestController
@RequestMapping("users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping(path = "/{status}")
	public ResponseEntity<Map<String, Object>> getAll(@PathVariable(name = "status") String status){
		Map<String, Object> response = new HashMap<>();
		List<String> errors = new ArrayList<>();
		try {
			List<User> users = null;
			if (status.equalsIgnoreCase("actives")) {
				users = userService.getAllByStatus(true);
			} else if (status.equalsIgnoreCase("desactives")) {
				users = userService.getAllByStatus(false);
			} else {
				logger.error("Error: params status no accepted");
				errors.add("params status no accepted, Status: actives -desactives");
				response.put("mensaje", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			response.put("users", users);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.put("mensaje", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody User user, BindingResult result){
		Map<String, Object> response = new HashMap<>();
		List<String> errors = new ArrayList<>();
		if (result.hasErrors()) {
			errors = result.getFieldErrors().stream().map(e -> 
				e.getDefaultMessage()
			).collect(Collectors.toList());
		}
		if (userService.getByEmail(user.getEmail()) != null) {
			logger.error("Error: The email already registered");
			errors.add("The email already registered");
	    }
		if (!GlobalFunctions.validatePassword(user.getPassword())) {
			logger.error("Error: The password must have an uppercase, lowercase letters and two numbers");
			errors.add("The password must have an uppercase, lowercase letters and two numbers.");
		}
		if(errors.size() > 0) {
			response.put("mensaje", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		user = userService.save(user);
		response.put("user", user);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/activate/{id}")
	public ResponseEntity<Map<String, Object>> activateUser(@PathVariable UUID id){
		Map<String, Object> response = new HashMap<>();
		User user = userService.getByIdStatus(id, false);
		if (user == null) {
			logger.error("Error: User not found or not inactive.");
			response.put("mensaje", "User not found or not inactive.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "The user has been activated.");
		response.put("user", userService.updateStatus(user, true));
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PutMapping(path = "/desactivate/{id}")
	public ResponseEntity<Map<String, Object>> desactivateUser(@PathVariable UUID id){
		Map<String, Object> response = new HashMap<>();
		User user = userService.getByIdStatus(id, true);
		if (user == null) {
			logger.error("Error: User not found or not active.");
			response.put("mensaje", "User not found or not active.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "The user has been desactivated.");
		response.put("user", userService.updateStatus(user, false));
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
