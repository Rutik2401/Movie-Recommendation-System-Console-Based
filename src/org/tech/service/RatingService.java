package org.tech.service;

import java.util.ArrayList;

import org.tech.model.RatingModel;
import org.tech.repository.RatingRepository;

public class RatingService {
	RatingRepository rtRepo = new RatingRepository();
	public Boolean addRating(RatingModel rtmodel, String mov_name) {
		
		return rtRepo.addRating(rtmodel,mov_name);
	}
	public ArrayList<RatingModel> getAllRatings() {
		return rtRepo.getAllRatings();
	}

}
