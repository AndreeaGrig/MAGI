package apbdoo.onlineLib.repositories;

import apbdoo.onlineLib.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    void deleteById(long id);
}
