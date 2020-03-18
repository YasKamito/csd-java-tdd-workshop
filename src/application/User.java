package application;

public class User {
	private String firstName;
	private String lastName;
	private String userEmail;
	private String userName;
	private String password;
	private LoginStatus status;
	private boolean seller;

	enum LoginStatus {
		Login, Logout
	};

	public User(String firstName, String lastName, String userEmail, String userName, String password) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
		this.userName = userName;
		this.password = password;
		this.status = LoginStatus.Logout;

	}

	public String firstName() {
		return this.firstName;
	}

	public String lastName() {
		return this.lastName;
	}

	public String userName() {
		return this.userName;
	}

	public String userEmail() {
		return this.userEmail;
	}

	public String password() {
		return this.password;
	}

	public LoginStatus isLogin() {
		return this.status;
	}

	public void status(LoginStatus login) {
		this.status = login;
	}

	public void setSeller(boolean seller) {
		this.seller = seller;
	}

	public boolean isSeller() {
		return seller;
	}

}
