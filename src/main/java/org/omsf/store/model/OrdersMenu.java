package org.omsf.store.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName    : org.omsf.store.model
 * fileName       : OrdersMenu
 * author         : iamjaeeuncho
 * date           : 2024-07-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-12      iamjaeeuncho       
 */

@Getter
@Setter
@ToString
public class OrdersMenu {
	
	private Integer ordermenuno;
	private Integer orderno;
	private String ordermenuname;
	private Integer ordermenuprice;
	
}
