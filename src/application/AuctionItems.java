package application;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AuctionItems {

	ArrayList<AuctionItem> auctions = new ArrayList<AuctionItem>();

	public boolean create(AuctionItem auctionItem) throws NotSellerException, NotLoginException, InvalidDateException {

		if (!auctionItem.seller().isSeller()) {
			throw new NotSellerException();
		}
		if (auctionItem.seller().isLogin() == User.LoginStatus.Logout) {
			throw new NotLoginException();
		}

		//開始日付チェック
		if (auctionItem.startDate().compareTo(LocalDateTime.now()) < 0) {
			throw new InvalidDateException();
		}

		//終了日付チェック
		if (auctionItem.startDate().compareTo(auctionItem.endDate()) > 0) {
			throw new InvalidDateException();
		}

		this.auctions.add(auctionItem);
		return true;
	}

	public boolean bid(AuctionItem auctionItem, User user, int price)
			throws NotLoginException, NotStartException, InvaildPriceException {
		if (user.isLogin() == User.LoginStatus.Logout) {
			throw new NotLoginException();
		}
		if (auctionItem.state() != AuctionItem.State.STARTED) {
			throw new NotStartException();
		}
		if (auctionItem.price() >= price) {
			throw new InvaildPriceException();
		}

		auctionItem.bid(price, user);
		return true;
	}

}
