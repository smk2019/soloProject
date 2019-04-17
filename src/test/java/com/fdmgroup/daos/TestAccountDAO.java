package com.fdmgroup.daos;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.entities.Account;
import com.fdmgroup.entities.Item;




public class TestAccountDAO {
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soloprojectfinal");
	private EntityManager entityManager = entityManagerFactory.createEntityManager();

	@Before
	public void setup() {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		TypedQuery<Account> deleteAccountQuery = entityManager.createQuery("Delete from Account", Account.class);
		deleteAccountQuery.executeUpdate();
		entityTransaction.commit();
	}

	@Test // 1
	public void testThatTheListAccountsMethodReturnsAListOfSizeZero() {
		AccountDAO accountDAO = new AccountDAO(entityManager);
		List<Account> listAccounts = accountDAO.listAccounts();
		int listSize = listAccounts.size();
		assertEquals(0, listSize);
	}

	@Test // 2
	public void testThatWhenIAddAnAccountToAnEmptyListTheListAccountsMethodReturnsAListOfSizeOne() {
		AccountDAO accountDAO = new AccountDAO(entityManager);
		Account account = new Account();
		account.setCardNumber(123456);
		account.setCvv(121);
		accountDAO.addCard(account);
		List<Account> listAccounts = accountDAO.listAccounts();
		int listSize = listAccounts.size();
		assertEquals(1, listSize);
	}
	
	
	@Test // 3
	public void testThatVerifyAccountMethodReturnsTrueWhenOneAccountAddedAndSameAccountDetailsPassed() {
		AccountDAO accountDAO = new AccountDAO(entityManager);
		Account account = new Account();
		account.setCardNumber(123456);
		account.setCvv(121);
		accountDAO.addCard(account);
		int card = account.getCardNumber();
		int cvv = account.getCvv();
		boolean test = accountDAO.verify(card, cvv);
		assertTrue(test);
	}

	@Test // 4
	public void testThatWhenCardNumberPassedToRemoveAccountMethodThenThatAccountIsRemoved() {
		AccountDAO accountDAO = new AccountDAO(entityManager);
		Account account = new Account();
		account.setCardNumber(123456);
		account.setCvv(121);
		accountDAO.addCard(account);
		int card = account.getCardNumber();
		accountDAO.removeCard(card);
		List<Account> listAccounts = accountDAO.listAccounts();
		int listSize = listAccounts.size();
		assertEquals(0, listSize);
	}
	
	@Test // 5
	public void testThatWhenAccountIsPassedToUpdateAccountMethodThenThatAccountIsUpdated() {
		AccountDAO accountDAO = new AccountDAO(entityManager);
		Account account = new Account();
		account.setCardNumber(123456);
		account.setCvv(121);
		accountDAO.addCard(account);
		
		Account newAccount = new Account();
		newAccount.setCardNumber(123456);
		newAccount.setCvv(122);
		accountDAO.updateCard(newAccount);
		
		Account accountInDB = accountDAO.getCard(123456);
		int pin = accountInDB.getCvv();
		assertEquals(122, pin);

	}
	
	
	@Test // 6
	public void testThatWhenAccountNumberAndAmountIsPassedThenThatMethodReturnsTrue() {
		AccountDAO accountDAO = new AccountDAO(entityManager);
		Account account = new Account();
		account.setCardNumber(123456);
		account.setCvv(121);
		account.setBalance(1000.0);;
		accountDAO.addCard(account);
		
		boolean test = accountDAO.pay(123456,100.0);
		assertTrue(test);

	}
	
	@Test // 7
	public void testThatWhenAccountNumberAndAmountIsPassedThenThatAmountIsReducedFromBalance() {
		AccountDAO accountDAO = new AccountDAO(entityManager);
		Account account = new Account();
		account.setCardNumber(123456);
		account.setCvv(121);
		account.setBalance(1000.0);
		accountDAO.addCard(account);
		
		boolean test = accountDAO.pay(123456,100.0);
		Account accountInDB = accountDAO.getCard(123456);
		double balance = accountInDB.getBalance();
		double oldBalance = 900.0;
		assertEquals(oldBalance, balance, 0.01);

	}
	
}
