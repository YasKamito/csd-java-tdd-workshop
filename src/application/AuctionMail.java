package application;

import com.tobeagile.training.ebaby.services.PostOffice;

public abstract class AuctionMail {
	protected PostOffice post;

	public PostOffice sendMail(AuctionItem auctionItem) {
		return post;
	}
}
