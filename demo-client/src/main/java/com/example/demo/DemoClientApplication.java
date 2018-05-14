package com.example.demo;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
	
	
}

@Data
@RequiredArgsConstructor
@AllArgsConstructor
class Book {

	private Long id;
	private String name;
	private Library library;
	
}

@Data
@RequiredArgsConstructor
@AllArgsConstructor
class Library {

	private Long id;
	private String name;
	private List<Book> books;
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
	
	@GetMapping("api/libraries")
	public Collection<Library> getLibraries() {
		Collection<Library> libraries = Collections.emptyList();
		try {
			libraries = restTemplate.exchange(
					libraryUrl, 
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<Resources<Library>>() {})
					.getBody().getContent().stream().collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(libraries);
		
		return libraries;
	}
	
}
