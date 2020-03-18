package application;

import com.tobeagile.training.ebaby.services.PostOffice;

public class AuctionMailSendNoBidder extends AuctionMail {

	public PostOffice sendMail(AuctionItem auctionItem) {
		post = PostOffice.getInstance();
		String sendMessage = "Sorry, your auction for " + auctionItem.auctionName() + " did not have any bidders.";
		String sendEmail = auctionItem.seller().userEmail();
		post.sendEMail(sendEmail, sendMessage);
		return post;
	}

}
