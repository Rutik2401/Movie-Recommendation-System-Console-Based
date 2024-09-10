package org.tech.service;
public class SQLQueries {
// Queries --->  MovieMaster table
	public static final String insertMovie = "INSERT INTO moviemaster VALUES ('0',?,?,?,?)";
	public static final String selectAllMovies = "SELECT * FROM moviemaster";
	public static final String updateMovieName = "UPDATE moviemaster SET mov_name=? WHERE mov_id=?";
	public static final String updateMovieActor = "UPDATE moviemaster SET mov_actor=? WHERE mov_id=?";
	public static final String updateMovieLanguage = "UPDATE moviemaster SET mov_lang=? WHERE mov_id=?";
	public static final String updateMovieYear = "UPDATE moviemaster SET mov_year=? WHERE mov_id=?";
	public static final String deleteMovie = "DELETE FROM moviemaster WHERE mov_id=?";

// Queries ---> MovieUserJoin table
	public static final String insertMovieUserJoin = "INSERT INTO movieuserjoin VALUES (?,?)";

// Queries ---> watched movies by user
	public static final String selectWatchedMoviesByUser = "SELECT m.mov_name FROM moviemaster m INNER JOIN movieuserjoin mu ON m.mov_id=mu.mov_id WHERE mu.user_id=?";

// Queries ---> user-wise movie names
	public static final String selectUserWiseMovieNames = "SELECT mm.mov_name FROM moviemaster mm INNER JOIN movieuserjoin muj ON mm.mov_id=muj.mov_id INNER JOIN usermaster um ON muj.user_id=um.user_id WHERE um.user_id=?";

// Queries ---> Rating table
	public static final String selectMovieIdByName = "SELECT mov_id FROM moviemaster WHERE mov_name=?";
	public static final String insertRating = "INSERT INTO rating VALUES ('0',?,?,?)";
	public static final String selectAllRatings = "SELECT * FROM rating";

// Queries  --->  Review table
	public static final String insertReview = "INSERT INTO review VALUES ('0',?,?,?)";
	public static final String selectAllReviews = "SELECT * FROM review";

// Queries  ---> UserMaster table
	public static final String registerUser = "INSERT INTO usermaster (username, password, email, contact) VALUES (?, ?, ?, ?)";
	public static final String updateUserDetails = "UPDATE usermaster SET username=? WHERE password=?";
	public static final String getAllRegisterUsers = "SELECT * FROM usermaster";
	public static final String deleteUserById = "DELETE FROM usermaster WHERE user_id=?";
	public static final String authenticateUser = "SELECT user_id,password FROM usermaster WHERE username = ?";
	public static final String updateUserPassword = "UPDATE usermaster SET password=? WHERE contact=?";
	public static final String selectUserByContact = "SELECT contact FROM usermaster WHERE contact=?";
}
