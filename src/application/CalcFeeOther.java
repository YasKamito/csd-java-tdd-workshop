package application;

public class CalcFeeOther extends CalcFeeFactory {

	@Override
	public long calcSellerFee(AuctionItem auctionItem) {
		return new SellerFeeOther().calc(auctionItem);
	}

	@Override
	public long calcShippingFee(AuctionItem auctionItem) {
		return new ShippingFeeOther().calc(auctionItem);
	}

	@Override
	public long calcLuxuryTax(AuctionItem auctionItem) {
		return new LuxuryTaxOther().calc(auctionItem);
	}

}
