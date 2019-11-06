package apbdoo.onlineLib.repositories;

import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.domain.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    List<Book> findAllByCategory(Category category, Pageable pageable);
}
