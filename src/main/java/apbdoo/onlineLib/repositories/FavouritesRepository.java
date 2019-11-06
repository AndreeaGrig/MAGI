package apbdoo.onlineLib.repositories;


import apbdoo.onlineLib.domain.Book;
import apbdoo.onlineLib.domain.Favourites;
import apbdoo.onlineLib.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface FavouritesRepository extends JpaRepository<Favourites, Long> {

    @Query("from Favourites where bookFav = :bookFav and userFav = :userFav")
    List<Favourites> findByBookAndUser(@Param("bookFav") Book bookFav, @Param("userFav") User userFav);
}
