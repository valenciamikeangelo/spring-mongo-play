package beans;

import models.Account;
import models.MongoDbCollectionsKey;
import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.DBUpdate;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;

import com.mongodb.DB;
import com.mongodb.DBCollection;

import exceptions.AccountCreateException;


public class AccountService {

	private DB db;
	private DBCollection accountDBCollection;
	private JacksonDBCollection<Account, String> accountDBCollectionJML; 
	
	
	public AccountService(MongoDbService mongoDbService){
		this.db=mongoDbService.getDbConnection();
		this.accountDBCollection=db.getCollection(MongoDbCollectionsKey.ACCOUNT.toString());
		this.accountDBCollectionJML= JacksonDBCollection.wrap(accountDBCollection, Account.class,String.class);
	}
		
	public JacksonDBCollection<Account, String> getAccountColletion() {
		return accountDBCollectionJML;
	}
	
	public WriteResult<Account, String> insertAccount(Account account) throws AccountCreateException {
		Account paccount =retriveByEmail(account.email);
		if(paccount!=null){
			throw new AccountCreateException("Email already Exist!");
		}
		WriteResult<Account, String> result = accountDBCollectionJML.insert(account);
		return result;
	}
		
	public WriteResult<Account, String>  deleteAccount(Account account){
		return accountDBCollectionJML.remove(account);
	}

	public Account retriveById(String id) {
		return accountDBCollectionJML.findOneById(id);
	}

	public Account retriveByEmail(String email) {
		DBCursor<Account> cursor = accountDBCollectionJML.find().is("email", email);
		if(cursor.hasNext())
		{
			return cursor.next();
		}	
		return null;
	}
	
	
	public WriteResult<Account, String> addParticipatedPostToAccount(Account account, String postId){
		Account paccount= retriveByEmail(account.email);
		if(!paccount.participatedPosts.contains(postId))
		{
			return accountDBCollectionJML.updateById(account.id, DBUpdate.push("participatedPosts",postId));
		}	
		return null;
	}


}
