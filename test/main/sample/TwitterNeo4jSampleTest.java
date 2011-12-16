package main.sample;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twitter4j.auth.RequestToken;

public class TwitterNeo4jSampleTest {
	private TwitterSample twitterSample;
	
	@Before
	public void setUp() throws Exception {
		twitterSample = new TwitterSample();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRequestToken() {
		RequestToken requestToken = twitterSample.getRequestToken();
		assertNotNull("get requesttoken", requestToken);
		assertNotNull(requestToken.getAuthorizationURL());
	}

}
