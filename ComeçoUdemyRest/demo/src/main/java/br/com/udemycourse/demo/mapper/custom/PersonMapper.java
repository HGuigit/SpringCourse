package br.com.udemycourse.demo.mapper.custom;

import br.com.udemycourse.demo.Models.Person;
import br.com.udemycourse.demo.data.vo.v2.PersonVOV2;
import org.springframework.stereotype.Service;

import java.util.Date;

/*
@author Guilherme on 31/01/2023.
@project demo
*/
@Service
public class PersonMapper {

    public PersonVOV2 convertEntityToVo(Person person){
        PersonVOV2 vo = new PersonVOV2();
        vo.setAddress(person.getAddress());
        vo.setId(person.getId());
        vo.setGender(person.getGender());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());
        vo.setBirthDay(new Date());
        return vo;
    }

    public Person ConvertVoToEntity(PersonVOV2 personVOV2){
        Person person = new Person();
        person.setAddress(personVOV2.getAddress());
        person.setId(personVOV2.getId());
        person.setGender(personVOV2.getGender());
        person.setFirstName(personVOV2.getFirstName());
        person.setLastName(personVOV2.getLastName());
        return person;
    }


}
