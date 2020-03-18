package application;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String firstName = "Taro";
		String lastName = "Yamada";
		String userName = "yamada01";
		String userEmail = "yamada@example.com";
		String password = "yamada1234";

		User user = new User(firstName, lastName, userEmail, userName, password);
		//正常系
		Users users = new Users();
		users.register(user);
		User loginresult1 = users.LogIn(userName, password);
		assertEquals(userName, loginresult1.userName());
		//パスワード間違い
		User loginresult2 = users.LogIn(userName, "aaa");
		assertNull(loginresult2);
		//ユーザーいない場合
		User loginresult3 = users.LogIn("aaa", password);
		assertNull(loginresult3);

		//ユーザーログイン状態テスト
		//未ログイン状態
		firstName = "Hanako";
		lastName = "Sato";
		userName = "Sato01";
		userEmail = "sato@example.com";
		password = "sato1234";
		User user2 = new User(firstName, lastName, userEmail, userName, password);
		users.register(user2);
		assertEquals(User.LoginStatus.Logout, user2.isLogin());
		//ログイン中
		User loginuser = users.LogIn(userName, password);
		assertEquals(User.LoginStatus.Login, loginuser.isLogin());

		//ログアウト中
		users.logOut(user2);
		assertEquals(User.LoginStatus.Logout, user2.isLogin());
	}

}
