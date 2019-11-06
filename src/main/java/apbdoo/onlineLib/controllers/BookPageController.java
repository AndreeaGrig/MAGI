package apbdoo.onlineLib.controllers;

import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.domain.Category;
import apbdoo.onlineLib.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class BookPageController {

    @Autowired
    BookService bookService;

    @GetMapping({"/books","/",""})
    public String pageBooks(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size){

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(4);

        Page<Book> bookPage = bookService.getPage(PageRequest.of(currentPage-1, pageSize,  Sort.by("addDate").descending()));
        model.addAttribute("bookPage",bookPage);

        int totalPages = bookPage.getTotalPages();
        if(totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers",pageNumbers);

        }
        return "index";
    }

    @GetMapping({"/books/{category}"})
    public String pageCategoryBooks(
            Model model,
            @PathVariable String category,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size){

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(4);

        Page<Book> bookPage = bookService.getCategoryPage(category.toString(),PageRequest.of(currentPage-1, pageSize));
        model.addAttribute("bookPage",bookPage);
        model.addAttribute("category",category);

        int totalPages = bookPage.getTotalPages();
        if(totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers",pageNumbers);

        }
        return "category";
    }
}
