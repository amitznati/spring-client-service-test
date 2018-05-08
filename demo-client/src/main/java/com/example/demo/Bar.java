package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Bar {

	private Long id;
	private String name;
	private Foo foo;
	
}
