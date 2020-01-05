package apbdoo.onlineLib.repositories;


import apbdoo.onlineLib.domain.Review;
import apbdoo.onlineLib.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Date;

@Repository
public class ReviewInsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertWithQuery(Review review) {
        Long userId = review.getUser().getId();
        Long bookId = review.getBook().getId();

        entityManager.createNativeQuery("INSERT INTO review (add_date, book_id, last_edit_date, text, user_id) VALUES (?,?,?,?,?) ")
                .setParameter(1, review.getAddDate())
                .setParameter(2, bookId)
                .setParameter(3, review.getLastEditDate())
                .setParameter(4, review.getText())
                .setParameter(5, userId)
               .executeUpdate();

    }

    @Transactional
    public void updateWithQuery(Review review) {
        Long userId = review.getUser().getId();
        Long bookId = review.getBook().getId();
        Long id = review.getId();

        entityManager.createNativeQuery("UPDATE review set book_id=?, last_edit_date=?, text=?, user_id=? where id = ?")
                .setParameter(1, bookId)
                .setParameter(2, new Date())
                .setParameter(3, review.getText())
                .setParameter(4, userId)
                .setParameter(5, id)
                .executeUpdate();

    }


    @Transactional
    public Review selectWithQuery(Review review) {
        Long userId = review.getUser().getId();
        Long bookId = review.getBook().getId();

        Review reviewStored = (Review) entityManager.createNativeQuery("Select * FROM review  WHERE book_id = ?  AND user_id = ? order by id desc", Review.class)
                .setParameter(1, bookId)
                .setParameter(2, userId)
                .getResultList().get(0);

        return reviewStored;

    }

}
