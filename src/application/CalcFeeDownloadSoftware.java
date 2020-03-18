package application;

public class CalcFeeDownloadSoftware extends CalcFeeFactory {

	@Override
	public long calcSellerFee(AuctionItem auctionItem) {
		return new SellerFeeDowlloadSoftware().calc(auctionItem);
	}

	@Override
	public long calcShippingFee(AuctionItem auctionItem) {
		return new ShippingFeeDownloadSoftware().calc(auctionItem);
	}

	@Override
	public long calcLuxuryTax(AuctionItem auctionItem) {
		return new LuxuryTaxDownloadSoftware().calc(auctionItem);
	}

}
