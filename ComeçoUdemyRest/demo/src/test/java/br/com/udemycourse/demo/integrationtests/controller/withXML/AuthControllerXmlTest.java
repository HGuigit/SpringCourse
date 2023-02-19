package br.com.udemycourse.demo.integrationtests.controller.withJson;

import br.com.udemycourse.demo.configs.TestConfigs;
import br.com.udemycourse.demo.data.vo.v1.security.AccountCredentialVO;
import br.com.udemycourse.demo.data.vo.v1.security.TokenVO;
import br.com.udemycourse.demo.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerXmlTest extends AbstractIntegrationTest {

    private static TokenVO tokenVO;

    @Test
    @Order(1)
    public void testSignIn() throws JsonMappingException, JsonProcessingException {
        AccountCredentialVO user = new AccountCredentialVO("guilherme_souza", "admin123");

        tokenVO = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath().getObject("body", TokenVO.class);

        assertNotNull(tokenVO.getAccessToken());
        assertNotNull(tokenVO.getRefreshToken());

    }

    @Test
    @Order(2)
    public void testRefreshToken() throws JsonMappingException, JsonProcessingException {
        AccountCredentialVO user = new AccountCredentialVO("guilherme_souza", "admin123");

        var newTokenVO = given()
                .basePath("/auth/refresh/")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
                .pathParams("username", user.getUsername())
                .when()
                .put("{username}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath().getObject("body", TokenVO.class);

        assertNotNull(newTokenVO.getAccessToken());
        assertNotNull(newTokenVO.getRefreshToken());

    }



}
