package apbdoo.onlineLib.controllers;

import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.domain.Favourites;
import apbdoo.onlineLib.domain.User;
import apbdoo.onlineLib.services.BookService;
import apbdoo.onlineLib.services.FavouritesService;
import apbdoo.onlineLib.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Set;

@Controller
public class FavouritesController {

    @Autowired
    UserService userService;

    @Autowired
    FavouritesService favouritesService;

    @Autowired
    BookService bookService;

    @RequestMapping("/mybooks")
    public String showMyBooks(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.findByEmail(name);
        Set<Favourites> favourites = user.getFavourites();
        model.addAttribute("favourites", favourites);

        return "mybooks";
    }

    @RequestMapping("/book/{id}/addtomybooks")
    public String addToMyBooks(@PathVariable String id){
        Favourites favourite = new Favourites();
        Book book = bookService.findBookById(Long.valueOf(id));
        favourite.setBookFav(book);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.findByEmail(name);
        favourite.setUserFav(user);
        favourite.setAddDate(new Date());
        favouritesService.saveFavourites(favourite);

        return "redirect:/mybooks";
    }

    @RequestMapping("/favbook/{id}/removefromybooks")
    public String removeFromMyBooks(@PathVariable String id){
        favouritesService.deleteById(Long.parseLong(id));
        return "redirect:/mybooks";

    }

}
