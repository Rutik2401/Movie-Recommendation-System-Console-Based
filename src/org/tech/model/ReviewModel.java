package org.tech.model;

public class ReviewModel extends MovieModel  {
	private int reviewID;
	private String reviewText;
	// Constructor
	public ReviewModel(String review) {
		this.reviewText=review;
	}
	public ReviewModel() {
		
	}
	// Getter And Setter
	public int getReviewID() {
		return reviewID;
	}
	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	
	
	
	
}
