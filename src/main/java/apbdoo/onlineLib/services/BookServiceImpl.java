package apbdoo.onlineLib.services;

import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.domain.Category;
import apbdoo.onlineLib.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
            this.bookRepository = bookRepository;
        }

        @Override
        public Set<Book> getBooks() {
            log.info("Get all books sorted by add date descending!");
            Set<Book> books = new HashSet<Book>();
            bookRepository.findAll(Sort.by("addDate").descending()).iterator().forEachRemaining(books::add);
            books.forEach(book -> log.info(book.getTitle()));
            return books;
        }

        @Override
        public Book findBookById(Long l) {

            Optional<Book> bookOptional = bookRepository.findById(l);

            if (!bookOptional.isPresent()) {
                log.error("Book id not found: " + l);
                throw new RuntimeException("Book not found!");
            }

            return bookOptional.get();
        }


        @Override
        public Book saveBook(Book book) {
            Book savedBook = bookRepository.save(book);
            log.info("Saving book '"+savedBook.getTitle()+"' into database");
            return savedBook;
        }

        @Override
        public void deleteBookById(Long id) {
        log.info("Deleting book with id " + id + "from database");
        bookRepository.deleteById(id);
        }

    @Override
    public Page<Book> getPage(Pageable pageable){
        log.info("Display books from all categories.");
        Page<Book> bookPage = bookRepository.findAll(pageable);
        bookPage.forEach(book -> log.info(book.getTitle()));
        return bookPage;
    }

    @Override
    public Page<Book> getCategoryPage(String category, Pageable pageable){
        log.info("Display books from category "+category);
        List<Book> books = bookRepository.findAllByCategory(Category.valueOf(category), pageable);
        Page<Book> bookPage =  new PageImpl<>(books, pageable, books.size());
        bookPage.forEach(book->log.info(book.getTitle()));
        return bookPage;
    }
}

