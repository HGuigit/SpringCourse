package br.com.udemycourse.demo.Services;

import br.com.udemycourse.demo.Exceptions.RequiredObjectIsNullException;
import br.com.udemycourse.demo.Models.Person;
import br.com.udemycourse.demo.data.vo.v1.PersonVO;
import br.com.udemycourse.demo.mocks.MockPerson;
import br.com.udemycourse.demo.repositories.PersonRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices services;

    @Mock
    PersonRepo repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    /*
    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var people = services.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.get(1);

        assertNotNull(personOne);
        assertNotNull(personOne.getKey());
        assertNotNull(personOne.getLinks());

        assertTrue(personOne.toString().contains("links: [</person/1>;rel=\"self\"]"));
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Addres Test1", personOne.getAddress());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());

        var personFour = people.get(4);

        assertNotNull(personFour);
        assertNotNull(personFour.getKey());
        assertNotNull(personFour.getLinks());

        assertTrue(personFour.toString().contains("links: [</person/4>;rel=\"self\"]"));
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Addres Test4", personFour.getAddress());
        assertEquals("Last Name Test4", personFour.getLastName());
        assertEquals("Male", personFour.getGender());

    }
 */
    @Test
    void findById() {
        Person person = input.mockEntity();
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        var result = services.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</person/1>;rel=\"self\"]"));
        assertEquals("First Name Test0", result.getFirstName());
        assertEquals("Addres Test0", result.getAddress());
        assertEquals("Last Name Test0", result.getLastName());
        assertEquals("Male", result.getGender());
    }

    /*
    @Test
    void create() {
        Person person = input.mockEntity(1);

        Person persistedPerson = person;
        persistedPerson.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(person)).thenReturn(persistedPerson);

        var result = services.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</person/create>;rel=\"self\"]"));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }
    */

    @Test
    void update() {
        Person person = input.mockEntity(1);

        Person persisted = person;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);


        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(persisted);

        var result = services.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</person/" + 1L + ">;rel=\"self\"]"));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            services.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void delete() {
    }
}