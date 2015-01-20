package com.stone.core.db.service;

import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.entity.IEntity;

/**
 * 基于hibernate的db服务实现;
 * 
 * @author crazyjohn
 *
 */
public class HibernateDBService implements IDBService {
	private Logger logger = LoggerFactory.getLogger(HibernateDBService.class);
	/** session factory */
	private SessionFactory sessionFactory;
	/** template */
	private IHibernateTemplate template = new HibernateTransactionTemplate();

	public HibernateDBService(URL hibernateCfgUrl, Properties properties) {
		Configuration config = new Configuration().configure(hibernateCfgUrl);
		sessionFactory = config.buildSessionFactory(ServiceRegistryBuilder.buildServiceRegistry());
	}

	@Override
	public void update(final IEntity<?> entity) {
		template.doCallback(new IHibernateOperationCallback<Void>() {
			@Override
			public Void doCallback(Session session) {
				session.update(entity);
				return null;
			}
		});
	}

	@Override
	public Serializable insert(final IEntity<?> entity) {
		Serializable id = template.doCallback(new IHibernateOperationCallback<Serializable>() {
			@Override
			public Serializable doCallback(Session session) {
				Serializable result = session.save(entity);
				return result;
			}
		});
		return id;
	}

	@Override
	public void delete(final IEntity<?> entity) {
		template.doCallback(new IHibernateOperationCallback<Void>() {
			@Override
			public Void doCallback(Session session) {
				session.delete(entity);
				return null;
			}
		});
	}

	@Override
	public void deleteById(final Class<?> entityClass, final Serializable id) {
		template.doCallback(new IHibernateOperationCallback<Void>() {
			@Override
			public Void doCallback(Session session) {
				session.delete(entityClass.getSimpleName(), id);
				return null;
			}
		});
	}

	@Override
	public List<Object> queryByNameAndParams(String queryName, String[] params, Object[] values, int maxResult, int start) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void heartBeat() {
		// TODO Auto-generated method stub

	}

	interface IHibernateTemplate {
		public <T> T doCallback(IHibernateOperationCallback<T> callback);
	}

	interface IHibernateOperationCallback<T> {
		public T doCallback(Session session);
	}

	/**
	 * 事务模板;<br>
	 * 实现sesion事务的正确提交以及错误回滚模板框架;
	 * 
	 * @author crazyjohn
	 *
	 */
	class HibernateTransactionTemplate implements IHibernateTemplate {
		@Override
		public <T> T doCallback(IHibernateOperationCallback<T> callback) {
			Session session = null;
			Transaction transaction = null;
			T result = null;
			try {
				session = sessionFactory.openSession();
				transaction = session.beginTransaction();
				result = callback.doCallback(session);
				transaction.commit();
				return result;
			} catch (Exception e) {
				// log and rollback
				logger.error(String.format("Db operation error"), e);
				if (transaction != null) {
					transaction.rollback();
				}
			} finally {
				if (session != null) {
					session.close();
				}
			}
			return result;
		}

	}

}
