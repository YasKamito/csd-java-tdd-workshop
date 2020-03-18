package application;

public class SellerFeeDowlloadSoftware extends SellerFee {

	@Override
	public long calc(AuctionItem auctionItem) {
		return (long) Math.floor(auctionItem.price() * 0.98);
	}

}