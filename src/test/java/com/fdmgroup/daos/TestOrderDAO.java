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

import com.fdmgroup.entities.Order;
import com.fdmgroup.entities.User;



public class TestOrderDAO {
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soloprojectfinal");
	private EntityManager entityManager = entityManagerFactory.createEntityManager();

	@Before
	public void setup() {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		TypedQuery<Order> deleterOrdersQuery = entityManager.createQuery("Delete from SC_ORDER", Order.class);
		deleterOrdersQuery.executeUpdate();
		entityTransaction.commit();
	}

	@Test // 1
	public void testThatTheListOrdersMethodReturnsAListOfSizeZero() {
		OrderDAO orderDAO = new OrderDAO(entityManager);
		List<Order> listOrders = orderDAO.listOrders();
		int listSize = listOrders.size();
		assertEquals(0, listSize);
	}
	
	@Test // 2
	public void testThatWhenIAddAnOrderToAnEmptyListTheListOrdersMethodReturnsAListOfSizeOne() {
		OrderDAO orderDAO = new OrderDAO(entityManager);

		Order order = new Order();
		order.setOrderID(1);
		orderDAO.addOrder(order);

		List<Order> listOrders = orderDAO.listOrders();
		int listSize = listOrders.size();
		assertEquals(1, listSize);
	}

	@Test // 3
	public void testThatWhenIAddAnOrderToAnEmptyListTheGetMethodByOrderIdReturnsTheOrderThatWasAdded() {
		OrderDAO orderDAO = new OrderDAO(entityManager);

		Order order = new Order();
		order.setOrderID(1);
		orderDAO.addOrder(order);

		Order orderInDB = orderDAO.getOrder(1);
		assertNotNull(orderInDB);
	}
	
	@Test // 3.5
	public void testThatWhenIAddAnOrderWithUsernameToAnEmptyListTheGetMethodByUsernameReturnsAListOfOrdersThatWerePlacedByThatUser() {
		OrderDAO orderDAO = new OrderDAO(entityManager);
		User user = new User();
		Order order = new Order();
		order.setOrderID(1);
		user.setUsername("Sudheep");
		order.setUser(user);
		orderDAO.addOrder(order);

		List<Order> listOrders = orderDAO.getOrder(user);
		int listSize = listOrders.size();
		assertEquals(1, listSize);
	}
	
	@Test // 4
	public void testThatWhenIAddAnOrderAndThenRemoveThatSameOrderThatGetOrderMethodReturnsNull() {
		OrderDAO orderDAO = new OrderDAO(entityManager);

		Order order = new Order();
		order.setOrderID(1);
		orderDAO.addOrder(order);
		orderDAO.removeOrder(1);

		Order orderInDB = orderDAO.getOrder(1);
		assertNull(orderInDB);
	}

	@Test // 5
	public void testThatWhenIAddAnOrderAndThenRemoveThatSameOrderThatListOrderMethodReturnsASizeZero() {
		OrderDAO orderDAO = new OrderDAO(entityManager);

		Order order = new Order();
		order.setOrderID(1);
		orderDAO.addOrder(order);
		orderDAO.removeOrder(1);
		List<Order> listOrders = orderDAO.listOrders();
		int listSize = listOrders.size();
		assertEquals(0, listSize);

	}

	@Test // 6
	public void testThatWhenIAddAnOrderAndThenUpdateThatOrderThatTheOrderHasBeenUpdated() {
		OrderDAO orderDAO = new OrderDAO(entityManager);
		User user = new User();

		Order order = new Order();
		order.setOrderID(1);
		order.setOrderDate("10-12-2010");
		orderDAO.addOrder(order);
		
		Order newOrder = new Order();
		newOrder.setOrderID(1);
		user.setUsername("Sudheep");
		newOrder.setUser(user);
		newOrder.setOrderDate("10-08-2010");
	

		orderDAO.updateOrder(newOrder);

		Order orderInDB = orderDAO.getOrder(1);
		String date = orderInDB.getOrderDate();

		assertEquals("10-08-2010", date);

	}

	@Test // 7
	public void testThatWhenRemoveAllOrdersMethodIsCalledThenListOrdersMethodReturnsASizeZero() {
		OrderDAO orderDAO = new OrderDAO(entityManager);

		Order order = new Order();
		order.setOrderID(1);
		orderDAO.addOrder(order);
		orderDAO.removeAllOrder();
		
		List<Order> listOrders = orderDAO.listOrders();
		int listSize = listOrders.size();
		assertEquals(0, listSize);


	}
	
	@Test // 8
	public void testThatWhenRemoveAllOrdersMethodIsCalledThenThatGetOrderMethodReturnsNull() {
		OrderDAO orderDAO = new OrderDAO(entityManager);

		Order order = new Order();
		order.setOrderID(1);
		orderDAO.addOrder(order);
		orderDAO.removeAllOrder();
		
		Order orderInDB = orderDAO.getOrder(1);
		assertNull(orderInDB);
	}
	
	
}
