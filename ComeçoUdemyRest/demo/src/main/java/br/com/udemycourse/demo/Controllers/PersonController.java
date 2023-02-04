package br.com.udemycourse.demo.Controllers;

import br.com.udemycourse.demo.Services.PersonServices;
import br.com.udemycourse.demo.data.vo.v1.PersonVO;
import br.com.udemycourse.demo.data.vo.v2.PersonVOV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping(value = "/{id}", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" })
    public PersonVO findById(@PathVariable(value = "id") Long id) {
        return personServices.findById(id);
    }

    @GetMapping(value = "/all", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE , "application/x-yaml" })
    public List<PersonVO> findById() {
        return personServices.findAll();
    }

    @PostMapping(value = "/create",
            produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" }, consumes = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" }
    )
    public PersonVO create(@RequestBody PersonVO person) {
        return personServices.create(person);
    }

    @PostMapping(value = "/v2/create", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" }, consumes = {
            APPLICATION_JSON_VALUE, "application/x-yaml", APPLICATION_XML_VALUE })
    public PersonVOV2 createV2(@RequestBody PersonVOV2 personVOV2){
        return personServices.createV2(personVOV2);
    }


    @PutMapping(value = "/update",
            produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" },
            consumes = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" }
    )
    public PersonVO update(@RequestBody(required = false) PersonVO person) {
        return personServices.update(person);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        personServices.delete(id);
    }


}
