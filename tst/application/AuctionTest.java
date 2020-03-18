package application;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuctionTest {
	private String firstName = "Taro";
	private String lastName = "Yamada";
	private String userName = "yamada01";
	private String userEmail = "yamada@example.com";
	private String password = "yamada1234";
	private User user;
	private Users users;
	private User bidder;

	private String bFirstName = "Hanako";
	private String bLastName = "Sato";
	private String bUserName = "Sato01";
	private String bUserEmail = "sato@example.com";
	private String bPassword = "sato1234";

	private String auctionName = "Sample01";
	private User sellerUser;
	private String auctionDesctiption = "sampleDescription";
	private int price = 1000;
	private LocalDateTime startDate = LocalDateTime.now().plusDays(1);
	private LocalDateTime endDate = LocalDateTime.now().plusDays(10);

	@Before
	public void setUp() throws Exception {

		user = new User(firstName, lastName, userEmail, userName, password);
		//正常系
		users = new Users();
		users.register(user);
		sellerUser = user;

		bidder = new User(bFirstName, bLastName, bUserEmail, bUserName, bPassword);
		users.register(bidder);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetSeller() {
		users.setSeller(user);
		assertEquals(true, user.isSeller());
	}

	@Test
	public void testNewAuction() {
		users.setSeller(user);
		users.LogIn(userName, password);
		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate);
		assertEquals(auctionName, auctionItem.auctionName());
		assertEquals(sellerUser, auctionItem.seller());
		assertEquals(auctionDesctiption, auctionItem.acutionDescription());
		assertEquals(price, auctionItem.price());
		assertEquals(startDate, auctionItem.startDate());
		assertEquals(endDate, auctionItem.endDate());
	}

	@Test
	public void testCreateAuction() {

		//認証売り主がオークションを作成できること(sellerUser)
		users.setSeller(user);
		users.LogIn(userName, password);
		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate);
		AuctionItems auctionItems = new AuctionItems();
		try {
			assertEquals(true, auctionItems.create(auctionItem));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//認証売り主ではない場合、オークションが作成できないこと(bidder)
		//NotSellerException
		AuctionItem auctionItem2 = new AuctionItem(auctionName, bidder, auctionDesctiption, price, startDate, endDate);
		try {
			auctionItems.create(auctionItem2);
			fail();
		} catch (NotSellerException e) {
			assertEquals("Not seller user", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testSellerNotLogin() {
		//認証売り主が未ログイン状態の場合、オークションが作成できないこと
		//NotLoginException
		users.setSeller(user);
		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate);
		AuctionItems auctionItems = new AuctionItems();

		try {
			assertEquals(false, auctionItems.create(auctionItem));
			fail();
		} catch (NotLoginException e) {
			assertEquals("Not login user", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testInvalidStartDate() {
		//開始日付が現在日付よりも過去の場合、オークションが生成できないこと
		//InvalidDateException
		users.setSeller(user);
		users.LogIn(userName, password);
		startDate = LocalDateTime.now().minusDays(1);
		endDate = LocalDateTime.now().plusDays(10);
		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate);
		AuctionItems auctionItems = new AuctionItems();

		try {
			assertEquals(false, auctionItems.create(auctionItem));
			fail();
		} catch (InvalidDateException e) {
			assertEquals("Invalid Date", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testInvalidEndDate() {
		//終了日付が現在日付よりも過去の場合、オークションが生成できないこと
		users.setSeller(user);
		users.LogIn(userName, password);
		LocalDateTime startDate = LocalDateTime.now().plusDays(2);
		LocalDateTime endDate = LocalDateTime.now().plusDays(1);
		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate);
		AuctionItems auctionItems = new AuctionItems();

		try {
			assertEquals(false, auctionItems.create(auctionItem));
			fail();
		} catch (InvalidDateException e) {
			assertEquals("Invalid Date", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testCreateAuctionNormal() {
		//正常にオークションが生成できること
		users.setSeller(user);
		users.LogIn(userName, password);
		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate);
		AuctionItems auctionItems = new AuctionItems();
		try {
			assertEquals(true, auctionItems.create(auctionItem));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//オークション生成直後は、オークションのステータスが"BEFORE"であること
		assertEquals(AuctionItem.State.BEFORE, auctionItem.state());
		//オークション開始後、オークションのステータスが"STARTED"であること
		auctionItem.onStart();
		assertEquals(AuctionItem.State.STARTED, auctionItem.state());
		//オークション終了後、オークションのステータスが"CLOSED"であること
		auctionItem.onClose();
		assertEquals(AuctionItem.State.CLOSED, auctionItem.state());

	}

	@Test
	public void testBidToAuction() {
		//開始したオークションに対して入札を行う
		users.setSeller(user);
		users.LogIn(userName, password);
		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate);
		AuctionItems auctionItems = new AuctionItems();
		try {
			auctionItems.create(auctionItem);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			assertEquals(false, auctionItems.bid(auctionItem, bidder, 2000));
			fail();
		} catch (NotLoginException e) {
			assertEquals("Not login user", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//オークション開始チェック
		users.LogIn(bUserName, bPassword);
		try {
			assertEquals(false, auctionItems.bid(auctionItem, bidder, 2000));
			fail();
		} catch (NotStartException e) {
			assertEquals("Not Start Auction", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//現在価格より低い金額の入札はできない
		auctionItem.onStart();
		try {
			assertEquals(false, auctionItems.bid(auctionItem, bidder, 100));
			fail();
		} catch (InvaildPriceException e) {
			assertEquals("Price is invalid", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//正常な入札
		try {
			assertEquals(true, auctionItems.bid(auctionItem, bidder, 2000));
			assertEquals(2000, auctionItem.price());
			assertEquals(bidder.userName(), auctionItem.lastBitUser().userName());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAuctionWithNoBidsNotifiesSeller() {
		// オークションが閉じ時に落札者がいなかったことを売り主にメールで通知
		users.setSeller(user);
		users.LogIn(userName, password);

		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate);
		AuctionItems auctionItems = new AuctionItems();
		try {
			auctionItems.create(auctionItem);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//オークションクローズ
		auctionItem.onStart();
		auctionItem.onClose();
		String sendMessage = "Sorry, your auction for " + auctionItem.auctionName() + " did not have any bidders.";
		assertEquals(true, auctionItem.postOffice().doesLogContain(auctionItem.seller().userEmail(), sendMessage));

	}

	@Test
	public void testAuctionWithBidNotifiesHighbiderAndSeller() {
		// オークションが閉じ時に最高落札者と売り主にメールで通知
		users.setSeller(user);
		users.LogIn(userName, password);
		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate);
		AuctionItems auctionItems = new AuctionItems();
		try {
			auctionItems.create(auctionItem);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		users.LogIn(bUserName, bPassword);
		//オークションクローズ
		auctionItem.onStart();
		//正常な入札
		try {
			auctionItems.bid(auctionItem, bidder, 2000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		auctionItem.onClose();
		String sendSellerMessage = "Your " + auctionItem.auctionName() + " autction sold to bidder "
				+ auctionItem.lastBitUser().userEmail() +
				" for " + auctionItem.price() + ".";
		assertEquals(true,
				auctionItem.postOffice().doesLogContain(auctionItem.seller().userEmail(), sendSellerMessage));

		String sendBidderMessage = "Congratulations! You won an auction for a " + auctionItem.auctionName() + " from "
				+ auctionItem.seller().userEmail() +
				" for " + auctionItem.price() + ".";
		assertEquals(true,
				auctionItem.postOffice().doesLogContain(auctionItem.lastBitUser().userEmail(), sendBidderMessage));
	}

	@Test
	public void testFeesForDownloadableSoftware() {
		// ダウンロードソフトウェアの手数料のテスト
		// オークションが閉じ時に最高落札者と売り主にメールで通知
		users.setSeller(user);
		users.LogIn(userName, password);
		AuctionItem.AuctionCategory auctionCategory = AuctionItem.AuctionCategory.DOWNLOADSOFTWARE;
		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate, auctionCategory);
		AuctionItems auctionItems = new AuctionItems();
		try {
			auctionItems.create(auctionItem);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		users.LogIn(bUserName, bPassword);
		//オークションクローズ
		auctionItem.onStart();
		//正常な入札
		try {
			auctionItems.bid(auctionItem, bidder, 3000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		auctionItem.onClose();
		// 落札金額は２％引かれる
		assertEquals((long) Math.floor(3000 * 0.98), auctionItem.sellerPrice());
		//落札者送料は追加されるない
		assertEquals(0, auctionItem.shippingFee());
		//ぜいたく税はない。
		assertEquals(0, auctionItem.laxuryTax());

	}

	@Test
	public void testFeeForNonDownloadbleSoftwareOrCar() {
		// ダウンロードと車以外の手数料のテスト
		// オークションが閉じ時に最高落札者と売り主にメールで通知
		users.setSeller(user);
		users.LogIn(userName, password);
		AuctionItem.AuctionCategory auctionCategory = AuctionItem.AuctionCategory.OTHER;
		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate, auctionCategory);
		AuctionItems auctionItems = new AuctionItems();
		try {
			auctionItems.create(auctionItem);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		users.LogIn(bUserName, bPassword);
		//オークションクローズ
		auctionItem.onStart();
		//正常な入札
		try {
			auctionItems.bid(auctionItem, bidder, 2000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		auctionItem.onClose();
		// 落札金額は２％引かれる
		assertEquals((long) Math.floor(2000 * 0.98), auctionItem.sellerPrice());
		//落札者送料は10＄追加される
		assertEquals(10, auctionItem.shippingFee());
		//ぜいたく税を計算する。
		assertEquals(0, auctionItem.laxuryTax());
	}

	@Test
	public void testFeesForCarUnder50k() {
		// ５万ドル以下の車の手数料のテスト
		// オークションが閉じ時に最高落札者と売り主にメールで通知
		users.setSeller(user);
		users.LogIn(userName, password);
		AuctionItem.AuctionCategory auctionCategory = AuctionItem.AuctionCategory.CAR;
		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate, auctionCategory);
		AuctionItems auctionItems = new AuctionItems();

		try {
			auctionItems.create(auctionItem);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		users.LogIn(bUserName, bPassword);
		//オークションクローズ
		auctionItem.onStart();
		//正常な入札
		try {
			auctionItems.bid(auctionItem, bidder, 40000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		auctionItem.onClose();
		// 落札金額は２％引かれる
		assertEquals((long) Math.floor(40000 * 0.98), auctionItem.sellerPrice());
		//落札者送料は10＄追加される
		assertEquals(1000, auctionItem.shippingFee());
		//ぜいたく税を計算する。
		assertEquals(0, auctionItem.laxuryTax());
	}

	@Test
	public void testFeesForCarOver50k() {
		// ５万ドル以上の車の手数料のテスト
		// オークションが閉じ時に最高落札者と売り主にメールで通知
		users.setSeller(user);
		users.LogIn(userName, password);
		AuctionItem.AuctionCategory auctionCategory = AuctionItem.AuctionCategory.CAR;
		AuctionItem auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate,
				endDate, auctionCategory);
		AuctionItems auctionItems = new AuctionItems();

		try {
			auctionItems.create(auctionItem);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		users.LogIn(bUserName, bPassword);
		auctionItem.onStart();
		try {
			auctionItems.bid(auctionItem, bidder, 50000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		auctionItem.onClose();
		// 落札金額は２％引かれる
		assertEquals((long) Math.floor(50000 * 0.98), auctionItem.sellerPrice());
		//落札者送料は10＄追加される
		assertEquals(1000, auctionItem.shippingFee());
		//ぜいたく税を計算する。
		assertEquals((long) Math.floor(auctionItem.price() * 0.04), auctionItem.laxuryTax());
	}

}
