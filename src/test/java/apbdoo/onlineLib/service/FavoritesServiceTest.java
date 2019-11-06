package apbdoo.onlineLib.service;

import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.domain.Favourites;
import apbdoo.onlineLib.domain.User;
import apbdoo.onlineLib.repositories.FavouritesRepository;
import apbdoo.onlineLib.services.BookService;
import apbdoo.onlineLib.services.FavouritesService;
import apbdoo.onlineLib.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class FavoritesServiceTest {
    public static final String TITLE = "title";

    @Autowired
    FavouritesService favouritesService;

    @Autowired
    FavouritesRepository favouritesRepository;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Test
    public void testSaveBookToFavorites(){
        Favourites fav = new Favourites();
        Book book = bookService.findBookById(1L);
        String title = book.getTitle();
        User user = userService.findByEmail("andreea@gmail.com");
        fav.setBookFav(book);
        fav.setUserFav(user);
        Favourites favouriteSave;
        favouriteSave = favouritesService.saveFavourites(fav);
        assertEquals(title, favouriteSave.getBookFav().getTitle());
    }

    @Test
    public void testDeleteBookFromFavorites() {
        Optional<Favourites> optionalFavourites = favouritesRepository.findById(1L);
        assertTrue(optionalFavourites.isPresent());
        favouritesService.deleteById(1L);
        optionalFavourites = favouritesRepository.findById(1L);
        assertFalse(optionalFavourites.isPresent());
    }


}
