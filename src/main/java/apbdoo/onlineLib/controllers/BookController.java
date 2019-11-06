package apbdoo.onlineLib.controllers;

import apbdoo.onlineLib.domain.Author;
import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.domain.Category;
import apbdoo.onlineLib.domain.PubHouse;
import apbdoo.onlineLib.services.AuthorService;
import apbdoo.onlineLib.services.BookService;
import apbdoo.onlineLib.services.PubHouseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService)
    {
        this.bookService = bookService;
    }

    @ModelAttribute("rbooks")
    public List<Book> populateRecommended() {
         Set<Book> books = bookService.getBooks();
         List<Book> rec = new ArrayList<>(books);
         List<Book> sortedBooks = books.stream()
                .sorted(Comparator.comparing(Book::getNoOfFavs).reversed())
                .collect(Collectors.toList()).subList(0,6);
         return sortedBooks;
    }

    @RequestMapping("/book/details/{id}")
    public String showBookById(@PathVariable String id, Model model){

        model.addAttribute("book", bookService.findBookById(new Long(id)));

        return "bookDetails";
    }

    @GetMapping
    @RequestMapping("book/{id}/delete")
    public String deleteBookById(@PathVariable String id){

        bookService.deleteBookById(Long.valueOf(id));
        return "redirect:/";
    }

    @RequestMapping("/books/{category}")
    public String showBooksByCategory(@PathVariable String category, Model model){

        model.addAttribute("books", bookService.getBooks());

        return "books";
    }


}
