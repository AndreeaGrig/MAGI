package apbdoo.onlineLib.service;


import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.repositories.BookRepository;
import apbdoo.onlineLib.services.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {
    public static final String TITLE = "My book's title";

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Test
    public void testSaveBook(){
        Optional<Book> optBook = bookRepository.findById(1L);
        Book book = null;
        if(optBook.isPresent())
             book = optBook.get();
        if(book!=null)
            book.setTitle("My book's title");
        else
        {
            book = new Book();
            book.setTitle("My book's title");
            book.setId(1L);
        }

        Book savedBook;
        savedBook = bookService.saveBook(book);

        assertEquals(TITLE, savedBook.getTitle());
    }


}
