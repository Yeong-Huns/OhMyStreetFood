package org.omsf.store.service;

import java.util.List;

import org.omsf.store.model.Order;
import org.omsf.store.model.OrderMenu;

/**
* @packageName    : org.omsf.store.service
* @fileName       : OrdersService.java
* @author         : iamjaeeuncho
* @date           : 2024.07.12
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.07.12        iamjaeeuncho       최초 생성
*/

public interface OrderService {
	// Order
	void saveOrder(Order order);
	Order getOrderByNo(int orderNo);
	void updateOrderApproval(int orderNo, String string);
	
	// OrderMenu
	void saveOrderMenu(OrderMenu orderMenu);
	List<OrderMenu> getOrderMenuByNo(int orderNo);
}
