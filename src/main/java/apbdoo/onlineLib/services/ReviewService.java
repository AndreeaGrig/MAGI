package apbdoo.onlineLib.services;

import apbdoo.onlineLib.domain.Review;

public interface ReviewService {
     Review saveReview(Review review);
     void deleteById(Long id);
     Review findById(Long id);
}
