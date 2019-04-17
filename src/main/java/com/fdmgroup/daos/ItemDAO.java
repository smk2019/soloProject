package com.fdmgroup.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.fdmgroup.entities.Item;



public class ItemDAO {
	List<Item> listItems = new ArrayList<Item>();
	private EntityManager entityManager;

	public ItemDAO(EntityManager entityManager) {
		this.entityManager = entityManager;

	}
	
	public List<Item> listItems() {
		TypedQuery<Item> listItemsQuery = entityManager.createQuery("Select i from Item i", Item.class);
		listItems = listItemsQuery.getResultList();
		return listItems;
	}


	public void addItem(Item item) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(item);
		entityTransaction.commit();
		
	}

	public Item getItem(int item_id) {
		Item itemInDB = entityManager.find(Item.class, item_id);
		return itemInDB;
	}

	public void removeItem(int item_id) {
		Item itemInDB = entityManager.find(Item.class, item_id);
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(itemInDB);
		entityTransaction.commit();
		
	}

	public void updateItem(Item newItem) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.merge(newItem);
		entityTransaction.commit();
		
	}

}
