package main.sample;

import twitter4j.IDs;
import twitter4j.TwitterException;

public class FriendGetSample extends AbstractTwitter{

	public IDs getFriends() {
		twitter.setOAuthAccessToken(accessTokenList.get(0));
		try {
			return twitter.getFriendsIDs(0);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public IDs getFriends(int id) {
		twitter.setOAuthAccessToken(accessTokenList.get(id));
		try {
			return twitter.getFriendsIDs(0);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public IDs getFriends(String screenName, int i) {
		twitter.setOAuthAccessToken(accessTokenList.get(i));
		try {
			return twitter.getFriendsIDs(screenName, -1);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FriendGetSample friendGetSample = new FriendGetSample();
		IDs ids = friendGetSample.getFriends("ukyo", 2);
		
		for (long id: ids.getIDs()) {
			System.out.println(id);
		}
		System.out.println(ids.getIDs().length);
	}

}
