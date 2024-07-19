package org.omsf.store.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.omsf.store.model.Order;
import org.omsf.store.model.OrderMenu;

/**
* @packageName    : org.omsf.store.service
* @fileName       : OrderRepository.java
* @author         : iamjaeeuncho
* @date           : 2024.07.12
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.07.12        iamjaeeuncho       최초 생성
*/

public interface OrderRepository {
	// Order
	void saveOrder(Order order);
	Optional<Order> getOrderByNo(int orderNo);
	void updateOrderApproval(Map<String, Object> params);
	void updatePayStatus(Map<String, Object> params);
	void updateOrderPickup(Map<String, Object> params);
	
	// OrderMenu
	void saveOrderMenu(OrderMenu orderMenu);
	List<OrderMenu> getOrderMenuByNo(int orderNo);
}
