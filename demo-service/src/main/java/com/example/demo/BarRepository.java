package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "bars", path = "bars")
public interface BarRepository extends JpaRepository<Bar, Long> {

}
