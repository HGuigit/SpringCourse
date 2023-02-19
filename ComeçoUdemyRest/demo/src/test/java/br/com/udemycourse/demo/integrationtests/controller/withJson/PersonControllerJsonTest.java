package br.com.udemycourse.demo.integrationtests.controller.withJson;

import static io.restassured.RestAssured.given;

import br.com.udemycourse.demo.configs.TestConfigs;
import br.com.udemycourse.demo.data.vo.v1.security.AccountCredentialVO;
import br.com.udemycourse.demo.data.vo.v1.security.TokenVO;
import br.com.udemycourse.demo.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.udemycourse.demo.integrationtests.vo.PersonVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest  extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static PersonVO person;

    @BeforeAll
    public static void setup(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonVO();
    }

    @Test
    @Order(0)
    public void authorization() throws JsonMappingException, JsonProcessingException{
        AccountCredentialVO user = new AccountCredentialVO("guilherme_souza", "admin123");

        var accessToken = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath().getObject("body", TokenVO.class)
                .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

    }


    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockPerson();

        var content = given().spec(specification)
                .basePath("/person/create")
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                    .body(person)
                    .when()
                    .post()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();

        PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
        person = createdPerson;

        Assertions.assertNotNull(createdPerson);
        Assertions.assertNotNull(createdPerson.getId());
        Assertions.assertNotNull(createdPerson.getAddress());
        Assertions.assertNotNull(createdPerson.getFirstName());
        Assertions.assertNotNull(createdPerson.getLastName());
        Assertions.assertNotNull(createdPerson.getGender());

        Assertions.assertTrue(createdPerson.getId() > 0);

        Assertions.assertEquals("Richard", createdPerson.getFirstName());
        Assertions.assertEquals("Stallman", createdPerson.getLastName());
        Assertions.assertEquals("New york city", createdPerson.getAddress());
        Assertions.assertEquals("Male", createdPerson.getGender());



    }

    @Test
    @Order(2)
    public void testCreateWithWrongOrigin() throws JsonProcessingException {
        mockPerson();

        var content = given().spec(specification)
                .basePath("/person/create")
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();


        Assertions.assertNotNull(content);
        Assertions.assertEquals(content, "Invalid CORS request");

    }
    @Test
    @Order(3)
    public void testFindById() throws JsonProcessingException {
        mockPerson();

        var content = given().spec(specification)
                .basePath("/person/")
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO )
                .pathParams("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonVO returnedPerson = objectMapper.readValue(content, PersonVO.class);

        Assertions.assertNotNull(returnedPerson);
        Assertions.assertNotNull(returnedPerson.getId());
        Assertions.assertNotNull(returnedPerson.getAddress());
        Assertions.assertNotNull(returnedPerson.getFirstName());
        Assertions.assertNotNull(returnedPerson.getLastName());
        Assertions.assertNotNull(returnedPerson.getGender());

        Assertions.assertEquals(person, returnedPerson);


    }

    @Test
    @Order(4)
    public void testUpdate() throws JsonProcessingException {

        Long oldPersonId = person.getId();
        mockUpdatePerson();

        var content = given().spec(specification)
                .basePath("/person/update")
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .body(person)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonVO updatedPerson = objectMapper.readValue(content, PersonVO.class);
        Assertions.assertNotNull(updatedPerson);
        Assertions.assertNotNull(updatedPerson.getId());
        Assertions.assertNotNull(updatedPerson.getAddress());
        Assertions.assertNotNull(updatedPerson.getFirstName());
        Assertions.assertNotNull(updatedPerson.getLastName());
        Assertions.assertNotNull(updatedPerson.getGender());

        Assertions.assertTrue(updatedPerson.getId() > 0);
        Assertions.assertEquals(oldPersonId, updatedPerson.getId());

        Assertions.assertEquals("Nelson", updatedPerson.getFirstName());
        Assertions.assertEquals("Mandela", updatedPerson.getLastName());
        Assertions.assertEquals("Africa", updatedPerson.getAddress());
        Assertions.assertEquals("Male", updatedPerson.getGender());

    }

    @Test
    @Order(5)
    public void deletePersonTest() throws JsonProcessingException {

        given().spec(specification)
                .basePath("/person/delete")
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO )
                .pathParams("id", person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);

    }

    @Test
    @Order(6)
    public void testFindAll() throws JsonProcessingException {

        var content = given().spec(specification)
                .basePath("/person/all")
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        List<PersonVO> people = objectMapper.readValue(content, new com.fasterxml.jackson.core.type.TypeReference<List<PersonVO>>() {});

        PersonVO personOne = people.get(0);


        Assertions.assertEquals(1, personOne.getId());

        Assertions.assertEquals("Leonardooooo", personOne.getFirstName());
        Assertions.assertEquals("Da Vinci", personOne.getLastName());
        Assertions.assertEquals("São Paulo", personOne.getAddress());
        Assertions.assertEquals("M", personOne.getGender());

        PersonVO personTwo = people.get(1);

        Assertions.assertEquals(2, personTwo.getId());

        Assertions.assertEquals("Guilherme", personTwo.getFirstName());
        Assertions.assertEquals("Souza", personTwo.getLastName());
        Assertions.assertEquals("Rio de Janeiro", personTwo.getAddress());
        Assertions.assertEquals("M", personTwo.getGender());



    }


    private void mockPerson() {
        person.setFirstName("Richard");
        person.setLastName("Stallman");
        person.setAddress("New york city");
        person.setGender("Male");
    }

    private void mockUpdatePerson() {
        person.setFirstName("Nelson");
        person.setLastName("Mandela");
        person.setAddress("Africa");
        person.setGender("Male");
    }

}
