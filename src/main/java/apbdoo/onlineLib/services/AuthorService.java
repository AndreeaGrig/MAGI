package apbdoo.onlineLib.services;

import apbdoo.onlineLib.domain.Author;

import java.util.Set;

public interface AuthorService {
    Set<Author> getAuthors();
    Boolean existsById(Long id);
}
