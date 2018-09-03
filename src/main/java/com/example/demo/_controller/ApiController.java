package com.example.demo._controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo._domain.GenericRestResponse;
import com.example.demo._domain.Login;
import com.example.demo.cipher.service.ICipherManager;

import lombok.SneakyThrows;

@RestController
public class ApiController {

	@Autowired
	private ICipherManager cipherManager;

	@RequestMapping(value = "/key", method = RequestMethod.GET)
	public ResponseEntity<GenericRestResponse> key(@RequestParam(value = "appId") String appId) {

		GenericRestResponse.GenericRestResponseBuilder builder = GenericRestResponse.builder();

		if (appId != null && !appId.isEmpty()) {
			builder.data(cipherManager.generatePublicKey(appId));
			builder.code(HttpStatus.OK.value());

			return ResponseEntity.ok().body(builder.build());
		} else {
			builder.data("missing appId");
			builder.code(HttpStatus.BAD_REQUEST.value());

			return ResponseEntity.badRequest().body(builder.build());
		}
	}

	@SneakyThrows
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<GenericRestResponse> login(@RequestBody Login login) {

		GenericRestResponse.GenericRestResponseBuilder builder = GenericRestResponse.builder();

		String username = null;
		String password = null;

		if (login != null) {

			username = cipherManager.decrypt(login.getAppId(), login.getUsername());
			password = cipherManager.decrypt(login.getAppId(), login.getPassword());

			builder.data("username=" + username + ", password=" + password);
			builder.code(HttpStatus.OK.value());

			return ResponseEntity.ok().body(builder.build());

		} else {
			builder.data("missing login data");
			builder.code(HttpStatus.BAD_REQUEST.value());

			return ResponseEntity.badRequest().body(builder.build());
		}

	}

}
