package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import models.Account;
import models.Post;
import net.vz.mongodb.jackson.JacksonDBCollection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import beans.AccountService;
import beans.MongoDbService;
import exceptions.AccountCreateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class AccountServiceTest {

	@Autowired
	private AccountService accountService;
	@Autowired
	private MongoDbService mongoDbService;

	@Test
	public void testBeansLoaded() {
		assertNotNull(mongoDbService);
		assertNotNull(accountService);
	}

	@Test
	public void testInsertSearchAndDelete() throws AccountCreateException {
		Account paccount = accountService.retriveByEmail("test@email.com");
		if (paccount != null) {
			accountService.deleteAccount(paccount);
		}
		Account account = new Account("test@email.com", "test");
		accountService.insertAccount(account);
		Account paccount2 = accountService.retriveByEmail(account.email);
		assertNotNull(paccount2);
		assertEquals("test@email.com", paccount2.email);
		accountService.deleteAccount(paccount2);
		paccount2 = accountService.retriveByEmail(account.email);
		assertNull(paccount2);
	}

	@Test(expected = AccountCreateException.class)
	public void testDuplicateInsert() throws AccountCreateException {
		Account account = new Account("test@email.com", "test");
		accountService.insertAccount(account);
		Account paccount = accountService.retriveByEmail(account.email);
		assertNotNull(paccount);
		assertEquals("test@email.com", paccount.email);
		Account account2 = new Account("test@email.com", "test");
		accountService.insertAccount(account2);
	}

}
