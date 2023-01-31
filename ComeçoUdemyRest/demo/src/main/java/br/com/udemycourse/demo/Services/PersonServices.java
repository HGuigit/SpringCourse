package br.com.udemycourse.demo.Services;

import br.com.udemycourse.demo.Exceptions.ResourceNotFoundException;
import br.com.udemycourse.demo.Models.Person;
import br.com.udemycourse.demo.repositories.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepo repository;

    public List<Person> findAll() {

        return repository.findAll();
    }

    public Person findById(Long id) {
        logger.info("finding one person");

        Person person = new Person();

        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records Found for this Id"));
    }

    public Person create(Person person){
        logger.info("Creating One Person");
        return repository.save(person);
    }

    public Person update(Person person){
        logger.info("Creating One Person");

        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records Found for this Id"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        
        return repository.save(entity);
    }

    public void delete(Long id){
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records Found for this Id"));
        repository.delete(entity);
    }


}
