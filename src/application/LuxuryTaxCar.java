package application;

public class LuxuryTaxCar extends LuxuryTax {

	@Override
	public long calc(AuctionItem auctionItem) {
		if (auctionItem.price() >= 50000) {
			return (long) Math.floor(auctionItem.price() * 0.04);
		}
		return 0;
	}

}
