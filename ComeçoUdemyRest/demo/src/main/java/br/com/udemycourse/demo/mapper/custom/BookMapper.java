package br.com.udemycourse.demo.mapper.custom;

import br.com.udemycourse.demo.Models.Book;
import br.com.udemycourse.demo.data.vo.v1.BookVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookMapper {

    public static BookVO convertEntityToVo(Book book){
        BookVO vo = new BookVO();
        vo.setAuthor(book.getAuthor());
        vo.setKey(book.getId());
        vo.setPrice(book.getPrice());
        vo.setLaunchDate(book.getLaunchDate());
        vo.setTitle(book.getTitle());
        return vo;
    }

    public static Book convertVoToEntity(BookVO bookVO){
        Book book = new Book();
        book.setAuthor(bookVO.getAuthor());
        book.setId(bookVO.getKey());
        book.setPrice(bookVO.getPrice());
        book.setLaunchDate(bookVO.getLaunchDate());
        book.setTitle(bookVO.getTitle());
        return book;
    }

    public static List<BookVO> convertListEntityToListVO(List<Book> listBook){
        return listBook.stream().map(book -> convertEntityToVo(book)).collect(Collectors.toList());
    }
}
