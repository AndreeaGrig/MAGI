package apbdoo.onlineLib.controllers;

import apbdoo.onlineLib.domain.*;
import apbdoo.onlineLib.services.AuthorService;
import apbdoo.onlineLib.services.BookService;
import apbdoo.onlineLib.services.PubHouseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class AddBookController {


    private final BookService bookService;
    private final AuthorService authorService;
    private final PubHouseService pubHouseService;

    public AddBookController(BookService bookService, AuthorService authorService, PubHouseService pubHouseService)
    {
        this.bookService = bookService;
        this.authorService = authorService;
        this.pubHouseService = pubHouseService;
    }

    @RequestMapping("/book/{id}/edit")
    public String editBookDetails(@PathVariable String id, Model model){

        model.addAttribute("book", bookService.findBookById(new Long(id)));

        return "addbook";
    }

    @ModelAttribute("allPubHouses")
    public Set<PubHouse> populatePubHouses() {
        return pubHouseService.getPubHouses();
    }

    @ModelAttribute("allAuthors")
    public Set<Author> populateAuthors() {
        return authorService.getAuthors();
    }

    @ModelAttribute("allCategories")
    public List<Category> populateCategories() {
        return Arrays.asList(Category.values());
    }

//    @ModelAttribute("book")
//    public Book addBook() {
//        return new Book();
//    }

    @GetMapping("/book/add")
    public String showBookForm(Model model) {
        Book book = new Book();
        model.addAttribute("book",book);
        return "addbook";
    }

    @PostMapping("/book/add")
    public String saveOrUpdate(@ModelAttribute("book") @Valid Book book,
                               BindingResult bindingResult,
                               @RequestParam(value = "selectedAuthors" , required = false) int[] selectedAuthors
                               ){


        if(selectedAuthors != null) {
            Author author = null ;
            for (int i = 0; i < selectedAuthors.length; i++) {

                if(authorService.existsById((long) selectedAuthors[i])) {
                    author = new Author();
                    author.setId(selectedAuthors[i]);
                    book.addAuthor(author);
                }
            }
        }
        else{
            bindingResult.addError(new FieldError("book", "authors", "Required field!"));
        }

        if(book.getBookInfo().getInfo()==null){
            bindingResult.addError(new FieldError("book", "bookInfo", "Required field!"));
        }
        else{
            String info = book.getBookInfo().getInfo().trim();
            if(info.equals(""))
                bindingResult.addError(new FieldError("book", "bookInfo", "Required field!"));
        }

        book.setAddDate(new Date());

        if (bindingResult.hasErrors()) {
            return "addbook";
        }
        Book savedCommand = bookService.saveBook(book);
        if(book.getUrlPdf()==null || book.getUrlPdf().equals(""))
            return "redirect:/book/"+book.getId()+"/image" ;
        else
            return "redirect:/book/details/"+book.getId();
    }
}
