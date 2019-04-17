package com.fdmgroup.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.entities.Item;




public class TestItemDAO {

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soloprojectfinal");
	private EntityManager entityManager = entityManagerFactory.createEntityManager();

	@Before
	public void setup() {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		TypedQuery<Item> deleterUsersQuery = entityManager.createQuery("Delete from Item", Item.class);
		deleterUsersQuery.executeUpdate();
		entityTransaction.commit();
	}
	
	@Test // 1
	public void testThatTheListItemsMethodReturnsAListOfSizeZero() {
		ItemDAO itemDAO = new ItemDAO(entityManager);
		List<Item> listItems = itemDAO.listItems();
		int listSize = listItems.size();
		assertEquals(0, listSize);
	}
	
	@Test // 2
	public void testThatWhenIAddAnItemToAnEmptyListTheListItemsMethodReturnsAListOfSizeOne() {
		ItemDAO itemDAO = new ItemDAO(entityManager);

		Item item = new Item();
		item.setItemId(1);
		item.setItemName("iPhone");
		item.setDescription("Smart Phone");
		item.setItemPrice(999.9);
		itemDAO.addItem(item);

		List<Item> listItems = itemDAO.listItems();
		int listSize = listItems.size();
		assertEquals(1, listSize);
	}
	
	@Test // 3
	public void testThatWhenIAddAnItemToAnEmptyListTheGetMethodReturnsTheItemThatWasAdded() {
		ItemDAO itemDAO = new ItemDAO(entityManager);

		Item item = new Item();
		item.setItemId(1);
		item.setItemName("iPhone");
		item.setDescription("Smart Phone");
		item.setItemPrice(999.9);
		itemDAO.addItem(item);

		Item itemInDB = itemDAO.getItem(1);
		assertNotNull(itemInDB);
	}

	@Test // 4
	public void testThatWhenIAddAnItemAndThenRemoveThatSameItemThatGetItemMethodReturnsNull() {
		ItemDAO itemDAO = new ItemDAO(entityManager);

		Item item = new Item();
		item.setItemId(1);
		item.setItemName("iPhone");
		item.setDescription("Smart Phone");
		item.setItemPrice(999.9);
		itemDAO.addItem(item);
		itemDAO.removeItem(1);

		Item itemInDB = itemDAO.getItem(1);
		assertNull(itemInDB);
	}

	@Test // 5
	public void testThatWhenIAddAnItemAndThenRemoveThatSameItemThanListItemsMethodReturnsASizeZero() {
		ItemDAO itemDAO = new ItemDAO(entityManager);

		Item item = new Item();
		item.setItemId(1);
		item.setItemName("iPhone");
		item.setDescription("Smart Phone");
		item.setItemPrice(999.9);
		itemDAO.addItem(item);
		itemDAO.removeItem(1);

		List<Item> listItems = itemDAO.listItems();
		int size = listItems.size();
		assertEquals(0, size);

	}
	
	@Test // 6
	public void testThatWhenIAddAnItemAndThenUpdateThatItemThatTheItemHasBeenUpdated() {
		ItemDAO itemDAO = new ItemDAO(entityManager);

		Item item = new Item();
		item.setItemId(1);
		item.setItemName("iPhone");
		item.setDescription("Smart Phone");
		item.setItemPrice(999.9);
		itemDAO.addItem(item);

		Item newItem = new Item();
		newItem.setItemId(1);
		newItem.setItemName("Samsung");
		newItem.setDescription("Smart Phone");
		newItem.setItemPrice(999.9);
		itemDAO.updateItem(newItem);

		Item itemInDB = itemDAO.getItem(1);
		String model = itemInDB.getItemName();
		assertEquals("Samsung", model);

	}
	
	
}


