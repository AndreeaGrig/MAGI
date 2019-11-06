package apbdoo.onlineLib.controllers;

import apbdoo.onlineLib.domain.Author;
import apbdoo.onlineLib.services.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Set;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ModelAttribute("allAuthors")
    public Set<Author> populateAuthors() {
        return authorService.getAuthors();
    }
}
