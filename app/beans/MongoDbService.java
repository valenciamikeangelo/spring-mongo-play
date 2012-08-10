package beans;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDbService {

	private DB dbConnection;
	private String hostname;
	private String port;
	private String dbName;
	
	public MongoDbService(String hostname,String port,String dbName){
		Mongo mongo=null;
		try {
			mongo=	 new Mongo(hostname,Integer.parseInt(port));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbConnection = mongo.getDB(dbName);
	}

	public DB getDbConnection() {
		return dbConnection;
	}

	public String getHostname() {
		return hostname;
	}

	public String getPort() {
		return port;
	}

	public String getDbName() {
		return dbName;
	}
	
}
