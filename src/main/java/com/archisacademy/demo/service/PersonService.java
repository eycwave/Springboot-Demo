package com.archisacademy.demo.service;

import com.archisacademy.demo.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> getAll();
    String create(Person person);
    String update(int id, Person person);
    String delete(int id);
    Person getByEmail(String email);
    String forgotPassword(String email);
    String resetPassword(String email, String resetToken, String password);
}
