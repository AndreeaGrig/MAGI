package apbdoo.onlineLib.controllers;

import apbdoo.onlineLib.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    private final BookService bookService;

    public IndexController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/accessDenied")
    public String acessDenied(){
        return "access-denied";

    }
//    @RequestMapping({"", "/"})
//    public String getIndexPage(Model model){
//        model.addAttribute("books", bookService.getBooks());
//        return "index";
//    }

}
