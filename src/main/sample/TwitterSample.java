package main.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import main.config.Consumer;

public class TwitterSample {
	private Consumer consumer;
	private Twitter twitter;
	private RequestToken requestToken;
	
	public TwitterSample() {
		consumer = Consumer.getInstance();
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(consumer.getToken(), consumer.getSecret());
		requestToken = this.getRequestToken();
	}

	public Consumer getConsumer() {
		return consumer;
	}
	
	public RequestToken getRequestToken() {
		try {
			return twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AccessToken getAccessToken(String pin) {
		try {
			return twitter.getOAuthAccessToken(requestToken, pin);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		TwitterSample twitterSample = new TwitterSample();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println(twitterSample.requestToken.getAuthorizationURL());
			String pin = br.readLine();
			AccessToken accessToken = twitterSample.getAccessToken(pin);
			System.out.println(accessToken.getToken()+","+accessToken.getTokenSecret());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
