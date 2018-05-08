package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.rest.core.annotation.RestResource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Bar {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@OneToOne
    @JoinColumn(name = "foo_id")
    @RestResource(path = "barFoo", rel="foo")
	private Foo foo;
	
}
