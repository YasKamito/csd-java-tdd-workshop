package application;

import com.tobeagile.training.ebaby.services.AuctionLogger;

public class LogCar extends LogFactory {

	@Override
	public void logging(AuctionItem auctionItem) {
		AuctionLogger auctionLogger = AuctionLogger.getInstance();
		String message = "Car Sale Price  for " + auctionItem.lastBidUser().userName();
		auctionLogger.log(fileName, message);
	}

}
