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
public class OrderMenu {
    private Integer orderno;
    private String ordername;
    private Integer orderprice;
    private Integer orderquantity;
}