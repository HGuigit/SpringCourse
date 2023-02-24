package br.com.udemycourse.demo.integrationtests.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class PersonEmbeddedVO implements Serializable {

    @JsonProperty("personVOList")
    private List<PersonVO> persons;

    public List<PersonVO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonVO> persons) {
        this.persons = persons;
    }
}
