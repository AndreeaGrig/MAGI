package apbdoo.onlineLib.services;

import apbdoo.onlineLib.domain.Review;
import apbdoo.onlineLib.repositories.ReviewInsertRepository;
import apbdoo.onlineLib.repositories.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewInsertRepository reviewInsertRepository;

    @Override
    public Review saveReview(Review review) {
        log.info("Saving "+review.getUser().getUsername()+"'s review for book "+review.getBook().getTitle());
        reviewInsertRepository.insertWithQuery(review);
        return reviewInsertRepository.selectWithQuery(review);
    }

    @Override
    public void updateReview(Review review) {
        log.info("Updating "+review.getUser().getUsername()+"'s review for book "+review.getBook().getTitle());
        reviewInsertRepository.updateWithQuery(review);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting review with id: "+id);
        reviewRepository.deleteById(id);
    }

    @Override
    public Review findById(Long id) {

        Optional<Review> reviewOptional = reviewRepository.findById(id);
        log.info("Retrieving review with id: "+id);
        if (!reviewOptional.isPresent()) {
            log.error("Review with id "+id+" not found!");
            throw new RuntimeException("Review not found!");
        }

        return reviewOptional.get();
    }
}
