package application;

public class ShippingFeeOther extends ShippingFee {

	@Override
	public long calc(AuctionItem auctionItem) {
		return 10;
	}

}
