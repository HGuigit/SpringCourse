package br.com.udemycourse.demo.Services;

import br.com.udemycourse.demo.Controllers.PersonController;
import br.com.udemycourse.demo.Exceptions.RequiredObjectIsNullException;
import br.com.udemycourse.demo.Exceptions.ResourceNotFoundException;
import br.com.udemycourse.demo.Models.Person;
import br.com.udemycourse.demo.data.vo.v1.PersonVO;
import br.com.udemycourse.demo.data.vo.v2.PersonVOV2;
import br.com.udemycourse.demo.mapper.ModelMapper;
import br.com.udemycourse.demo.mapper.custom.PersonMapper;
import br.com.udemycourse.demo.repositories.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepo repository;

    @Autowired
    PagedResourcesAssembler<PersonVO> assembler;

    @Autowired
    private PersonMapper personMapper;

    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {

        var personPage = repository.findAll(pageable);

        var personVosPage = personPage.map(p -> ModelMapper.parseObject(p, PersonVO.class));
        personVosPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(),"asc")).withSelfRel();
       return assembler.toModel(personVosPage, link);
    }

    public PagedModel<EntityModel<PersonVO>> findPersonByName(String firstName, Pageable pageable) {

        var personPage = repository.findPersonByName(firstName, pageable);

        var personVosPage = personPage.map(p -> ModelMapper.parseObject(p, PersonVO.class));
        personVosPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(),"asc")).withSelfRel();
        return assembler.toModel(personVosPage, link);
    }


    public PersonVO findById(Long id) {
        logger.info("finding one person");

        PersonVO vo =  ModelMapper.parseObject(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records Found for this Id")), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person){
        if (person == null) throw new RequiredObjectIsNullException();
        logger.info("Creating One PersonVO");
        Person personTransformed = ModelMapper.parseObject(person, Person.class);
        PersonVO vo = ModelMapper.parseObject(repository.save(personTransformed), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person){
        System.out.println(person);
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating One PersonVO");

        Person entity = repository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records Found for this Id"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        
        PersonVO vo =  ModelMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id){
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records Found for this Id"));
        repository.delete(entity);
    }

    @Transactional
    public void disablePerson(Long id) {
        logger.info("disabling person with id: " + id);

        repository.disablePerson(id);
    }



    public PersonVOV2 createV2(PersonVOV2 personVOV2) {
        logger.info("Creating one person with V2");
        Person personTransformed = personMapper.ConvertVoToEntity(personVOV2);
        return personMapper.convertEntityToVo(repository.save(personTransformed));
    }
}
