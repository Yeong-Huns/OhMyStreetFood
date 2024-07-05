package org.omsf.store.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName    : org.omsf.store.model
 * fileName       : StoreInfo
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
public class StoreInfo {
	private Store store;
	private Photo photo;
	private List<Menu> menus;
	private List<Photo> gallery;
}
