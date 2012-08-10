package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import models.Account;
import models.Post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import beans.AccountService;
import beans.MongoDbService;
import beans.PostService;
import exceptions.AccountCreateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class PostServiceTest {

	@Autowired
	private AccountService accountService;
	@Autowired
	private PostService postService;
	@Autowired
	private MongoDbService mongoDbService;

	@Test
	public void testBeansLoaded() {
		assertNotNull(mongoDbService);
		assertNotNull(postService);
		assertNotNull(accountService);
	}
		
	@Test
	public void testAddPost() throws AccountCreateException {
	Account account = new Account("test@email.com", "test");
	Account account2 = new Account("test2@email.com", "test");
	accountService.insertAccount(account);
	account=accountService.retriveByEmail(account.email);
	String title ="TITLE1";
	String content ="CONTENT1";
	postService.createPost(account, title, content);
	List<Post> posts=postService.getPostByAuthor(account);
	assertEquals(1, posts.size());
	List<Post> posts2=postService.getPostByAuthor(account2);
	assertEquals(0, posts2.size());
	
	String title2 ="TITLE2";
	String content2 ="CONTENT2";
	postService.createPost(account, title2, content2);
	posts=postService.getPostByAuthor(account);
	assertEquals(2, posts.size());
	
	String title3 ="TITLE3";
	String content3 ="CONTENT3";
	accountService.insertAccount(account2);
	account2=accountService.retriveByEmail(account2.email);
	postService.createPost(account2, title3, content3);
	posts2=postService.getPostByAuthor(account2);
	assertEquals(1, posts2.size());
	
	
	postService.deletePostByAuthor(account);
	List<Post> ppost=postService.getPostByAuthor(account);
	assertEquals(0, ppost.size());
	postService.deletePostByAuthor(account2);
	List<Post> ppost2=postService.getPostByAuthor(account2);
	assertEquals(0, ppost2.size());
	accountService.deleteAccount(account);
	Account paccount = accountService.retriveById(account.id);
	assertNull(paccount);
	accountService.deleteAccount(account2);
	Account paccount2 = accountService.retriveById(account2.id);
	assertNull(paccount2);
	}
	
	
	@Test
	public void testCommentToPost() throws AccountCreateException {
	Account account = new Account("test@email.com", "test");
	Account account2 = new Account("test2@email.com", "test");
	accountService.insertAccount(account);
	account=accountService.retriveByEmail(account.email);
	String title ="TITLE1";
	String content ="CONTENT1";
	postService.createPost(account, title, content);
	List<Post> posts=postService.getPostByAuthor(account);
	assertEquals(1, posts.size());
	List<Post> posts2=postService.getPostByAuthor(account2);
	assertEquals(0, posts2.size());
	
	String title2 ="TITLE2";
	String content2 ="CONTENT2";
	postService.createPost(account, title2, content2);
	posts=postService.getPostByAuthor(account);
	assertEquals(2, posts.size());
	
	String title3 ="TITLE3";
	String content3 ="CONTENT3";
	accountService.insertAccount(account2);
	account2=accountService.retriveByEmail(account2.email);
	postService.createPost(account2, title3, content3);
	posts2=postService.getPostByAuthor(account2);
	assertEquals(1, posts2.size());
	
	Post postToComment=posts2.get(0);
	
	postService.addComment(account, "THIS IS A COMMENT", postToComment.id);
	
	Post ppostc=postService.findPostById(postToComment.id);
	account = accountService.retriveById(account.id);
	assertNotNull(ppostc);
	assertEquals(1, ppostc.comments.size());
	assertEquals(account.email, ppostc.comments.get(0).commenter.email);
	assertEquals("THIS IS A COMMENT", ppostc.comments.get(0).content);
	assertEquals(1,account.participatedPosts.size());
	
	postService.addComment(account2, "THIS IS ANOTHER COMMENT", postToComment.id);
	
	ppostc=postService.findPostById(postToComment.id);
	account2 = accountService.retriveById(account2.id);
	
	assertNotNull(ppostc);
	assertEquals(2, ppostc.comments.size());
	assertEquals(account2.email, ppostc.comments.get(1).commenter.email);
	assertEquals("THIS IS ANOTHER COMMENT", ppostc.comments.get(1).content);
	assertEquals(1,account2.participatedPosts.size());
	
	postService.deletePostByAuthor(account);
	List<Post> ppost=postService.getPostByAuthor(account);
	assertEquals(0, ppost.size());
	postService.deletePostByAuthor(account2);
	List<Post> ppost2=postService.getPostByAuthor(account2);
	assertEquals(0, ppost2.size());
	accountService.deleteAccount(account);
	Account paccount = accountService.retriveById(account.id);
	assertNull(paccount);
	accountService.deleteAccount(account2);
	Account paccount2 = accountService.retriveById(account2.id);
	assertNull(paccount2);
	}
	
	@Test
	public void dropData(){
		postService.getPostColletion().drop();
		accountService.getAccountColletion().drop();
	}
}
