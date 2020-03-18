package application;

public class AuctionMailFactory {
	public AuctionMail getInctance(AuctionItem auctionItem) {

		AuctionMail auctionMail = null;

		if (auctionItem.lastBidUser() == null) {
			auctionMail = new AuctionMailSendNoBidder();
		} else {
			auctionMail = new AuctionMailSellerBidder();
		}
		return auctionMail;
	}

}
