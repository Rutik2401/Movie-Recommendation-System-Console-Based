package org.tech.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.tech.client.MovieRecommendationApp;
import org.tech.model.MovieModel;
import org.tech.repository.MovieRepository;


public class MovieService {
	MovieRepository mrepo = new MovieRepository();

	public boolean isAddMovie(MovieModel mmodel) {
		try {
			return mrepo.isAddMovie(mmodel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public List<MovieModel> getAllMovie() throws SQLException {
		return mrepo.getAllMovie();
	}

	public int updateMovieDetails(int id) throws SQLException {
		return mrepo.updateMovieDetails(id);
	}

	public int deleteMovie(int id) throws SQLException {
		return mrepo.deleteMovie(id);
	}

	public Boolean assignMovie(String mov_name) {
		return mrepo.assignMovie(mov_name);
	}

	public ArrayList getAllWatchMovieById(int userId) {
		return mrepo.getAllWatchMovieById(userId);
	}

	public LinkedHashMap<Integer, ArrayList<String>> getUserWiseMovieName() {
		return mrepo.getUserWiseMovieName();
	}

	public String getRecommendation() {
	    Map<Integer, Map<String, Double>> userRatings = mrepo.getRecommendation();
	    
	    Map<String, Double> priorProbabilities = calculatePriorProbabilities(userRatings);
	    
	    Map<String, Double> likelihoods = calculateLikelihoods(userRatings, MovieRecommendationApp.userId);
	    System.out.println("likelihoods"+likelihoods);
	    
	    Map<String, Double> posteriorProbabilities;
	    
	    if (likelihoods.isEmpty()) {
	        // If new user
	        posteriorProbabilities = priorProbabilities;
	    } else {
	        posteriorProbabilities = calculatePosteriorProbabilities(priorProbabilities, likelihoods);
	    }
	    
	    String recommendedMovie = recommendMovie(posteriorProbabilities);
	    System.out.println(recommendedMovie);
	    return recommendedMovie;
	}

	private static Map<String, Double> calculatePriorProbabilities(Map<Integer, Map<String, Double>> userRatings) {
	    Map<String, Double> priorProbabilities = new HashMap<>();
	    Map<String, Double> totalRatings = new HashMap<>();
	    int numRatings = 0;

	    // Calculate total ratings for each movie
	    for (Map<String, Double> ratings : userRatings.values()) {
	        for (Map.Entry<String, Double> entry : ratings.entrySet()) {
	            totalRatings.put(entry.getKey(), totalRatings.getOrDefault(entry.getKey(), 0.0) + entry.getValue());
	            numRatings++;
	        }
	    }
	    System.out.println("totalRatings"+totalRatings);

	    // Calculate prior probabilities
	    for (Map.Entry<String, Double> entry : totalRatings.entrySet()) {
	        priorProbabilities.put(entry.getKey(), entry.getValue() / (double) numRatings);
	    }
	    System.out.println("prior probabilities"+priorProbabilities);
	    return priorProbabilities;
	}

	private static Map<String, Double> calculateLikelihoods(Map<Integer, Map<String, Double>> userRatings, int userId) {
	    Map<String, Double> likelihoods = new HashMap<>();
	    Map<String, Double> userRatingsForUser = userRatings.get(userId);

	    if (userRatingsForUser == null) {
	        // Return empty likelihoods for new users
	        return likelihoods;
	    }

	    int totalRatings = userRatingsForUser.size();
	    int numMovies = userRatings.values().iterator().next().size();

	    for (String movie : userRatingsForUser.keySet()) {
	        Double rating = userRatingsForUser.get(movie);
	        likelihoods.put(movie, (rating + 1) / (double) (totalRatings + numMovies));
	    }

	    return likelihoods;
	}

	private static Map<String, Double> calculatePosteriorProbabilities(Map<String, Double> priorProbabilities, Map<String, Double> likelihoods) {
	    Map<String, Double> posteriorProbabilities = new HashMap<>();

	    for (String movie : priorProbabilities.keySet()) {
	        double prior = priorProbabilities.get(movie);
	        double likelihood = likelihoods.getOrDefault(movie, 0.0);
	        double posterior = prior * likelihood;
	        posteriorProbabilities.put(movie, posterior);
	    }
	    System.out.println("posteriorProbabilities"+posteriorProbabilities);
	    return posteriorProbabilities;
	}

	private static String recommendMovie(Map<String, Double> posteriorProbabilities) {
	    String recommendedMovie = null;
	    double maxProbability = 0.0;

	    for (Map.Entry<String, Double> entry : posteriorProbabilities.entrySet()) {
	        if (entry.getValue() > maxProbability) {
	            maxProbability = entry.getValue();
	            recommendedMovie = entry.getKey();
	        }
	    }

	    if (recommendedMovie == null) {
	        return "No suitable movie found";
	    }

	    return recommendedMovie;
	}


}
