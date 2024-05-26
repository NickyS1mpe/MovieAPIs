package dev.nick.movies.service;

import dev.nick.movies.model.Movie;
import dev.nick.movies.model.Review;
import dev.nick.movies.model.User;
import dev.nick.movies.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String nickname, String username, String imdbId) {
        String time = new UserService().getDate();
        Review review = reviewRepository.insert(new Review(reviewBody, nickname, time));

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        mongoTemplate.update(User.class)
                .matching(Criteria.where("username").is(username))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;
    }
}
