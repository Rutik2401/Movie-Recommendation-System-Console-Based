package org.tech.model;

public class UserModel {
	// Instance Variable
	private int user_id;
	private String username;
	private String password;
	private String email;
	private String contact;

	// Paramiterized Constructor
	public UserModel(String username, String password, String email, String contact) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.contact = contact;
	}

	// Default Constructor
	public UserModel() {
		super();
	}

	// Getter and Setter
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
