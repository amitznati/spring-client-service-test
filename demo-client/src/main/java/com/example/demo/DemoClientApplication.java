package com.example.demo;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

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

@RestController
class librarybookController {
	
	@Autowired
	RestTemplate restTemplate;
	
	String bookUrl = "http://localhost:8401/books";
	String libraryUrl = "http://localhost:8401/libraries";
	
	@PostMapping("/api/books")
	public String addbook(@RequestBody ObjectNode book,	@RequestParam("library_id") int libraryId){
		URI uri = null;
		try {
			book.put("library", libraryUrl+"/"+libraryId);
			uri = restTemplate.postForLocation(bookUrl, book);
		}catch (Exception e) {
			throw e;
		}
		return uri.toString();
	}
}
