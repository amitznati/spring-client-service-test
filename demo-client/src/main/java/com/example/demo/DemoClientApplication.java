package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
public class DemoClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoClientApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	
}

@Data
@RequiredArgsConstructor
@AllArgsConstructor
class Foo {

	private Long id;
	private String name;
	private Bar bar;
	
}

@Data
@RequiredArgsConstructor
@AllArgsConstructor
class Bar {

	private Long id;
	private String name;
	private Foo foo;
	
}

@RestController
class FooBarController {

	@Autowired
	RestTemplate restTemplate;
	
	String barUrl = "http://localhost:8401/bars";
	String fooUrl = "http://localhost:8401/foos";
	
	@PostMapping("/api/bars")
	public Bar addBar(@RequestBody Bar bar,
			@RequestParam("foo_id") int driverId){
		Bar retVal = null;
		try {
			retVal = restTemplate.postForObject(barUrl, bar, Bar.class);
			
		}catch (Exception e) {
			throw e;
		}
		return retVal;
	}
}
