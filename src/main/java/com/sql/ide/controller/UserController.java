package com.sql.ide.controller;

import com.sql.ide.domain.DataSourceRequest;

import com.sql.ide.domain.QueryRequest;
import com.sql.ide.services.ConnectionService;
import com.sql.ide.services.QueryService;
import com.sql.ide.services.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/user")
public class UserController {

	private Logger logger = LogManager.getLogger(QueryController.class);

	@Autowired
	UserService userService;

	@GetMapping(path = "/connections")
	public ResponseEntity<Object> fetchUserConnections(@RequestHeader("user") String username) throws Exception {
		logger.info("Fetching connections of User: START");

		return new ResponseEntity<>(userService.fetchUserConnections(username), HttpStatus.OK);
	}
}
