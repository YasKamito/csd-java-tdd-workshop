package application;

import com.tobeagile.training.ebaby.services.PostOffice;

public class AuctionMailSellerBidder extends AuctionMail {

	public PostOffice sendMail(AuctionItem auctionItem) {
		// send to seller
		post = PostOffice.getInstance();
		String sendEmail = auctionItem.seller().userEmail();
		String sendMessage = "Your " + auctionItem.auctionName() + " autction sold to bidder "
				+ auctionItem.lastBitUser().userEmail() +
				" for " + auctionItem.price() + ".";
		post.sendEMail(sendEmail, sendMessage);

		// send to bidder
		sendEmail = auctionItem.lastBitUser().userEmail();
		sendMessage = "Congratulations! You won an auction for a " + auctionItem.auctionName() + " from "
				+ auctionItem.seller().userEmail() +
				" for " + auctionItem.price() + ".";
		post.sendEMail(sendEmail, sendMessage);

		return post;
	}
}
