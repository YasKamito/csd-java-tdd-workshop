package application;

import java.time.LocalDateTime;

import com.tobeagile.training.ebaby.services.PostOffice;

public class AuctionItem {

	private User sellerUser;
	private String auctionName;
	private String auctionDescription;
	private long price;
	private LocalDateTime starDate;
	private LocalDateTime endDate;
	private State auctionState = State.BEFORE;
	private User lastBidUser = null;
	private PostOffice postOffice;
	private long sellerPrice = 0;
	private AuctionCategory auctionCategry = AuctionCategory.OTHER;
	private long shippingFee = 0;
	private long laxuryTax = 0;

	enum State {
		BEFORE, STARTED, CLOSED
	};

	enum AuctionCategory {
		CAR, DOWNLOADSOFTWARE, OTHER
	};

	public AuctionItem(String auctionName, User sellerUser, String auctionDesctiption, int price,
			LocalDateTime startDate, LocalDateTime endDate) {
		this.sellerUser = sellerUser;
		this.auctionName = auctionName;
		this.auctionDescription = auctionDesctiption;
		this.price = price;
		this.starDate = startDate;
		this.endDate = endDate;
	}

	public AuctionItem(String auctionName, User sellerUser, String auctionDesctiption, int price,
			LocalDateTime startDate, LocalDateTime endDate, AuctionCategory auctionCategory) {
		this.sellerUser = sellerUser;
		this.auctionName = auctionName;
		this.auctionDescription = auctionDesctiption;
		this.price = price;
		this.starDate = startDate;
		this.endDate = endDate;
		this.auctionCategry = auctionCategory;
	}

	public User seller() {
		return this.sellerUser;
	}

	public String auctionName() {
		return this.auctionName;
	}

	public String acutionDescription() {
		return this.auctionDescription;
	}

	public long price() {
		return this.price;
	}

	public LocalDateTime startDate() {
		return this.starDate;
	}

	public LocalDateTime endDate() {
		return this.endDate;
	}

	public State state() {
		return this.auctionState;
	}

	public void onStart() {
		this.auctionState = State.STARTED;
	}

	public void onClose() {
		this.auctionState = State.CLOSED;
		AuctionMail auctionMail = new AuctionMailFactory().getInctance(this);
		this.postOffice = auctionMail.sendMail(this);
		CalcFeeFactory factory = CalcFeeFactory.getInstance(this);
		this.sellerPrice = factory.calcSellerFee(this);
		this.shippingFee = factory.calcShippingFee(this);
		this.laxuryTax = factory.calcLuxuryTax(this);
		LogFactory.getInstance(this).logging(this);
		;
	}

	public void bid(long price, User user) {
		this.price = price;
		this.lastBidUser = user;
	}

	public User lastBitUser() {
		return this.lastBidUser;
	}

	public PostOffice postOffice() {
		return this.postOffice;
	}

	public User lastBidUser() {
		return this.lastBidUser;
	}

	public long sellerPrice() {
		return this.sellerPrice;
	}

	public long shippingFee() {
		return this.shippingFee;
	}

	public long laxuryTax() {
		return this.laxuryTax;
	}

	public AuctionCategory getAuctionCategory() {
		return this.auctionCategry;
	}

}