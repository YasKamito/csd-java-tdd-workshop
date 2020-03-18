package application;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegistorUserTest {

	private String firstName;
	private String lastName;
	private String userName;
	private String userEmail;
	private String password;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateUser() {
		firstName = "Taro";
		lastName = "Yamada";
		userName = "yamada01";
		userEmail = "yamada@example.com";
		password = "yamada1234";

		User user = new User(firstName, lastName, userEmail, userName, password);
		assertEquals(firstName, user.firstName());
		assertEquals(lastName, user.lastName());
		assertEquals(userName, user.userName());
		assertEquals(userEmail, user.userEmail());
		assertEquals(password, user.password());

		firstName = "Hanako";
		lastName = "Sato";
		userName = "Sato01";
		userEmail = "sato@example.com";
		password = "sato1234";

		user = new User(firstName, lastName, userEmail, userName, password);
		assertEquals(firstName, user.firstName());
		assertEquals(lastName, user.lastName());
		assertEquals(userName, user.userName());
		assertEquals(userEmail, user.userEmail());
		assertEquals(password, user.password());

		Users users = new Users();

		// Usersコレクションに追加できること
		assertEquals(true, users.register(user));

		// ユーザー名でコレクションを検索できること
		User user1 = users.findByUserName(user.userName());
		assertEquals(user1.userName(), userName);

		User user2 = users.findByUserName("Ito01");
		assertNull(user2);

		// 既に存在するユーザー名では登録できないこと
		assertEquals(false, users.register(user));
	}

}
