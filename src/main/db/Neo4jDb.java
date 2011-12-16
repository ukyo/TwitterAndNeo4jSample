package main.db;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import tokyocabinet.HDB;

public class Neo4jDb {
	private GraphDatabaseService graphDB;
	private IndexManager indexManager;
	private Index<Node> userIndex;
	private DynamicRelationshipType relationType;
	private String kind;
	private String path;
	private HDB kvs;
	public Neo4jDb() {
		this("FOLLOW", "user", "~/neo4j-community-1.6.M01/data");
	}
	
	/**
	 * 
	 * @param name RelationShipTypeの名前
	 * @param kind Nodeの種類
	 * @param path DBのパス
	 */
	public Neo4jDb(String name, String kind, String path) {
		relationType = DynamicRelationshipType.withName(name);
		this.kind = kind;
		this.path = path;
		kvs = new HDB();
		
	}
	
	public void openDB() {
		graphDB = new EmbeddedGraphDatabase(path);
		kvs.open("userid2id.tch", HDB.OWRITER | HDB.OCREAT);
	}
	
	public void startIndex() {
		indexManager = graphDB.index();
		userIndex = indexManager.forNodes("useres");
	}
	
	public void closeDB() {
		graphDB.shutdown();
		kvs.close();
	}
	
	private long getNeo4jID(long id){
		Object o = kvs.get(String.valueOf(id));
		if(o == null) return -1;
		return Long.parseLong((String)o);
	}
	public boolean inDB(long id) {
		long a = getNeo4jID(id);
		return a == -1 ? false : true;
	}
	
	public void setNodeToDB(long id) {
		Transaction tx = graphDB.beginTx();
		startIndex();
		Node user = graphDB.createNode();
		try {
			user.setProperty("user_id", id);
			kvs.put(String.valueOf(id), String.valueOf(user.getId()));
			tx.success();
		} catch (Exception e) {
			tx.failure();
		} finally {
			tx.finish();
		}
	}
	
	public void setNodeToDB(long[] ids) {
		Transaction tx = graphDB.beginTx();
		startIndex();
		try {
			for(long id: ids) {
				if(inDB(id)) continue;
				Node user = graphDB.createNode();
				System.out.println("Neo4jDB "+user.getId());
				user.setProperty("user_id", id);
				kvs.put(String.valueOf(id), String.valueOf(user.getId()));
			}
			tx.success();
		} catch (Exception e) {
			tx.failure();
		} finally {
			tx.finish();
		}
	}

	public void setRelationToDB(long fromID, long toID) {
		Transaction tx = graphDB.beginTx();
		startIndex();
		Node from = graphDB.getNodeById(getNeo4jID(fromID));
		Node to = graphDB.getNodeById(getNeo4jID(toID));
		try {
			from.createRelationshipTo(to, relationType);
			tx.success();
		} catch (Exception e) {
			tx.failure();
		} finally {
			tx.finish();
		}
	}
	
	public void setRelationToDB(long fromID, long[] toIDs) {
		Transaction tx = graphDB.beginTx();
		
		try {
			Node from = graphDB.getNodeById(getNeo4jID(fromID));
			Node to;
			for (long toID: toIDs) {
				to = graphDB.getNodeById(getNeo4jID(toID));
				from.createRelationshipTo(to, relationType);
			}
			tx.success();
		} catch (Exception e) {
			tx.failure();
		} finally {
			tx.finish();
		}
	}
	
	public boolean isRelation(long fromID, long toID) {
		startIndex();
		Node from = graphDB.getNodeById(getNeo4jID(fromID));
		Node to = graphDB.getNodeById(getNeo4jID(toID));
		for(Relationship r: from.getRelationships(Direction.OUTGOING, relationType)) {
			if (to.equals(r.getEndNode())) {
				return true;
			}
		}
		return false;
	}
}
