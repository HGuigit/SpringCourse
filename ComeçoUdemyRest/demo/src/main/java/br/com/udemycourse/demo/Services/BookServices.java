package br.com.udemycourse.demo.Services;


import br.com.udemycourse.demo.Controllers.BookController;
import br.com.udemycourse.demo.Exceptions.RequiredObjectIsNullException;
import br.com.udemycourse.demo.Exceptions.ResourceNotFoundException;
import br.com.udemycourse.demo.Models.Book;
import br.com.udemycourse.demo.data.vo.v1.BookVO;
import br.com.udemycourse.demo.mapper.custom.BookMapper;
import br.com.udemycourse.demo.repositories.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    @Autowired
    private BookRepo repository;

    public BookVO findById(Integer id) {
        BookVO vo  = BookMapper.convertEntityToVo(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Este livro não existe!")));
        vo.add(linkTo(methodOn(BookController.class).findBookById(id)).withSelfRel());
        return vo;
    }

    public List<BookVO> findAll(){
        List<BookVO> listBooks =  BookMapper.convertListEntityToListVO(repository.findAll());
        listBooks.stream().forEach(b -> b.add(linkTo(methodOn(BookController.class).findBookById(b.getKey())).withSelfRel()));
        return listBooks;
    }

    public BookVO create(BookVO book){
        if (book == null) throw new RequiredObjectIsNullException();
        Book bookTransformed = BookMapper.convertVoToEntity(book);
        BookVO vo = BookMapper.convertEntityToVo(repository.save(bookTransformed));
        vo.add(linkTo(methodOn(BookController.class).findBookById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book){
        if (book == null) throw new RequiredObjectIsNullException();
        Book oldBook = repository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("Este livro não existe!"));
        oldBook.setTitle(book.getTitle());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setLaunchDate(book.getLaunchDate());
        oldBook.setPrice(book.getPrice());
        BookVO vo = BookMapper.convertEntityToVo(repository.save(oldBook));
        vo.add(linkTo(methodOn(BookController.class).findBookById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Integer id){
        Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Este livro não existe!"));
        repository.delete(entity);
    }

}
