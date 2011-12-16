package main.config;

import static org.junit.Assert.*;

import org.junit.Test;

import twitter4j.auth.AccessToken;

public class AccessTokenListTest {

	@Test
	public void test() {
		AccessTokenList list = AccessTokenList.getInstance();
		assertNotNull(list);
		for(AccessToken at: list) {
			System.out.println(at.getToken()+" "+at.getTokenSecret());
		}
	}

}
