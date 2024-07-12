package org.omsf.store.service;

import org.omsf.store.model.Order;

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
	void saveOrder(Order orders);
	Order getOrderByNo(int orderNo);
}
