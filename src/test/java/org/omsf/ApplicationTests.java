package org.omsf;

import org.junit.jupiter.api.Test;
import org.omsf.store.dao.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;


@SpringJUnitConfig(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Transactional
@Rollback
public class ApplicationTests {
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Test
	public void testInsert() {
		storeRepository.createStore();
	}
}
