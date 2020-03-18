package application;

import java.util.ArrayList;

public class Users {

	ArrayList<User> users = new ArrayList<User>();

	public boolean register(User user) {
		// TODO 自動生成されたメソッド・スタブ

		User result = this.findByUserName(user.userName());
		if (result == null) {
			users.add(user);
			return true;
		}
		return false;
	}

	public User findByUserName(String userName) {
		// TODO 自動生成されたメソッド・スタブ
		for (User user : users) {
			if (user.userName().equals(userName)) {
				return user;
			}
		}
		return null;
	}

	public User LogIn(String userName, String password) {
		// TODO 自動生成されたメソッド・スタブ
		User user = this.findByUserName(userName);
		if (user == null) {
			return null;
		} else if (user.password().equals(password)) {
			user.status(User.LoginStatus.Login);
			return user;
		}
		return null;
	}

	public void logOut(User user) {
		// TODO 自動生成されたメソッド・スタブ
		user.status(User.LoginStatus.Logout);
	}

	public void setSeller(User user) {
		// TODO 自動生成されたメソッド・スタブ
		user.setSeller(true);
	}

}
