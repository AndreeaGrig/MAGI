package apbdoo.onlineLib.services;

import apbdoo.onlineLib.domain.Review;
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

    @Override
    public Review saveReview(Review review) {
        log.info("Saving "+review.getUser().getUsername()+"'s review for book "+review.getBook().getTitle());
        Review savedReview = reviewRepository.save(review);
        return savedReview;
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
