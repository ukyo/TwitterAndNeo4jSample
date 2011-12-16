package main.db;

import static org.junit.Assert.*;

import main.sample.TwitterSample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Neo4jDbTest {
	private Neo4jDb db;
	
	@Before
	public void setUp() throws Exception {
		db = new Neo4jDb("FOLLOW", "user", "temp");
		db.openDB();
	}

	@After
	public void tearDown() throws Exception {
		db.closeDB();
	}
	
	@Test
	public void test() {
		long a = 10, b = 20;
		
		//Nodeの作成
		assertFalse(db.inDB(a));
		db.setNodeToDB(a);
		assertTrue(db.inDB(a));
		assertFalse(db.inDB(b));
		db.setNodeToDB(b);
		assertTrue(db.inDB(b));
		
		//RelationShipの作成
		assertFalse(db.isRelation(a, a));
		db.setRelationToDB(a, b);
		assertTrue(db.isRelation(a, b));
	}

}
