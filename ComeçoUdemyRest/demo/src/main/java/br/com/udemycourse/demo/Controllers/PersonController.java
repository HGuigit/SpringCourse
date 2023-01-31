package br.com.udemycourse.demo.Controllers;


import br.com.udemycourse.demo.Models.Person;
import br.com.udemycourse.demo.Services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById(@PathVariable(value = "id") Long id) {
        return personServices.findById(id);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findById() {
        return personServices.findAll();
    }

    @PostMapping(value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes =
            MediaType.APPLICATION_JSON_VALUE
    )
    public Person create(@RequestBody Person person) {
        return personServices.create(person);
    }

    @PutMapping(value = "/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Person update(@RequestBody Person person) {
        return personServices.update(person);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable(value = "id") Long id){
        personServices.delete(id);
    }


}
