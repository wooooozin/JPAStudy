package com.example.jpa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecondController {

	@RequestMapping(value = "hello-spring", method = RequestMethod.GET)
	public String helloSpring() {
		return "Hello Spring";
	}

	@GetMapping("hello-rest")
	public String helloRest() {
		return "Hello Rest";
	}

	@GetMapping("/api/helloworld")
	public String helloRestApi() {
		return "Hello REST API";

	}
}
