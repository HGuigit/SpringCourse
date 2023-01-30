package br.com.udemycourse.demo.Services;

import br.com.udemycourse.demo.Models.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public List<Person> findAll() {

        logger.info("finding all people");

        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        }

        return persons;
    }

    public Person findById(String id) {
        logger.info("finding one person");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Guilherme");
        person.setLastName("Souza");
        person.setAdress("São Paulo BR");
        person.setGender("Masculino");

        return person;
    }

    public Person create(Person person){
        logger.info("Creating One Person");

        return person;
    }

    public Person update(Person person){
        logger.info("Creating One Person");

        return person;
    }

    public void delete(String id){
        logger.info("deleting One Person");
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Person Name" + i);
        person.setLastName("Last name" + i);
        person.setAdress("Some adress" + i);
        person.setGender("Masculino");

        return person;
    }


}
