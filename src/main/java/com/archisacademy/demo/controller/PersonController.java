package com.archisacademy.demo.controller;

import com.archisacademy.demo.model.Person;
import com.archisacademy.demo.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    @GetMapping
    public List<Person> getAllPersons(){
        return personService.getAll();
    }

    @GetMapping("/{email}")
    public Person getPersonByEmail(@PathVariable String email){
        return personService.getByEmail(email);
    }
    @PostMapping
    public String save(@RequestBody Person person){
        return personService.create(person);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody String email) {
        return personService.forgotPassword(email);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody String requestBody) {
        // Taking only one parameter without "dto" causes more difficult method. This is not recommended.
        String[] parts = requestBody.split(",");
        String email = parts[0].replaceAll("\"", "").trim();
        String resetToken = parts[1].replaceAll("\"", "").trim();
        String newPassword = parts[2].replaceAll("\"", "").trim();
        return personService.resetPassword(email, resetToken, newPassword);
    }
}
