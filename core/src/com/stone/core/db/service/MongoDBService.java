package com.stone.core.db.service;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.stone.core.entity.IEntity;

/**
 * The mongo db service;
 * 
 * @author crazyjohn
 *
 */
public class MongoDBService implements IDBService {
	/** mongo instance */
	Mongo mongo;
	/** db instance */
	DB db;

	public MongoDBService(String host, int port) throws UnknownHostException {
		mongo = new Mongo(host, port);
		db = mongo.getDB("stone");
	}

	@Override
	public void update(IEntity entity) {
		DBCollection collection = getDBCollection(entity);
		collection.update(getIdQuery(entity.getClass(), entity.getId()), getDBObject(entity));
	}

	private DBObject getDBObject(IEntity entity) {
		return null;
	}

	/**
	 * Get query object;
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	private DBObject getIdQuery(Class<? extends IEntity> entityClass, Serializable id) {
		return new BasicDBObject("guid", id);
	}

	/**
	 * Get db collection;
	 * 
	 * @param entity
	 * @return
	 */
	private DBCollection getDBCollection(IEntity entity) {
		return db.getCollection(entity.getClass().getSimpleName());
	}

	@Override
	public IEntity get(Class<?> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable insert(IEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(IEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Class<?> entityClass, Serializable id) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> List<T> queryByNameAndParams(String queryName, String[] params, Object[] values, int maxResult, int start) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void heartBeat() {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> List<T> queryByNameAndParams(String queryName, String[] params, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	private void foo() {
		Set<String> collectionNames = db.getCollectionNames();
		// print collection name
		for (String eachName : collectionNames) {
			System.out.println(eachName);
		}
		// human
		DBCollection humanCollection = db.getCollection("human");
		BasicDBObject humanDoc = new BasicDBObject();
		// put
		humanDoc.put("guid", 9999);
		humanDoc.put("playerId", 1);
		humanDoc.put("name", "crazyjohn");
		humanDoc.put("level", 100);
		BasicDBObject itemDoc = new BasicDBObject();
		itemDoc.put("itemId", 1001);
		humanDoc.put("items", itemDoc);
		// insert
		humanCollection.insert(humanDoc);
		DBObject oneRecord = humanCollection.findOne();
		// print
		System.out.println(oneRecord);
	}

	/**
	 * The main enter;
	 * 
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {
		MongoDBService service = new MongoDBService("localhost", 27017);
		service.foo();
	}

}
