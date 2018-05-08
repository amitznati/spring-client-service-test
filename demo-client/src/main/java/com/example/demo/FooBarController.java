package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FooBarController {

	@Autowired
	RestTemplate restTemplate;
	
	@PostMapping("/api/bars")
	public Bar addBar(@RequestBody Bar bar,
			@RequestParam("foo_id") int driverId){
		Bar retVal = null;
		String url = "http://localhost:8401/bars";
		try {
			retVal = restTemplate.postForObject(url, bar, Bar.class);
			
		}catch (Exception e) {
			throw e;
		}
		return retVal;
	}
}