package br.com.udemycourse.demo.Controllers;

import br.com.udemycourse.demo.Services.BookServices;
import br.com.udemycourse.demo.data.vo.v1.BookVO;
import br.com.udemycourse.demo.data.vo.v1.PersonVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequestMapping("/book")
@Tag(name = "Books", description = "Endpoints for threating books")
public class BookController {

    @Autowired
    private BookServices bookServices;


    @GetMapping(value = "/{id}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @Operation(summary = "Finds a book",
            description = "Finds a book",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = BookVO.class))
                    }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public BookVO findBookById(@PathVariable(value = "id") Integer id) {
        return bookServices.findById(id);
    }

    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @Operation(summary = "Finds all books",
            description = "Finds all books",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
                            )
                    }),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unathorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public List<BookVO> findAllBooks() {
        return bookServices.findAll();
    }

    @PostMapping(value = "/create", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public BookVO create(@RequestBody BookVO book) {
        return bookServices.create(book);
    }

    @PutMapping(value = "/update", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public BookVO update(@RequestBody BookVO book) {
        return bookServices.update(book);
    }

    @DeleteMapping(value = "/delete/{id}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void delete(@PathVariable(value = "id") Integer id) {
        bookServices.delete(id);
    }
}
