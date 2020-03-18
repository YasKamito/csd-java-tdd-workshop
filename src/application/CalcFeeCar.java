package application;

public class CalcFeeCar extends CalcFeeFactory {

	@Override
	public long calcSellerFee(AuctionItem auctionItem) {
		return new SellerFeeCar().calc(auctionItem);
	}

	@Override
	public long calcShippingFee(AuctionItem auctionItem) {
		return new ShippingFeeCar().calc(auctionItem);
	}

	@Override
	public long calcLuxuryTax(AuctionItem auctionItem) {
		return new LuxuryTaxCar().calc(auctionItem);
	}

}
