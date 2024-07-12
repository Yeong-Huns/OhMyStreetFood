package org.omsf.store.service;

import org.omsf.error.Exception.CustomBaseException;
import org.omsf.error.Exception.ErrorCode;
import org.omsf.store.dao.OrderRepository;
import org.omsf.store.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @packageName    : org.omsf.store.service
* @fileName       : OrdersServiceImpl.java
* @author         : iamjaeeuncho
* @date           : 2024.07.12
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.07.12        iamjaeeuncho       최초 생성
*/

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
    private OrderRepository orderRepository;

	@Override
	public void saveOrder(Order orders) {
		orderRepository.saveOrder(orders);
	}

	@Override
	public Order getOrderByNo(int orderNo) {
		Order order = orderRepository.getOrderByNo(orderNo).orElseThrow(() -> new CustomBaseException(ErrorCode.NOT_FOUND_STORE));
		return order;
	}
}
