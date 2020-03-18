package application;

import com.tobeagile.training.ebaby.services.AuctionLogger;

public class LogOver10k extends LogFactory {

	@Override
	public void logging(AuctionItem auctionItem) {
		AuctionLogger auctionLogger = AuctionLogger.getInstance();
		String message = "Over 10k Price for " + auctionItem.lastBidUser().userName();
		auctionLogger.log(fileName, message);
	}

}
