package application;

public class NotSellerException extends AuctionException {

	@Override
	public String getMessage() {
		return "Not seller user";
	}

}
