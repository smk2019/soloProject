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
import com.fdmgroup.entities.Order;
import com.fdmgroup.entities.OrderDetails;
import com.fdmgroup.entities.User;

public class TestOrderDetailsDAO {
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soloprojectfinal");
	private EntityManager entityManager = entityManagerFactory.createEntityManager();

	@Before
	public void setup() {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		TypedQuery<OrderDetails> deleterOrderDetailsQuery = entityManager.createQuery("Delete from SC_ORDERDETAILS", OrderDetails.class);
		deleterOrderDetailsQuery.executeUpdate();
		entityTransaction.commit();
	}

	@Test // 1
	public void testThatTheListOrderDetailsMethodReturnsAListOfSizeZero() {
		OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO(entityManager);
		List<OrderDetails> listOrderDetails = orderDetailsDAO.listOrderDetails();
		int listSize = listOrderDetails.size();
		assertEquals(0, listSize);
	}
	
	@Test // 2
	public void testThatWhenIAddAnOrderDetailToAnEmptyListTheListOrderDetailsMethodReturnsAListOfSizeOne() {
		OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO(entityManager);

		Order order = new Order();
		Item item = new Item();
		order.setOrderID(1);
		item.setItemId(123);
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setOrder(order);
		orderDetails.setItem(item);
		orderDetails.setQuantity(1);
		orderDetailsDAO.addOrderDetail(orderDetails);

		List<OrderDetails> listOrderDetails = orderDetailsDAO.listOrderDetails();
		int listSize = listOrderDetails.size();
		assertEquals(1, listSize);
	}
	
	@Test // 3
	public void testThatWhenIAddAnOrderToAnEmptyListTheGetMethodByOrderIdReturnsTheOrderThatWasAdded() {
		OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO(entityManager);

		Order order = new Order();
		Item item = new Item();
		order.setOrderID(1);
		item.setItemId(123);
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setOrder(order);
		orderDetails.setItem(item);
		orderDetails.setQuantity(1);
		orderDetailsDAO.addOrderDetail(orderDetails);


		List<OrderDetails> listOrderDetails = orderDetailsDAO.getOrderDetails(order);
		int listSize = listOrderDetails.size();
		assertEquals(1, listSize);
	}
}
	