package com.fdmgroup.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.fdmgroup.entities.Item;
import com.fdmgroup.entities.Order;
import com.fdmgroup.entities.OrderDetails;

public class OrderDetailsDAO {
	List<OrderDetails> listOrderDetails = new ArrayList<OrderDetails>();
	private EntityManager entityManager;
	
	public OrderDetailsDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<OrderDetails> listOrderDetails() {
		TypedQuery<OrderDetails> listOrderDetailsQuery = entityManager.createQuery("Select o from SC_ORDERDETAILS o", OrderDetails.class);
		listOrderDetails = listOrderDetailsQuery.getResultList();
		return listOrderDetails;
	}


	public void addOrderDetail(OrderDetails orderDetails) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.merge(orderDetails);
		entityTransaction.commit();
		
	}

	public List<OrderDetails> getOrderDetails(Order order) {
		TypedQuery<OrderDetails> listOrderDetailsQuery = entityManager.createQuery("Select o from SC_ORDERDETAILS o where o.order =:order ", OrderDetails.class);
		listOrderDetailsQuery.setParameter("order", order);
		listOrderDetails = listOrderDetailsQuery.getResultList();
		return listOrderDetails;
	}



}
	
	
	
	
	
	

