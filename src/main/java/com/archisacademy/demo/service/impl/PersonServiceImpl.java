package com.archisacademy.demo.service.impl;

import com.archisacademy.demo.model.Person;
import com.archisacademy.demo.repository.PersonRepository;
import com.archisacademy.demo.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAll(){
        return personRepository.findAll();
    }

    @Override
    public String create(Person person){
        String response = "Person is not found.";
        if(person!=null){
            personRepository.save(person);
            response = "Person successfully saved.";
        }
        return response;
    }

    @Override
    public String update(int id, Person person){
        String response = "Person is not found.";
        Optional<Person> personFromDb = personRepository.findById(id);
        if(personFromDb.isPresent()){
           personFromDb.get().setEmail(person.getEmail());
           personFromDb.get().setName(person.getName());
           personRepository.save(personFromDb.get());
           response = "Person successfully updated.";
        }
        return response;
    }

    @Override
    public String delete(int id){
        String response = "Person is not found.";
        Optional<Person> personFromDb = personRepository.findById(id);
        if(personFromDb.isPresent()) {
            personRepository.deleteById(id);
            response = "Person successfully deleted.";
        }
        return response;
    }

    @Override
    public Person getByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    private String generateOneTimeToken(){
        return UUID.randomUUID().toString();
    }

    @Override
    public String forgotPassword(String email){
        Person person = personRepository.findByEmail(email);
        if (person == null) {
            return "Person not found.";
        }

        String resetToken = generateOneTimeToken();
        person.setOneTimeToken(resetToken);
        personRepository.save(person);

        // sendResetPasswordEmail(person.getEmail(), resetToken);
        // The link will direct to "resetPassword" function.

        return "Password reset link sent to your email address.";
    }

    @Override
    public String resetPassword(String email, String resetToken, String newPassword){
        Person person = personRepository.findByEmail(email);
        if (person == null) {
            return "Person not found.";
        }

        if (!resetToken.equals(person.getOneTimeToken())) {
            return "Invalid password reset link";
        }

        person.setPassword(newPassword);
        person.setOneTimeToken(null);
        personRepository.save(person);

        return "Password changed successfully.";
    }

}
