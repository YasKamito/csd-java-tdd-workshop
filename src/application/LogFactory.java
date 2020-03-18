package application;

import application.AuctionItem.AuctionCategory;

public abstract class LogFactory {
	protected static String fileName = "sale.log";

	public abstract void logging(AuctionItem auctionItem);

	public static LogFactory getInstance(AuctionItem auctionItem) {

		AuctionCategory auctionCategory = auctionItem.getAuctionCategory();

		if (auctionCategory == AuctionItem.AuctionCategory.CAR) {
			return new LogCar();
		} else {
			if (auctionItem.price() >= 10000) {
				return new LogOver10k();
			} else {
				return new LogUnder10k();
			}

		}
	}
}
