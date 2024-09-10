package org.tech.repository;

import java.util.ArrayList;

import org.tech.client.MovieRecommendationApp;
import org.tech.config.DBHelper;
import org.tech.model.RatingModel;
import org.tech.model.ReviewModel;

public class ReviewRepository extends DBHelper {

	public Boolean addReview(ReviewModel rmodel, String mov_name) {
		try {
			stmt=conn.prepareStatement("select mov_id from Moviemaster where Mov_name=? ");
			stmt.setString(1, mov_name);
			rs=stmt.executeQuery();
			if(rs.next()) {
				System.out.println("ccc");
				String temp=rmodel.getReviewText();
				stmt=conn.prepareStatement("insert into Review values('0',?,?,?)");
				stmt.setString(1, temp);//review_text
				stmt.setInt(2,MovieRecommendationApp.userId);
				stmt.setInt(3, rs.getInt(1));
				int value=stmt.executeUpdate();
				return value>0?true:false;
			}
		}
		catch(Exception e) {
			System.out.println("Error Is:"+e);
			return false ;
		}
		return false ;
	}

	public ArrayList<ReviewModel> getAllReviews() {
		ArrayList<ReviewModel> rlist= new ArrayList<ReviewModel>();
		try {
			stmt=conn.prepareStatement("select * from review");
			rs=stmt.executeQuery();
			while (rs.next()) {
				ReviewModel rmodel= new ReviewModel();
				rmodel.setReviewID(rs.getInt(1));
				rmodel.setReviewText(rs.getString(2));
				rmodel.setUser_id(rs.getInt(3));
				rmodel.setMov_id(rs.getInt(4));
				rlist.add(rmodel);
			}
			return rlist;
		}catch(Exception e) {
			System.out.println("Error IS:"+e);
			return rlist;
		}
	}

}
