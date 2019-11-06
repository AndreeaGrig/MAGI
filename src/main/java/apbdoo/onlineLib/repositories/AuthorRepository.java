package apbdoo.onlineLib.repositories;

import apbdoo.onlineLib.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    boolean existsById(Long id);
}
