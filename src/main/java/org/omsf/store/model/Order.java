package org.omsf.store.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName    : org.omsf.store.model
 * fileName       : Orders
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
public class Order {
	
	private Integer orderno;
	private Integer storeno;
	private String username;
	private Integer totalprice;
	private String approval;
	private String paymentstatus;
	private String paymentmethod;
	private Timestamp createdat;
	private Timestamp pickupat;
	private Timestamp approvedat;
	private Timestamp paidat;
	
}
