package org.tech.repository;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  
import java.sql.*;
import java.util.*;
import org.tech.client.MovieRecommendationApp;
import org.tech.config.DBHelper;
import org.tech.exceptions.MovieNotFoundException;
import org.tech.model.MovieModel;

public class MovieRepository extends DBHelper {
	
	Scanner sc = new Scanner(System.in);

	public boolean isAddMovie(MovieModel mmodel) throws SQLException {
		String mov_name = mmodel.getMov_name();
		String mov_actor = mmodel.getMov_actor();
		int mov_year = mmodel.getMov_year();
		String mov_lang = mmodel.getMov_lang();
		stmt = conn.prepareStatement("insert into Moviemaster values('0',?,?,?,?)");
		stmt.setString(1, mov_name);
		stmt.setString(2, mov_actor);
		stmt.setInt(3, mov_year);
		stmt.setString(4, mov_lang);
		return stmt.executeUpdate() > 0 ? true : false;
	}

	public List<MovieModel> getAllMovie() throws SQLException {
		List<MovieModel> mlist = new ArrayList<MovieModel>();
		PreparedStatement stmt = conn.prepareStatement("select * from Moviemaster ");
		rs = stmt.executeQuery();
		while (rs.next()) {
			MovieModel mmodel = new MovieModel();
			mmodel.setMov_id(rs.getInt(1));
			mmodel.setMov_name(rs.getString(2));
			mmodel.setMov_actor(rs.getString(3));
			mmodel.setMov_year(rs.getInt(4));
			mmodel.setMov_lang(rs.getString(5));
			mlist.add(mmodel);
		}
		return mlist.size() > 0 ? mlist : null;
	}

	public int updateMovieDetails(int id) {
		int value = -1;
		try {
			if (id > 0) {
				stmt = conn.prepareStatement("select Mov_id from MovieMaster where Mov_id=?");
				stmt.setInt(1, id);
				rs = stmt.executeQuery();
				while (rs.next()) {
					if (rs.getInt(1) == id) {
						int choice;
						do {
							System.out.println("What do you want to update? Please select one of the options below:");
							System.out.println("1. Movie Name\n2. Movie Actor\n3. Movie Language\n4. Movie Year");
							System.out.println("5.EXIT 🔚❌");
							stmt = conn.prepareStatement("select * from Moviemaster");
							choice = sc.nextInt();
							switch (choice) {
							case 1: {
								System.out.println("Enter new movie name for update:");
								String mov_name = sc.next();
								stmt = conn.prepareStatement("update Moviemaster set Mov_name=? where Mov_id=?");
								stmt.setString(1, mov_name);
								stmt.setInt(2, id);
								value = stmt.executeUpdate();
								System.out.println(value > 0 ? "Movie name updated successfully" : "Movie name not updated");
								break;
							}
							case 2: {
								System.out.println("Enter new movie actor for update:");
								String mov_actor = sc.next();
								stmt = conn.prepareStatement("update Moviemaster set Mov_actor=? where Mov_id=?");
								stmt.setString(1, mov_actor);
								stmt.setInt(2, id);
								value = stmt.executeUpdate();
								System.out.println(value > 0 ? "Movie actor updated successfully" : "Movie actor not updated");
								break;
							}
							case 3: {
								System.out.println("Enter new movie language for update:");
								String mov_lang = sc.next();
								stmt = conn.prepareStatement("update Moviemaster set Mov_lang=? where Mov_id=?");
								stmt.setString(1, mov_lang);
								stmt.setInt(2, id);
								value = stmt.executeUpdate();
								System.out.println(value > 0 ? "Movie language updated successfully"
										: "Movie language not updated");
								break;
							}
							case 4: {
								System.out.println("Enter new movie year for update:");
								int mov_year = sc.nextInt();
								stmt = conn.prepareStatement("update Moviemaster set Mov_year=? where Mov_id=?");
								stmt.setInt(1, mov_year);
								stmt.setInt(2, id);
								value = stmt.executeUpdate();
								System.out.println(
										value > 0 ? "Movie year updated successfully" : "Movie year not updated");
								break;
							}
							case 5:{
								break;
							}
							default:
								System.out.println("Please enter a correct choice for updating.");
							}
							System.out.println("To continue updating, press 1 (Press any other key to exit)");
							choice = sc.nextInt();
						} while (choice == 1);
					} else {
						System.out.println("ID not found.");
					}
				}
			} else {
				System.out.println("Please enter a correct movie ID.");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return value;
		}
		return value;
	}

	public int deleteMovie(int id) throws SQLException {
		int value = -1;
		if (id > 0) {
			stmt = conn.prepareStatement("select Mov_id from MovieMaster where Mov_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == id) {
					stmt = conn.prepareStatement("delete from Moviemaster where Mov_id=?");
					stmt.setInt(1, id);
					value = stmt.executeUpdate();
				} else {
					System.out.println("Movie not found.");
				}
			}
		} else {
			System.out.println("Please enter a correct movie ID.");
		}
		return value;
	}

	public Boolean assignMovie(String mov_name) {
		int value = 0;
		if (mov_name != null) {
		    try {
		        String query = "select mov_name ,mov_id from MovieMaster where mov_name=? ";
		        stmt = conn.prepareStatement(query);
		        stmt.setString(1, mov_name);
		        rs = stmt.executeQuery();
		        if (rs.next()) {
		            System.out.println("Movie is Present");
		            
		            System.out.println(rs.getInt("mov_id"));
		            System.out.println(MovieRecommendationApp.userId);
		            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		            LocalDateTime now = LocalDateTime.now();
		            System.out.println(dtf.format(now));
		            
		            stmt = conn.prepareStatement("insert into movieuserjoin values(?,?,?)");
		            stmt.setInt(1, rs.getInt("mov_id"));
		            stmt.setInt(2, MovieRecommendationApp.userId);
		            stmt.setString(3, dtf.format(now));
		            value = stmt.executeUpdate();
		            return value > 0 ? true : false;

		        } else {
		            throw new MovieNotFoundException();
		        }

		    } catch (SQLException e) {
		        System.out.println("Error is::" + e);
		        return false;
		    } catch (MovieNotFoundException e) {
		        System.out.println("Error is : " + e.MovieNotFoundException());
		        return false;
		    }

		} else {
		    System.out.println("Please Enter Correct Name (Name Cannot be Empty)");
		}
		return false;
	}

	public ArrayList getAllWatchMovieById(int userId) {
		ArrayList<String> mov_list = new ArrayList<String>();
		try {
			stmt = conn.prepareStatement(
					" select m.mov_name from moviemaster m inner join movieuserjoin mu on m.mov_id=mu.mov_id where mu.user_id=?;");
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				mov_list.add(rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println("Error is:" + e);
			return null;
		}
		return mov_list.size() > 0 ? mov_list : mov_list;
	}

	public LinkedHashMap<Integer, ArrayList<String>> getUserWiseMovieName() {
		LinkedHashMap<Integer, ArrayList<String>> map1 = new LinkedHashMap<Integer, ArrayList<String>>();
		try {

			stmt = conn.prepareStatement("select user_id from Usermaster");
			rs = stmt.executeQuery();
			while (rs.next()) {
				int userid = rs.getInt(1);
				PreparedStatement stmt1 = conn.prepareStatement("select mm.mov_name from moviemaster mm inner join movieuserjoin muj on mm.mov_id=muj.mov_id inner join usermaster um on muj.user_id=um.user_id where um.user_id=?");
				stmt1.setInt(1, userid);
				ResultSet rs1 = stmt1.executeQuery();
				ArrayList<String> moviename = new ArrayList<String>();
				while (rs1.next()) {
					moviename.add(rs1.getString(1));
				}
				map1.put(userid, moviename);
			}
			return map1;
		} catch (Exception ex) {
			System.out.println("error is :" + ex);
			return map1;
		}
	}

	public Map<Integer, Map<String, Double>> getRecommendation() {
		 Map<Integer, Map<String, Double>> userRatings = new HashMap<>();
	        try {
	        	
	            String query = "SELECT r.user_id, m.mov_name, r.rating FROM rating r JOIN moviemaster m ON r.mov_id = m.mov_id";
	            stmt = conn.prepareStatement(query);
	            rs = stmt.executeQuery(query);

	            while (rs.next()) {
	                int userId = rs.getInt("user_id");
	                String movieName = rs.getString("mov_name");
	                int rating = rs.getInt("rating");

	                userRatings.putIfAbsent(userId, new HashMap<>());
	                userRatings.get(userId).put(movieName, (double) rating);
	            }

	        } catch (Exception e) {
	            System.out.println("Error IS:"+e);
	        }
		return userRatings;
	}
}
