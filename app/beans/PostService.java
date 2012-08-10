package beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import models.Account;
import models.Comment;
import models.MongoDbCollectionsKey;
import models.Post;
import net.vz.mongodb.jackson.DBQuery;
import net.vz.mongodb.jackson.DBUpdate;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;

import com.mongodb.DB;
import com.mongodb.DBCollection;

import exceptions.AccountCreateException;

public class PostService {

	private DB db;
	private DBCollection postDBCollection;
	private JacksonDBCollection<Post, String> postDBCollectionJML; 
	@Autowired
	private AccountService accountService;
	
	public PostService(MongoDbService mongoDbService){
		this.db=mongoDbService.getDbConnection();
		this.postDBCollection=db.getCollection(MongoDbCollectionsKey.POST.toString());
		this.postDBCollectionJML= JacksonDBCollection.wrap(postDBCollection, Post.class,String.class);
	}
		
	public JacksonDBCollection<Post, String> getPostColletion() {
		return postDBCollectionJML;
	}
	
	public WriteResult<Post, String> createPost(Account account,String title,String content) throws AccountCreateException {
		Post post= new Post(account, title, content);
		WriteResult<Post, String> result = postDBCollectionJML.insert(post);
		return result;
	}
	
	public List<Post> getPostByAuthor(Account account){
		 List<Post> posts = postDBCollectionJML.find(DBQuery.is("author", account)).toArray();
		 return posts;
	}
	
	public  WriteResult<Post, String> deletePostByAuthor(Account account){
		 return postDBCollectionJML.remove(DBQuery.is("author", account));
	}
	
	public Post findPostById(String id){
		return postDBCollectionJML.findOneById(id);
	}
	
	public  WriteResult<Post, String> addComment(Account commenter, String content,String postId) {
		Comment newComment = new Comment(commenter, content);
		accountService.addParticipatedPostToAccount(commenter, postId);
		return postDBCollectionJML.updateById(postId, DBUpdate.push("comments",newComment));
	}
	
	
}
