package com.simpleworks.store.manager;

import javax.annotation.Resource;

import com.simpleworks.store.BaseSimpleWorksService;
import com.simpleworks.store.dataaccess.SimpleWorksDataAccessObjectManager;

/**
 * The Class BaseSimpleWorksManager.
 */
public class BaseSimpleWorksManager extends BaseSimpleWorksService {
	/** The delivery interface data access object manager. */
	@Resource
	SimpleWorksDataAccessObjectManager simpleWorksDataAccessObjectManager;

	/**
	 * Gets the simple works data access object manager.
	 *
	 * @return the simple works data access object manager
	 */
	public SimpleWorksDataAccessObjectManager getSimpleWorksDataAccessObjectManager() {
		return simpleWorksDataAccessObjectManager;
	}

}
