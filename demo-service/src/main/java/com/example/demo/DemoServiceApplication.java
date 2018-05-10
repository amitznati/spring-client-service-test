package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
public class DemoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoServiceApplication.class, args);
	}
	
	
}

@Component
class ExposeEntityIdRestMvcConfiguration extends RepositoryRestConfigurerAdapter {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.exposeIdsFor(Bar.class);
    config.exposeIdsFor(Foo.class);
  }
}

@RepositoryRestResource(collectionResourceRel = "bars", path = "bars")
interface BarRepository extends JpaRepository<Bar, Long> {

}

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
class Bar {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@OneToOne
    @JoinColumn(name = "foo_id")
    @RestResource(path = "barFoo", rel="foo")
	private Foo foo;
	
}

@RepositoryRestResource(collectionResourceRel = "foos", path = "foos")
interface FooRepository extends JpaRepository<Foo, Long> {

}

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
class Foo {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@OneToOne(mappedBy = "foo")
	private Bar bar;
}






