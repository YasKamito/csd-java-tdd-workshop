package application;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tobeagile.training.ebaby.services.AuctionLogger;

public class AuditLogTest {
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
	private AuctionItem.AuctionCategory auctionCategory = AuctionItem.AuctionCategory.OTHER;
	private int price = 1000;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private AuctionItem auctionItem;
	private AuctionItems auctionItems;
	private AuctionLogger auctionLogger;
	private String logFileName = "sale.log";

	@Before
	public void setUp() throws Exception {

		user = new User(firstName, lastName, userEmail, userName, password);
		users = new Users();
		users.register(user);
		users.setSeller(user);
		users.LogIn(userName, password);

		bidder = new User(bFirstName, bLastName, bUserEmail, bUserName, bPassword);
		users.register(bidder);
		users.LogIn(bUserName, bPassword);

		sellerUser = user;
		startDate = LocalDateTime.now().plusDays(1);
		endDate = LocalDateTime.now().plusDays(10);

		auctionLogger = AuctionLogger.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		//auctionLogger.clearLog(logFileName);
	}

	@Test
	public void testLoggingCarSale() {

		auctionCategory = AuctionItem.AuctionCategory.CAR;
		auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate, endDate,
				auctionCategory);
		auctionItems = new AuctionItems();
		try {
			auctionItems.create(auctionItem);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//オークションクローズ
		auctionItem.onStart();
		//正常な入札
		try {
			auctionItems.bid(auctionItem, bidder, 5000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		auctionItem.onClose();
		String message = "Car Sale Price  for " + auctionItem.lastBidUser().userName();
		assertEquals(true, auctionLogger.findMessage(logFileName, message));
	}

	@Test
	public void testLoggingSaleOver10k() {

		auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate, endDate,
				auctionCategory);
		auctionItems = new AuctionItems();
		try {
			auctionItems.create(auctionItem);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//オークションクローズ
		auctionItem.onStart();
		//正常な入札
		try {
			auctionItems.bid(auctionItem, bidder, 10000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		auctionItem.onClose();
		String message = "Over 10k Price for " + auctionItem.lastBidUser().userName();
		assertEquals(true, auctionLogger.findMessage(logFileName, message));
	}

	@Test
	public void testLoggingCarSaleOver10k() {

		auctionCategory = AuctionItem.AuctionCategory.CAR;
		auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate, endDate,
				auctionCategory);
		auctionItems = new AuctionItems();
		try {
			auctionItems.create(auctionItem);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//オークションクローズ
		auctionItem.onStart();
		//正常な入札
		try {
			auctionItems.bid(auctionItem, bidder, 10000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		auctionItem.onClose();
		String message = "Car Sale Price  for " + auctionItem.lastBidUser().userName();
		assertEquals(true, auctionLogger.findMessage(logFileName, message));
	}

	@Test
	public void testNoLogging() {

		auctionItem = new AuctionItem(auctionName, sellerUser, auctionDesctiption, price, startDate, endDate,
				auctionCategory);
		auctionItems = new AuctionItems();
		try {
			auctionItems.create(auctionItem);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		//オークションクローズ
		auctionItem.onStart();
		//正常な入札
		try {
			auctionItems.bid(auctionItem, bidder, 5000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		auctionItem.onClose();
		auctionLogger.clearLog(logFileName);
		assertEquals(false, new File(logFileName).exists());
	}
}
