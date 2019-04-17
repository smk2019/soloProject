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

import com.fdmgroup.entities.User;

public class TestUserDAO {

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soloprojectfinal");
	private EntityManager entityManager = entityManagerFactory.createEntityManager();

	@Before
	public void setup() {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		TypedQuery<User> deleterUsersQuery = entityManager.createQuery("Delete from SC_USER", User.class);
		deleterUsersQuery.executeUpdate();
		entityTransaction.commit();
	}

	@Test // 1
	public void testThatTheListUsersMethodReturnsAListOfSizeZero() {
		UsersDAO usersDAO = new UsersDAO(entityManager);
		List<User> listUsers = usersDAO.listUsers();
		int listSize = listUsers.size();
		assertEquals(0, listSize);
	}

	@Test // 2
	public void testThatWhenIAddAUserToAnEmptyListTheListUsersMethodReturnsAListOfSizeOne() {
		UsersDAO usersDAO = new UsersDAO(entityManager);

		User user = new User();
		user.setUsername("Adrian");
		usersDAO.addUser(user);

		List<User> listUsers = usersDAO.listUsers();
		int listSize = listUsers.size();
		assertEquals(1, listSize);
	}

	@Test // 3
	public void testThatWhenIAddAUserToAnEmptyListTheGetMethodReturnsTheUserThatWasAdded() {
		UsersDAO usersDAO = new UsersDAO(entityManager);

		User user = new User();
		user.setUsername("Adrian");
		usersDAO.addUser(user);

		User userInDB = usersDAO.getUser("Adrian");
		assertNotNull(userInDB);
	}

	@Test // 4
	public void testThatWhenIAddAUserAndThenRemoveThatSameUserThatGetUerMethodReturnsNull() {
		UsersDAO usersDAO = new UsersDAO(entityManager);

		User user = new User();
		user.setUsername("Adrian");
		usersDAO.addUser(user);
		usersDAO.removeUser("Adrian");

		User userInDB = usersDAO.getUser("Adrian");
		assertNull(userInDB);
	}

	@Test // 5
	public void testThatWhenIAddAUserAndThenRemoveThatSameUserThanListUserMethodReturnsASizeZero() {
		UsersDAO usersDAO = new UsersDAO(entityManager);

		User user = new User();
		user.setUsername("Adrian");
		usersDAO.addUser(user);
		usersDAO.removeUser("Adrian");
		List<User> listUsers = usersDAO.listUsers();
		int size = listUsers.size();
		assertEquals(0, size);

	}

	@Test // 6
	public void testThatWhenIAddAUserAndThenUpdateThatUserThatTheUserHasBeenUpdated() {
		UsersDAO usersDAO = new UsersDAO(entityManager);

		User user = new User();
		user.setUsername("Adrian");
		user.setPassword("oldpassowrd");

		usersDAO.addUser(user);

		User newUser = new User();
		newUser.setUsername("Adrian");
		newUser.setPassword("newpassowrd");

		usersDAO.updateUser(newUser);

		User userInDB = usersDAO.getUser("Adrian");
		String password = userInDB.getPassword();

		assertEquals("newpassowrd", password);

	}
	
	@Test // 7
	public void testThatWhenRemoveAllUsersMethodIsCalledThenListUsersMethodReturnsASizeZero() {
		UsersDAO usersDAO = new UsersDAO(entityManager);

		User user = new User();
		user.setUsername("Adrian");
		usersDAO.addUser(user);
		usersDAO.removeAllUser();
		List<User> listUsers = usersDAO.listUsers();
		int size = listUsers.size();
		assertEquals(0, size);


	}
	
	@Test // 8
	public void testThatWhenRemoveAllUsersMethodIsCalledThenThatGetUerMethodReturnsNull() {
		UsersDAO usersDAO = new UsersDAO(entityManager);

		User user = new User();
		user.setUsername("Adrian");
		usersDAO.addUser(user);
		usersDAO.removeAllUser();
		
		User userInDB = usersDAO.getUser("Adrian");
		assertNull(userInDB);
	}
	
}
