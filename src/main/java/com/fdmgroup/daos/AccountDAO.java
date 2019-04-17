package com.fdmgroup.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.fdmgroup.entities.Account;

public class AccountDAO {

	List<Account> listAccounts = new ArrayList<Account>();
	private EntityManager entityManager;

	public AccountDAO(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	public List<Account> listAccounts() {
		TypedQuery<Account> listAccountQuery = entityManager.createQuery("Select a from Account a", Account.class);
		listAccounts = listAccountQuery.getResultList();
		return listAccounts;
	}

	public void addCard(Account account) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(account);
		entityTransaction.commit();

	}

	public Account getCard(int card) {
		Account accountInDB = entityManager.find(Account.class, card);
		return accountInDB;

	}

	public boolean verify(int card, int cvv) {

		Account accountInDB = entityManager.find(Account.class, card);
		if (accountInDB.getCvv() == cvv) {
			return true;
		}
		return false;

	}

	public void removeCard(int card) {
		Account accountInDB = entityManager.find(Account.class, card);
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(accountInDB);
		entityTransaction.commit();

	}

	public void updateCard(Account newAccount) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.merge(newAccount);
		entityTransaction.commit();

	}

	public boolean pay(int card, double amount) {
		Account accountInDB = entityManager.find(Account.class, card);
		double balance = accountInDB.getBalance();
		if (balance > amount) {
			double newBalance = (balance - amount);
			accountInDB.setBalance(newBalance);
			return true;
		}
		return false;
	}

}
