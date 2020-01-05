package apbdoo.onlineLib.repositories;

import apbdoo.onlineLib.domain.Favourites;
import apbdoo.onlineLib.domain.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;

@Repository
public class FavouritesInsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertWithQuery(Favourites favourites) {
        Long userId = favourites.getUserFav().getId();
        Long bookId = favourites.getBookFav().getId();

        entityManager.createNativeQuery("INSERT INTO favourites (add_date, book_fav_id, user_fav_id) VALUES (?,?,?) ")
                .setParameter(1, new Date())
                .setParameter(2, bookId)
                .setParameter(3, userId)
                .executeUpdate();

    }

}
