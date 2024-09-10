package org.tech.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.tech.config.DBHelper;
import org.tech.exceptions.UserNotFoundException;
import org.tech.model.UserModel;
import org.tech.service.BCrypt;

public class UserRepository extends DBHelper {
	Scanner sc = new Scanner(System.in);

	public boolean registerUser(UserModel umodel) throws SQLException {
		String sql = "insert into UserMaster (username, password, email, contact) values (?, ?, ?, ?)";
		stmt = conn.prepareStatement(sql);

		stmt.setString(1, umodel.getUsername());
		stmt.setString(2, umodel.getPassword());
		stmt.setString(3, umodel.getEmail());
		stmt.setString(4, umodel.getContact());

		int value = stmt.executeUpdate();
		return value > 0;
	}

	public int updateUserDetails(String password) throws SQLException {
		int value = -1;
		if (password != null) {
			System.out.println("user input true");
			stmt = conn.prepareStatement("SELECT password FROM UserMaster WHERE password=?");
			stmt.setString(1, password);
			rs = stmt.executeQuery();

			if (rs.next()) {
				System.out.println("User authenticated successfully");

				int choice;
				do {
					System.out.println("What do you want to update? Please select one of the options below:");
					System.out.println("1. User Name\n2. Change Email\n3. Change Contact");
					System.out.println("4.EXIT 🔚❌");
					System.out.println("Enter your choice:");
					choice = sc.nextInt();

					switch (choice) {
					case 1: {
						System.out.println("Enter new user name:");
						String username = sc.next();
						stmt = conn.prepareStatement("UPDATE UserMaster SET username=? WHERE password=?");
						stmt.setString(1, username);
						stmt.setString(2, password);
						value = stmt.executeUpdate();
						System.out.println(value > 0 ? "User name updated successfully" : "User name not updated");
						break;
					}
					case 2: {
						System.out.println("Enter new email:");
						String email = sc.next();
						stmt = conn.prepareStatement("UPDATE UserMaster SET email=? WHERE password=?");
						stmt.setString(1, email);
						stmt.setString(2, password);
						value = stmt.executeUpdate();
						System.out.println(value > 0 ? "User email updated successfully" : "User email not updated");
						break;
					}
					case 3: {
						System.out.println("Enter new contact number:");
						String contact = sc.next();
						stmt = conn.prepareStatement("UPDATE UserMaster SET contact=? WHERE password=?");
						stmt.setString(1, contact);
						stmt.setString(2, password);
						value = stmt.executeUpdate();
						System.out.println(value > 0 ? "User contact updated successfully" : "User contact not updated");
						break;
					}
					case 4:{
						break;
					}
					default:
						System.out.println("Please enter a valid choice for updating.");
					}
					System.out.println("To continue updating, press 1 (Press any other key to exit)");
					choice = sc.nextInt();
				} while (choice == 1);

			} else {
				changePassword();
			}
		} else {
			System.out.println("Password cannot be null");
		}
		return value;
	}

	public List<UserModel> getAllregisterUser() {
		List<UserModel> ulist = new ArrayList<UserModel>();
		try {
			stmt = conn.prepareStatement("select * from Usermaster");
			rs = stmt.executeQuery();
			while (rs.next()) {
				UserModel umodel = new UserModel();
				umodel.setUser_id(rs.getInt(1));
				umodel.setUsername(rs.getString(2));
				umodel.setPassword(rs.getString(3));
				umodel.setEmail(rs.getString(4));
				umodel.setContact(rs.getString(5));
				ulist.add(umodel);
			}
			return ulist.size() > 0 ? ulist : ulist;
		} catch (SQLException e) {
			e.printStackTrace();
			return ulist;
		}

	}

	public int deleteUserById(int id) {
		try {
			stmt = conn.prepareStatement("Delete from Usermaster Where user_id=?");
			stmt.setInt(1, id);
			int value = stmt.executeUpdate();
			return value;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int authenticateUser(String userName, String userPass) {//rutik
		if (userName != null && userPass != null) {
			try {
				stmt = conn.prepareStatement("SELECT user_id,password FROM UserMaster WHERE username = ?");
				stmt.setString(1, userName);
				rs = stmt.executeQuery();
				if (rs.next()) {
					String dbPass = rs.getString("password");//1vsaeawr43564786fbvsr23t54tasdfghjkl2345678xcvbnm 
					return BCrypt.checkpw(userPass, dbPass) ? rs.getInt(1) : 0; //return Id if User is True Person
				}
				else {					
					throw new UserNotFoundException();
				}
			}
			catch (SQLException e) {
				System.out.println("Error: " + e);
				return 0;
			}
			catch (UserNotFoundException e) {
				System.out.println("Error is:" + e.UserNotFoundException());
			}
		} else {
			System.out.println("Please enter correct username and password.");
			return 0;
		}
		return 0;
	}

	public Boolean changePassword() {
		System.out.println("Enter your contact number:");
		String contact = sc.next();
		int value;
		System.out.println("Enter the 4-digit OTP:");
		int otp = sc.nextInt();
		if (otp == 2401) {
			try {
				stmt = conn.prepareStatement("select contact from UserMaster where contact=?");
				stmt.setString(1, contact);
				rs = stmt.executeQuery();
				while (rs.next()) {
					String c = rs.getString("contact");
					if (c.equalsIgnoreCase(contact)) {
						System.out.println("Enter your new password:");
						String newPassword = sc.next();
						stmt = conn.prepareStatement("update UserMaster set password=? where contact=?");
						stmt.setString(1, newPassword);
						stmt.setString(2, contact);
						value = stmt.executeUpdate();
						return value>0 ?true:false;

					} else {
						System.out.println("Incorrect contact number.");
					}
				}
			} catch (SQLException e) {
				System.out.println("Error is:" + e);
			}

		} else {
			System.out.println("Invalid OTP.");
		}
		return false;
	}

}