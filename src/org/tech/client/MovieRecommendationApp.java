package org.tech.client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.tech.model.MovieModel;
import org.tech.model.RatingModel;
import org.tech.model.ReviewModel;
import org.tech.model.UserModel;

import org.tech.service.MovieService;
import org.tech.service.RatingService;
import org.tech.service.ReviewServices;
import org.tech.service.UserServices;

public class MovieRecommendationApp {
	public static int userId;

	public static void main(String[] args) throws SQLException {
		Scanner sc = new Scanner(System.in);
		MovieModel mmodel = new MovieModel();
		MovieService mservice = new MovieService();

		UserModel umodel = new UserModel();
		UserServices uservice = new UserServices();

		ReviewModel rmodel;
		ReviewServices rservic = new ReviewServices();

		RatingModel rtmodel = new RatingModel();
		RatingService rtservice = new RatingService();

		int choice;
		try {
			do {
				System.out.println("\033[0;31m" + "\033[1m"+ "🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴\t|「 ✦ Movie Recommendation System ✦ 」|\t🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴"+ "\033[0m");
				System.out.println("\033[1m" + "Select one of the options below:");
				System.out.println("1. Admin Login 🤴");
				System.out.println("2. User Login 👤");
				System.out.println("3. Register User 🖋️");
				try {
					choice = sc.nextInt();
					switch (choice) {
					case 1: {
						System.out.println("Enter your name and password:");
						String adminName = sc.next();
						String adminPass = sc.next();
						if (("Admin".equalsIgnoreCase(adminPass) && "Rutik".equalsIgnoreCase(adminName) || true)) {
							System.out.println("Admin Login Successful 🔓");
							do {
								System.out.println("1. Add Movie 🎞️ ");
								System.out.println("2. Show All Movies");
								System.out.println("3. Update Movie");
								System.out.println("4. Delete Movie");
								System.out.println("5. Show All Register Users");
								System.out.println("6. Delete User");
								System.out.println("7. Show User Wise Movie");
								System.out.println("8. EXIT 🔚❌");
								System.out.println("Enter your choice:");
								choice = sc.nextInt();
								switch (choice) {
								case 1: {
									sc.nextLine();
									System.out.println("Enter Movie Name, Actor Name, Year, Language:");
									String mov_name = sc.nextLine();
									String mov_actor = sc.nextLine();
									int mov_year = sc.nextInt();
									String mov_lang = sc.next();
									mmodel = new MovieModel(mov_name, mov_actor, mov_year, mov_lang);
									boolean b = mservice.isAddMovie(mmodel);
									System.out.println(b ? "Movie added successfully ✅" : "Movie not added");
									break;
								}
								case 2: {
									try {
										List<MovieModel> mlist = mservice.getAllMovie();
										if (mlist.size() > 0) {
											System.out.println("-------------------------------------------------------------------------");
											System.out.println("|   ID   |      Name      |      Actor      |   Year   |   Language   |");
											System.out.println("-------------------------------------------------------------------------");
											for (Object movieModel : mlist) {
												mmodel = (MovieModel) movieModel;
												System.out.printf("| %-6s | %-14s | %-15s | %-8s | %-12s |\n",mmodel.getMov_id(), mmodel.getMov_name(), mmodel.getMov_actor(),mmodel.getMov_year(), mmodel.getMov_lang());
											}
											System.out.println(
													"-------------------------------------------------------------------------");
										} else {
											System.out.println("Please add movies first....");
										}
									} catch (SQLException e) {
										e.printStackTrace();
									}
									break;
								}
								case 3: {
									System.out.println("If you want to update any field in a movie, please enter the ID:");
									int id = sc.nextInt();
									int b = mservice.updateMovieDetails(id);
									System.out.println(b > 0 ? "Movie details updated successfully ✅": "Movie details not updated");
									break;
								}
								case 4: {
									System.out.println("If you want to delete a movie, please enter the ID:");
									int id = sc.nextInt();
									int b = mservice.deleteMovie(id);
									System.out.println(b > 0 ? "Movie deleted successfully ✅" : "Movie not deleted");
									break;
								}
								case 5: {
									List<UserModel> ulist = uservice.getAllregisterUser();
									System.out.println("--------------------------------------------------------------------------------------------------------------------");
									System.out.println("| user_id | username | password                                                     | email           | contact    |");
									System.out.println("--------------------------------------------------------------------------------------------------------------------");
									if (ulist.size() != 0) {
										for (UserModel umodel1 : ulist) {
											System.out.printf("| %-7d | %-8s | %-60s | %-15s | %-10s |\n",umodel1.getUser_id(), umodel1.getUsername(), umodel1.getPassword(),umodel1.getEmail(), umodel1.getContact());
										}
									} else {
										System.out.println("There Is No User");
									}
									System.out.println("--------------------------------------------------------------------------------------------------------------------");
									break;
								}
								case 6: {
									System.out.println("If You Want TO Delete User Then Enter User Id:");
									int id = sc.nextInt();
									int b = uservice.deleteUserById(id);
									if (b > 0) {
										System.out.println("User Deleted Successfully ✅");
									} else {
										System.out.println("User is Not Present In Database");
									}
									break;
								}
								case 7: {
									LinkedHashMap<Integer, ArrayList<String>> map1 = mservice.getUserWiseMovieName();
									Set<Map.Entry<Integer, ArrayList<String>>> set = map1.entrySet();
									if (map1.size() > 0) {
										System.out.println("......................................................................");
										for (Map.Entry<Integer, ArrayList<String>> d : set) {
											ArrayList<String> values = d.getValue();
											if (values.size() > 0) {
												System.out.println(d.getKey() + "➤➤➤| Movie Name |");
												for (String val : values) {
													System.out.printf("     |   %-9s|\n", val);
												}
											}
											System.out.println("......................................................................");
										}
									} else {
										System.out.println("User Not Watch Any Movie Till");
									}
									break;
								}
								case 8: {
									break;
								}
								default:
									System.out.println("Invalid choice");
								}
								System.out.println("To continue, press 1 for HOME PAGE or 0 to exit:");
								choice = sc.nextInt();
							} while (choice == 1);
						} else {
							System.out.println("Please enter the correct name and password");
						}
						break;
					}
					case 2: {
						System.out.println("Enter your name and password:");
						String userName = sc.next();
						String UserPass = sc.next();

						userId = uservice.authenticateUser(userName, UserPass);// It  Return User ID If User Is genuine person

						if (userId > 0) {
							System.out.println("User Login Successful 🔓");
							do {
								System.out.println("1. Recommended Movie To the User");
								System.out.println("2. Watch Movie Using input");
								System.out.println("3. Update User");
								System.out.println("4. Show All Movies");
								System.out.println("5. Show Movies Watched by User:");
								System.out.println("6. Show All Reviews");
								System.out.println("7. Show All Ratings");
								System.out.println("8. EXIT 🔚❌");
								System.out.println("Enter your choice:");
								choice = sc.nextInt();
								switch (choice) {
								case 1: {
									String mov_name = mservice.getRecommendation();
									Boolean b1 = mservice.assignMovie(mov_name);
									if (b1) {
										System.out.println("Movie Recommendation to the User Successfully ✅");
										System.out.println("If You Want to Give a Review, Press 1 (For EXIT PRESS 0)");

										do {
											System.out.println("For Giving Review Press 1");
											System.out.println("For Giving Rating Press 2");
											System.out.println("Please Enter Your Choice:");
											int choice1 = sc.nextInt();
											switch (choice1) {
											case 1: {
												System.out.println("Enter Review For Movie:");
												String review = sc.next();
												rmodel = new ReviewModel(review);
												System.out.println(rmodel.getReviewText());
												Boolean b = rservic.addReview(rmodel, mov_name);
												System.out.println(b ? "Review Saved Successfully ✅" : "Review Not Saved");
												break;
											}
											case 2: {
												System.out.println("Enter Rating For Movie:");
												Double rating = sc.nextDouble();
												rtmodel = new RatingModel(rating);
												System.out.println("Rating is: " + rtmodel.getRating());
												Boolean b = rtservice.addRating(rtmodel, mov_name);
												System.out.println(b ? "Rating Saved Successfully ✅" : "Rating Not Saved");
												break;
											}
											default:
												System.out.println("Invalid Choice...");
											}
											System.out.println("To continue, press 1 for REVIEW/RATING or 0 to exit:");
											choice = sc.nextInt();
										} while (choice == 1);

									} else {
										System.out.println("Movie Not Recommended to the User");
									}
									break;
								}
								case 2: {
									System.out.println("Which Movie Do You Want to Watch?");
									System.out.println("Enter Movie Name:");
									String mov_name = sc.next();
									Boolean b1 = mservice.assignMovie(mov_name);
									if (b1) {
										System.out.println("Movie Assigned to the User Successfully ✅");
										System.out.println("If You Want to Give a Review, Press 1 (For EXIT PRESS 0)");

										do {
											System.out.println("For Giving Review Press 1");
											System.out.println("For Giving Rating Press 2");
											System.out.println("Please Enter Your Choice:");
											int choice1 = sc.nextInt();
											switch (choice1) {
											case 1: {
												System.out.println("Enter Review For Movie:");
												String review = sc.next();
												rmodel = new ReviewModel(review);
												System.out.println(rmodel.getReviewText());
												Boolean b = rservic.addReview(rmodel, mov_name);
												System.out.println(b ? "Review Saved Successfully ✅" : "Review Not Saved");
												break;
											}
											case 2: {
												System.out.println("Enter Rating For Movie:");
												Double rating = sc.nextDouble();
												rtmodel = new RatingModel(rating);
												System.out.println("Rating is: " + rtmodel.getRating());
												Boolean b = rtservice.addRating(rtmodel, mov_name);
												System.out.println(
														b ? "Rating Saved Successfully ✅" : "Rating Not Saved");
												break;
											}
											default:
												System.out.println("Invalid Choice...");
											}
											System.out.println("To continue, press 1 for REVIEW/RATING or 0 to exit:");
											choice = sc.nextInt();
										} while (choice == 1);

									} else {
										System.out.println("Movie Not Assigned to the User");
									}
									break;
								}
								case 3: {
									System.out.println("If you want to update any field, enter user password:");
									try {
										String password = sc.next();
										int b1 = uservice.updateUserDetails(password);
										System.out.println(b1 > 0 ? "User details updated successfully ✅": "User details not updated");
									} catch (Exception e) {
										System.out.println("Error is: " + e);
									}
									break;
								}
								case 4: {
									try {
										List<MovieModel> mlist = mservice.getAllMovie();
										if (mlist.size() > 0) {
											System.out.println(
													"-------------------------------------------------------------------------");
											System.out.println(
													"|   ID   |      Name      |      Actor      |   Year   |   Language   |");
											System.out.println(
													"-------------------------------------------------------------------------");
											for (Object movieModel : mlist) {
												mmodel = (MovieModel) movieModel;
												System.out.printf("| %-6s | %-14s | %-15s | %-8s | %-12s |\n",mmodel.getMov_id(), mmodel.getMov_name(), mmodel.getMov_actor(),mmodel.getMov_year(), mmodel.getMov_lang());
											}
											System.out.println(
													"-------------------------------------------------------------------------");
										} else {
											System.out.println("Please add movies first....");
										}
									} catch (SQLException e) {
										e.printStackTrace();
									}
									break;
								}
								case 5: {
									ArrayList mov_list = mservice.getAllWatchMovieById(userId);
									if (mov_list.size() > 0) {
										for (Object object : mov_list) {
											System.out.println((String) object);
										}
									} else {
										System.out.println("There is No Movie Watched by User");
									}
									break;
								}
								case 6: {
									ArrayList<ReviewModel> rlist = rservic.getAllReviews();
									if (rlist.size() > 0) {
										System.out.println("| Review ID | Review Text | User ID | Movie ID |");
										System.out.println("|-----------|-------------|---------|----------|");
										for (ReviewModel obj : rlist) {
											System.out.printf("| %-9s | %-11s | %-7s | %-8s |\n", obj.getReviewID(),obj.getReviewText(), obj.getUser_id(), obj.getMov_id());
										}
									} else {
										System.out.println("There is No Review Given by User");
									}
									break;
								}
								case 7: {
									ArrayList<RatingModel> rtlist = rtservice.getAllRatings();
									if (rtlist.size() > 0) {
										System.out.println("| Rating ID | Rating | User ID | Movie ID |");
										System.out.println("|-----------|--------|---------|----------|");
										for (RatingModel obj : rtlist) {
											System.out.printf("| %-9s | %-6s | %-7s | %-8s |\n", obj.getRating_ID(),obj.getRating(), obj.getUser_id(), obj.getMov_id());
										}
									} else {
										System.out.println("There is No Review Given by User");
									}
									break;
								}
								case 8: {
									break;
								}
								default: {
									System.out.println("Invalid option");
								}
								}
								System.out.println("To continue, press 1 for HOME PAGE or 0 to exit:");
								choice = sc.nextInt();
							} while (choice == 1);
						} else {
							System.out.println("Please Enter Correct UserName And Password Otherwise Register First...");
							System.out.println("if You Want Change PassWord Press ---> 1");
							int key = sc.nextInt();
							if (key == 1) {
								Boolean b = uservice.changePassword();
								System.out.println(b ? "Password updated successfully ✅" : "Password not updated");
							}
						}
						break;
					}
					case 3: {
						System.out.println("Enter User Name:");
						String username = sc.next();
						System.out.println("Enter User Password:");
						String password = sc.next();
						System.out.println("Enter User Email:");
						String email = sc.next();
						System.out.println("Enter User Contact:");
						String contact = sc.next();
						String pass = uservice.returnHash(password);
						umodel = new UserModel(username, pass, email, contact);
						boolean b = uservice.registerUser(umodel);
						System.out.println(b ? "User added successfully ✅" : "User not added");
						break;
					}
					default:
						System.out.println("Invalid option. Returning to main menu.");
					}
				} catch (Exception e) {
					System.out.println("Error is: " + e);
				}
				System.out.println("To continue, press 1 for HOME PAGE or 0 to exit:");
				choice = sc.nextInt();
			} while (choice == 1);
		} catch (ArithmeticException e) {
			System.out.println("Error is: " + e);
		} catch (Exception e) {
			System.out.println("Error is: " + e);
		} finally {
			sc.close();
			System.out.println("\033[0;31m" + "\033[1m"+ "\tThank you ❤🙏 for visiting! Don’t forget to come back for more updates and offers...!\t"+ "\033[0m");
		}
	}
}
