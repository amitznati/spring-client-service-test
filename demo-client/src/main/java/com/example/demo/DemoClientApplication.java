package com.example.demo;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
class Library {

	private Long id;
	private String name;
	private List<Book> books;
	
}

@Data
@RequiredArgsConstructor
@AllArgsConstructor
class Book {

	private Long id;
	private String name;
	private Library library;
	
}



@RestController
class librarybookController {

//	@Bean
//	public ObjectMapper getObjectMapperWithHalModule() {
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.registerModule(new Jackson2HalModule());
//		return objectMapper;
//	}
	
	@Autowired
	RestTemplate restTemplate;
	
	String bookUrl = "http://localhost:8401/books";
	String libraryUrl = "http://localhost:8401/libraries";
	
	@PostMapping("/api/books")
	public String addbook(@RequestBody Book book,	@RequestParam("library_id") int libraryId){
		Book retVal = null;
		URI uri = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new Jackson2HalModule());
			ObjectNode jsonNodebook = (ObjectNode) objectMapper.valueToTree(book);
			jsonNodebook.put("library", libraryUrl+"/"+libraryId);
			uri = restTemplate.postForLocation(bookUrl, jsonNodebook);
//			retVal = restTemplate.getForObject(uri, Book.class);
//			retVal.setLibrary(restTemplate.getForObject(libraryUrl+"/"+libraryId, Library.class));
		}catch (Exception e) {
			throw e;
		}
		return uri.toString();
	}
}
