package application;

import application.AuctionItem.AuctionCategory;

public abstract class CalcFeeFactory {

	public abstract long calcSellerFee(AuctionItem auctionItem);

	public abstract long calcShippingFee(AuctionItem auctionItem);

	public abstract long calcLuxuryTax(AuctionItem auctionItem);

	public static CalcFeeFactory getInstance(AuctionItem auctionItem) {

		AuctionCategory auctionCategory = auctionItem.getAuctionCategory();

		switch (auctionCategory) {
		case CAR:
			return new CalcFeeCar();

		case DOWNLOADSOFTWARE:
			return new CalcFeeDownloadSoftware();

		case OTHER:
			return new CalcFeeOther();
		}
		return null;
	}

}
