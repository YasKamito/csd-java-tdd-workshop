package application;

public abstract class LuxuryTax {
	protected long fee;

	public long calc(AuctionItem auctionItem) {
		return fee;
	}
}
