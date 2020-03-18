package application;

public class InvalidDateException extends AuctionException {

	@Override
	public String getMessage() {
		return "Invalid Date";
	}

}
