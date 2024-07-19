package org.omsf.store.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omsf.error.Exception.CustomBaseException;
import org.omsf.error.Exception.ErrorCode;
import org.omsf.store.dao.OrderRepository;
import org.omsf.store.model.Order;
import org.omsf.store.model.OrderMenu;
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

	// Order
	@Override
	public void saveOrder(Order order) {
		orderRepository.saveOrder(order);
	}

	@Override
	public Order getOrderByNo(int orderNo) {
		Order order = orderRepository.getOrderByNo(orderNo).orElseThrow(() -> new CustomBaseException(ErrorCode.NOT_FOUND_ORDER));
		return order;
	}
	
	@Override
	public void updateOrderApproval(int orderNo, String approval) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("orderNo", orderNo);
	    params.put("approval", approval);

	    orderRepository.updateOrderApproval(params);
	}

	@Override
	public boolean updatePayStatus(String storeNo, String orderNo, String paystatus, LocalDateTime paidat) {
		return orderRepository.updatePayStatus(storeNo, orderNo, paystatus, paidat);
	}

	// OrderMenu
	@Override
	public void saveOrderMenu(OrderMenu orderMenu) {
		orderRepository.saveOrderMenu(orderMenu);
	}
	
	@Override
    public List<OrderMenu> getOrderMenuByNo(int orderNo) {
        List<OrderMenu> ordermenu = orderRepository.getOrderMenuByNo(orderNo);
        return ordermenu;
    }
	
}
