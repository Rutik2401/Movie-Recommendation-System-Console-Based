package org.tech.service;

import java.util.ArrayList;

import org.tech.model.ReviewModel;
import org.tech.repository.ReviewRepository;

public class ReviewServices {
	ReviewRepository rRepo= new ReviewRepository();
	public Boolean addReview(ReviewModel rmodel, String mov_name) {
		return rRepo.addReview(rmodel,mov_name);
	}
	public ArrayList<ReviewModel> getAllReviews() {
		return rRepo.getAllReviews();
	}
	

}
