package application;

public class ShippingFeeCar extends ShippingFee {

	@Override
	public long calc(AuctionItem auctionItem) {
		return 1000;
	}

}
