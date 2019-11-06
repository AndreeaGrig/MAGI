package apbdoo.onlineLib.service;

import apbdoo.onlineLib.domain.Review;
import apbdoo.onlineLib.repositories.ReviewRepository;
import apbdoo.onlineLib.services.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewServiceTest  {

    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void testSaveReview(){
        Review review = new Review();
        String text = "This book was amazing!";
        review.setText("This book was amazing!");
        Review savedReview;
        savedReview = reviewService.saveReview(review);
        assertEquals(text, savedReview.getText());
    }

    @Test
    public void testDeleteReview() {
        reviewService.deleteById(1L);
        Optional<Review>  optionalReview = reviewRepository.findById(1L);
        assertFalse(optionalReview.isPresent());
    }

}
