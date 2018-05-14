package com.example.demo;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
public class DemoServiceApplication {

	@Autowired
	BookRepository br;
	
	@Autowired
	LibraryRepository fr;
	
	@Bean
	CommandLineRunner runner(){
		return args -> {
			addData();
			
		};
	}
	
	private void addData() {
		Library l = new Library(null,"library 1",null);
		fr.save(l);
		br.save(new Book(null,"book 1",l));
		
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoServiceApplication.class, args);
	}
	
	
}

@Component
class ExposeEntityIdRestMvcConfiguration extends RepositoryRestConfigurerAdapter {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.exposeIdsFor(Book.class);
    config.exposeIdsFor(Library.class);
  }
}

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
interface BookRepository extends JpaRepository<Book, Long> {

}

@Embeddable
@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne()
    @JoinColumn(name = "library_id")
	private Library library;
	
}

@RepositoryRestResource(collectionResourceRel = "libraries", path = "libraries")
interface LibraryRepository extends JpaRepository<Library, Long> {

}

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
class Library {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@OneToMany(mappedBy = "library",fetch = FetchType.EAGER)
	private List<Book> books;
	
	@Transient
	public List<Book> getBooks(){
		return this.books;
	}
}






