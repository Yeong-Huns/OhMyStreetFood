package org.omsf.store.model;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Menu {
	private int menuNo;
	private String menuName;
	private long price;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	
	private int storeNo;
	
	public Menu(int menuNo, String menuName, long price,
			Timestamp createdAt, Timestamp modifiedAt, int storeNo) {
		this.menuNo = menuNo;
        this.menuName = menuName;
        this.price = price;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.storeNo = storeNo;
    }
	public Menu() {
		
	}
}