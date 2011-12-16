package main.sample;

import main.config.AccessTokenList;
import main.config.Consumer;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class AbstractTwitter {
	protected Twitter twitter;
	protected AccessTokenList accessTokenList;
	
	public AbstractTwitter() {
		Consumer consumer = Consumer.getInstance();
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(consumer.getToken(), consumer.getSecret());
		accessTokenList = AccessTokenList.getInstance();
	}
}
