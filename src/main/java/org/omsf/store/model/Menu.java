package org.omsf.store.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName    : org.omsf.store.model
 * fileName       : Menu
 * author         : KIMCHANGHWAN
 * date           : 2024-07-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-05      KIMCHANGHWAN       
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
	private int menuNo;
	private String menuName;
	private long price;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	
	private int storeNo;
	
}