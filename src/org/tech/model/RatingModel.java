package org.tech.model;

public class RatingModel extends MovieModel {
	private int rating_ID;
	private Double rating;
	
	public int getRating_ID() {
		return rating_ID;
	}
	
	public void setRating_ID(int rating_ID) {
		this.rating_ID = rating_ID;
	}
	
	public Double getRating() {
		return rating;
	}
	
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	public RatingModel(Double rating) {
		this.rating=rating;
	}
	
	public RatingModel() {
		super();
	}
	
	public RatingModel(int rating_ID, Double rating) {
		super();
		this.rating_ID = rating_ID;
		this.rating = rating;
	}
	

	
	
}
