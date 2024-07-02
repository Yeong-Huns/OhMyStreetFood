package org.omsf.store.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreInfo {
	private Store store;
	private Photo photo;
	private List<Menu> menus;
	private List<Photo> gallery;
}
