package apbdoo.onlineLib.services;

import apbdoo.onlineLib.domain.Author;
import apbdoo.onlineLib.repositories.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {
    AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Set<Author> getAuthors() {
        log.info("Retrieving list of authors:");
        Set<Author> authors = new HashSet<Author>();
        authorRepository.findAll().iterator().forEachRemaining(authors::add);
        authors.forEach(author -> log.info(author.getFirstLastName()));
        return authors;
    }

    @Override
    public Boolean existsById(Long l){
        return authorRepository.existsById(l);
    }

}
