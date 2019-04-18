package com.fdmgroup.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.fdmgroup.entities.Order;
import com.fdmgroup.entities.User;




public class OrderDAO {
	List<Order> listOrders = new ArrayList<Order>();
	private EntityManager entityManager;
	
	public OrderDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Order> listOrders() {
		TypedQuery<Order> listOrdersQuery = entityManager.createQuery("Select o from SC_ORDER o", Order.class);
		listOrders = listOrdersQuery.getResultList();
		return listOrders;
	}

	public void addOrder(Order order) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(order);
		entityTransaction.commit();
		
	}

	public Order getOrder(int orderNumber) {
		Order orderInDB = entityManager.find(Order.class, orderNumber);
		return orderInDB;
	}

	public List<Order> getOrder(User user) {
		TypedQuery<Order> listOrdersQuery = entityManager.createQuery("Select o from SC_ORDER o where o.user =:user ", Order.class);
		listOrdersQuery.setParameter("user", user);
		listOrders = listOrdersQuery.getResultList();
		return listOrders;
	}
	
	public void removeOrder(int orderNumber) {
		Order orderInDB = entityManager.find(Order.class, orderNumber);
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(orderInDB);
		entityTransaction.commit();
		
	}

	public void updateOrder(Order newOrder) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.merge(newOrder);
		entityTransaction.commit();
		
	}

	public void removeAllOrder() {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		TypedQuery<Order> deleterOrdersQuery = entityManager.createQuery("Delete from SC_ORDER", Order.class);
		deleterOrdersQuery.executeUpdate();
		entityTransaction.commit();
		
	}

	
}
