package br.com.udemycourse.demo.Services;

import br.com.udemycourse.demo.Exceptions.ResourceNotFoundException;
import br.com.udemycourse.demo.Models.Person;
import br.com.udemycourse.demo.data.vo.v1.PersonVO;
import br.com.udemycourse.demo.data.vo.v2.PersonVOV2;
import br.com.udemycourse.demo.mapper.ModelMapper;
import br.com.udemycourse.demo.mapper.custom.PersonMapper;
import br.com.udemycourse.demo.repositories.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepo repository;

    @Autowired
    private PersonMapper personMapper;

    public List<PersonVO> findAll() {

        return ModelMapper.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {
        logger.info("finding one person");

        return ModelMapper.parseObject(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records Found for this Id")), PersonVO.class);
    }

    public PersonVO create(PersonVO person){
        logger.info("Creating One PersonVO");
        Person personTransformed = ModelMapper.parseObject(person, Person.class);
        return ModelMapper.parseObject(repository.save(personTransformed), PersonVO.class);
    }

    public PersonVO update(PersonVO person){
        logger.info("Creating One PersonVO");

        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records Found for this Id"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        
        return ModelMapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public void delete(Long id){
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records Found for this Id"));
        repository.delete(entity);
    }


    public PersonVOV2 createV2(PersonVOV2 personVOV2) {
        logger.info("Creating one person with V2");
        Person personTransformed = personMapper.ConvertVoToEntity(personVOV2);
        return personMapper.convertEntityToVo(repository.save(personTransformed));
    }
}
