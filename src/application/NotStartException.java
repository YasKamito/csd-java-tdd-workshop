package application;

public class NotStartException extends AuctionException {

	@Override
	public String getMessage() {
		return "Not Start Auction";
	}

}
