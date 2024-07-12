package org.omsf.store.dao;

import java.util.Optional;

import org.omsf.store.model.Order;

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
	void saveOrder(Order orders);
	Optional<Order> getOrderByNo(int orderNo);
}
