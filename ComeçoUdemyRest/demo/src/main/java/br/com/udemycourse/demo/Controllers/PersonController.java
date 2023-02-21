package br.com.udemycourse.demo.Controllers;

import br.com.udemycourse.demo.Services.PersonServices;
import br.com.udemycourse.demo.data.vo.v1.PersonVO;
import br.com.udemycourse.demo.data.vo.v2.PersonVOV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

//@CrossOrigin
@RestController
@RequestMapping("/person")
@Tag(name = "People", description = "Endpoints for threating People")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value = "/{id}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml"})
    @Operation(summary = "Finds a person",
            description = "Finds a person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = PersonVO.class))
                    }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public PersonVO findById(@PathVariable(value = "id") Long id) {
        return personServices.findById(id);
    }

    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml"})
    @Operation(summary = "Finds all People",
            description = "Finds all People",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                            )
                    }),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public List<PersonVO> findById() {
        return personServices.findAll();
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://erudio.com.br"})
    @PostMapping(value = "/create",
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml"}, consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml"}
    )
    @Operation(summary = "Adds a new person",
            description = "Adds a person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = PersonVO.class))
                    }),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public PersonVO create(@RequestBody PersonVO person) {
        return personServices.create(person);
    }

    @PostMapping(value = "/v2/create", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml"}, consumes = {
            APPLICATION_JSON_VALUE, "application/x-yaml", APPLICATION_XML_VALUE})
    public PersonVOV2 createV2(@RequestBody PersonVOV2 personVOV2) {
        return personServices.createV2(personVOV2);
    }


    @PutMapping(value = "/update",
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml"},
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml"}
    )
    @Operation(summary = "updates a person",
            description = "updates a person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = PersonVO.class))
                    }),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public PersonVO update(@RequestBody(required = false) PersonVO person) {
        return personServices.update(person);
    }

    @PatchMapping(value = "/disable-person/{id}")
    @Operation(summary = "Disables a person",
            description = "Disables a person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity disablePerson(@PathVariable(value = "id") Long id) {
        try{
            personServices.disablePerson(id);
            return ResponseEntity.ok("Pessoa desabilitada com sucesso.");
        } catch (Exception e){
            return ResponseEntity.status(400).body("Falha ao modificar a pessoa");
        }
    }
    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "deletes a person",
            description = "deletes a person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity delete(@PathVariable(value = "id") Long id) {
        personServices.delete(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
