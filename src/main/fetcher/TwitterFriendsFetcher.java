package main.fetcher;

import java.util.Queue;

import scala.actors.threadpool.LinkedBlockingQueue;
import tokyocabinet.HDB;
import twitter4j.TwitterException;
import main.db.Neo4jDb;
import main.sample.AbstractTwitter;

public class TwitterFriendsFetcher extends AbstractTwitter{
	private int count;
	private int userCount;
	private int limit;
	private Neo4jDb db;
	private HDB hdb;
	private long sleeptime = 1000;
	private Queue<Long> queue;
	public TwitterFriendsFetcher() {
		super();
		count = 0;
		userCount = 0;
		limit = getLimit(userCount);
		db = new Neo4jDb("FOLLOW", "user", "hoge");
		db.openDB();
		hdb = new HDB();
		hdb.open("comp.tch", HDB.OWRITER | HDB.OCREAT);
		queue = new LinkedBlockingQueue<Long>();
	}
	
	private int getLimit(int i) {
		twitter.setOAuthAccessToken(accessTokenList.get(i));
		try {
			return twitter.getRateLimitStatus().getRemainingHits();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void fetchFriends(long id) {
		for (;;) {
			if(hdb.get(String.valueOf(id)) != null) {
				
			} else {
				//RateLimitになった時の処理
				if (count >= limit) {
					if (userCount >= accessTokenList.size()) {
						try {
							long time = twitter.getRateLimitStatus().getResetTime()
									.getTime();
							Thread.sleep(time - System.currentTimeMillis());
						} catch (Exception e) {
							e.printStackTrace();
						}
						userCount = 0;
					} else {
						userCount++;
					}

					count = 0;
					limit = getLimit(count);
				}
				
				hdb.put(String.valueOf(id), "a");
				long[] ids = null;
				try {
					twitter.setOAuthAccessToken(accessTokenList.get(userCount));
					ids = twitter.getFriendsIDs(id, -1).getIDs();
					try {
						Thread.sleep(sleeptime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(count);
					count++;
				} catch (TwitterException e1) {

				}
				db.setNodeToDB(ids);
				db.setRelationToDB(id, ids);
				
				if (ids != null)
					for (long friendId : ids) {
						queue.add(friendId);
					}
			}
			id = queue.poll();
		}
	}
	
	public void start(long id) {
		twitter.setOAuthAccessToken(accessTokenList.get(0));
		fetchFriends(id);
	}
	
	public void start(String screenName) {
		twitter.setOAuthAccessToken(accessTokenList.get(0));
		try {
			long id = twitter.showUser(screenName).getId();
			if(!db.inDB(id)) db.setNodeToDB(id);
			fetchFriends(id);
		} catch (TwitterException e) {
			
		}
	}
	
	public void close(){
		hdb.close();
		db.closeDB();
	}
	public static void main(String[] args) {
		TwitterFriendsFetcher a = new TwitterFriendsFetcher();
		a.start("ukyo");
		a.close();
	}
}
