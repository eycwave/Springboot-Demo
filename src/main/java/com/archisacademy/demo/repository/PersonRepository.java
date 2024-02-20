package com.archisacademy.demo.repository;

import com.archisacademy.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person,Integer> {
    // @Query(value = "SELECT person FROM persons where email=?", nativeQuery = true)
    Person findByEmail(String email);
}
