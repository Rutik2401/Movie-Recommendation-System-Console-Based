package org.tech.repository;

import java.util.ArrayList;

import org.tech.client.MovieRecommendationApp;
import org.tech.config.DBHelper;
import org.tech.model.RatingModel;

public class RatingRepository extends DBHelper{

	public Boolean addRating(RatingModel rtmodel,String mov_name) {
		try {
			stmt=conn.prepareStatement("select mov_id from Moviemaster where Mov_name=? ");
			stmt.setString(1, mov_name);
			rs=stmt.executeQuery();
			if(rs.next()) {
				
				stmt=conn.prepareStatement("insert into Rating values('0',?,?,?)");
				stmt.setDouble(1, rtmodel.getRating());//Rating
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

	public ArrayList<RatingModel> getAllRatings() {
		ArrayList<RatingModel> rtlist= new ArrayList<RatingModel>();
		try {
			stmt=conn.prepareStatement("select * from Rating");
			rs=stmt.executeQuery();
			while (rs.next()) {
				RatingModel rmodel= new RatingModel();
				rmodel.setRating_ID(rs.getInt(1));
				rmodel.setRating((double) rs.getInt(2));
				rmodel.setUser_id(rs.getInt(3));
				rmodel.setMov_id(rs.getInt(4));
				rtlist.add(rmodel);
			}
			return rtlist;
		}catch(Exception e) {
			System.out.println("Error IS:"+e);
			return rtlist;
		}
		
	}

}
