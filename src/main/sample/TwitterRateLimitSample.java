package main.sample;

import twitter4j.TwitterException;

public class TwitterRateLimitSample extends AbstractTwitter{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TwitterRateLimitSample sample = new TwitterRateLimitSample();
		
		sample.twitter.setOAuthAccessToken(sample.accessTokenList.get(0));
		try {
			System.out.println(sample.twitter.getRateLimitStatus().getRemainingHits());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
