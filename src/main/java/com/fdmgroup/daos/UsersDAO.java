package com.fdmgroup.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;


import com.fdmgroup.entities.User;

public class UsersDAO {

	List<User> listUsers = new ArrayList<User>();
	private EntityManager entityManager;

	public UsersDAO(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	public List<User> listUsers() {
		TypedQuery<User> listUsersQuery = entityManager.createQuery("Select u from SC_USER u", User.class);
		listUsers = listUsersQuery.getResultList();
		return listUsers;
	}

	public void addUser(User user) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(user);
		entityTransaction.commit();

	}

	public User getUser(String username) {

		User userInDB = entityManager.find(User.class, username);

		return userInDB;
	}

	public void removeUser(String username) {
		User userInDB = entityManager.find(User.class, username);
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(userInDB);
		entityTransaction.commit();

	}

	public void updateUser(User newUser) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.merge(newUser);
		entityTransaction.commit();
		
	}

	public void removeAllUser() {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		TypedQuery<User> deleterUsersQuery = entityManager.createQuery("Delete from SC_USER", User.class);
		deleterUsersQuery.executeUpdate();
		entityTransaction.commit();
		
	}

}
