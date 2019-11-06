package apbdoo.onlineLib.services;
import apbdoo.onlineLib.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface BookService{
    Set<Book> getBooks();

    Book findBookById(Long l);

    Book saveBook(Book book);

    void deleteBookById(Long id);

    public Page<Book> getPage(Pageable pageable);

    Page<Book> getCategoryPage(String category, Pageable pageable);
}