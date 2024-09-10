package org.tech.service;
import java.sql.SQLException;
import java.util.List;
import org.tech.model.UserModel;
import org.tech.repository.UserRepository;

public class UserServices {
	UserRepository urepo = new UserRepository();

	public boolean registerUser(UserModel umodel) throws SQLException {

		return urepo.registerUser(umodel);
	}

	public int updateUserDetails(String password) throws SQLException {
		return urepo.updateUserDetails(password);
	}

	public List<UserModel> getAllregisterUser() {
	
		return urepo.getAllregisterUser();
	}

	public int deleteUserById(int id) {
		
		return urepo.deleteUserById(id);
	}

	public int authenticateUser(String userName, String userPass) {
		return urepo.authenticateUser( userName,  userPass);
	}

	public String returnHash(String password) {
		String hashPassword=BCrypt.hashpw(password, BCrypt.gensalt());
		return hashPassword;
	}

	public Boolean changePassword() {
		return urepo.changePassword();
	}

}
