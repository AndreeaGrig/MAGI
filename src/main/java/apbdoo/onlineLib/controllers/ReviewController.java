package apbdoo.onlineLib.controllers;

import apbdoo.onlineLib.domain.Author;
import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.domain.Review;
import apbdoo.onlineLib.domain.User;
import apbdoo.onlineLib.services.BookService;
import apbdoo.onlineLib.services.ReviewService;
import apbdoo.onlineLib.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @RequestMapping("/book/{id}/addreview")
    public String addReview(@PathVariable String id, Model model){
        Review review = new Review();
        Book book = bookService.findBookById(Long.valueOf(id));
        review.setBook(book);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.findByEmail(name);
        review.setUser(user);
        review.setAddDate(new Date());
        reviewService.saveReview(review);
        review.setText("  ");

        model.addAttribute("review", review);

        return "addreview";
    }

    @GetMapping("book/{bookId}/review/{id}/update")
    public String updateReviewContent(@PathVariable String bookId,
                                       @PathVariable String id, Model model){
        model.addAttribute("review", reviewService.findById(Long.valueOf(id)));

        return "addreview";
    }

    @PostMapping("review")
    public String saveOrUpdate(@Valid @ModelAttribute Review review,
                               BindingResult bindingResult){
        String text =  review.getText();
        if(text.trim()==null || text.trim().equals(""))
            bindingResult.addError(new FieldError("review","text","You can't insert blank reviews!"));
        if (bindingResult.hasErrors()) {
            return "addReview";
        }

        review.setLastEditDate(new Date());
        Review savedCommand = reviewService.saveReview(review);

        return "redirect:/book/details/"+review.getBook().getId();
    }

    @GetMapping
    @RequestMapping("book/{bookId}/review/{id}/delete")
    public String deleteReviewById(@PathVariable String id, @PathVariable String bookId){
        reviewService.deleteById(Long.parseLong(id));
        return "redirect:/book/details/"+bookId;
    }
}
