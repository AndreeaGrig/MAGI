package apbdoo.onlineLib.repositories;


import apbdoo.onlineLib.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UserInsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertWithQuery(User user) {
        entityManager.createNativeQuery("INSERT INTO users (email, password, username) VALUES (?,?,?)")
                .setParameter(1, user.getEmail())
                .setParameter(2, user.getPassword())
                .setParameter(3, user.getUsername())
                .executeUpdate();
    }
}
