package application;

public class InvaildPriceException extends AuctionException {

	@Override
	public String getMessage() {
		return "Price is invalid";
	}

}
