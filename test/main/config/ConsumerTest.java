package main.config;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConsumerTest {
	@Test
	public void test() {
		Consumer consumer = Consumer.getInstance();
		assertNotNull(consumer);
		assertNotNull(consumer.getToken());
		System.out.println(consumer.getToken());
		assertNotNull(consumer.getSecret());
		System.out.println(consumer.getSecret());
	}

}
