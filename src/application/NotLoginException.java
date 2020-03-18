package application;

public class NotLoginException extends AuctionException {

	@Override
	public String getMessage() {
		return "Not login user";
	}

}
