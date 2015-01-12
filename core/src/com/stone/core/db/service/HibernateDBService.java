package com.stone.core.db.service;

import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.stone.core.entity.IEntity;

/**
 * 基于hibernate的db服务实现;
 * 
 * @author crazyjohn
 *
 */
public class HibernateDBService implements IDBService {
	protected SessionFactory sessionFactory;

	public HibernateDBService(URL hibernateCfgUrl, Properties properties) {
		Configuration config = new Configuration().configure(hibernateCfgUrl);
		sessionFactory = config.buildSessionFactory(ServiceRegistryBuilder
				.buildServiceRegistry());
	}

	@Override
	public void update(IEntity<?> entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Serializable insert(IEntity<?> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(IEntity<?> entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Class<?> entityClass, Serializable id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Object> queryByNameAndParams(String queryName, String[] params,
			Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void heartBeat() {
		// TODO Auto-generated method stub

	}

}
