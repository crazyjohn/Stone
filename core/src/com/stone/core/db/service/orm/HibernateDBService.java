package com.stone.core.db.service.orm;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.entity.IEntity;

/**
 * The hibernate dbService;
 * 
 * @author crazyjohn
 *
 */
public class HibernateDBService implements IEntityDBService {
	private Logger logger = LoggerFactory.getLogger("error");
	/** session factory */
	private SessionFactory sessionFactory;
	/** template */
	private IHibernateTemplate template = new HibernateTransactionTemplate();

	@SuppressWarnings("deprecation")
	public HibernateDBService(URL hibernateCfgUrl, Properties properties) {
		Configuration config = new Configuration().configure(hibernateCfgUrl);
		// add properties
		config.addProperties(properties);
		// FIXME: crazyjohn build sessionFactory use
		// serviceRegistry(ServiceRegistryBuilder.buildServiceRegistry())
		sessionFactory = config.buildSessionFactory();
	}

	@Override
	public void update(final IEntity entity) {
		template.doCallback(new IHibernateOperationCallback<Void>() {
			@Override
			public Void doCallback(Session session) {
				session.update(entity);
				return null;
			}
		});
	}

	@Override
	public Serializable insert(final IEntity entity) {
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
	public IEntity get(final Class<?> entityClass, final Serializable id) {
		IEntity entity = template.doCallback(new IHibernateOperationCallback<IEntity>() {
			@Override
			public IEntity doCallback(Session session) {
				return (IEntity) session.get(entityClass, id);
			}
		});
		return entity;
	}

	@Override
	public void delete(final IEntity entity) {
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
	public <T> List<T> queryByNameAndParams(String queryName, String[] params, Object[] values) {
		return this.queryByNameAndParams(queryName, params, values, -1, -1);
	}

	@Override
	public <T> List<T> queryByNameAndParams(final String queryName, final String[] params, final Object[] values, final int maxResults,
			final int firstResult) {
		List<T> result = this.template.doCallback(new IHibernateOperationCallback<List<T>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<T> doCallback(Session session) {
				// get named query
				Query query = session.getNamedQuery(queryName);
				if (query == null) {
					logger.error(String.format("Can't find such query: %s", queryName));
					return new ArrayList<T>();
				}
				if (maxResults > -1) {
					query.setMaxResults(maxResults);
				}
				if (firstResult > -1) {
					query.setFirstResult(firstResult);
				}
				// prepare parameter
				for (int i = 0; i < params.length; i++) {
					if (values[i] instanceof Collection) {
						query.setParameterList(params[i], (Collection<?>) values[i]);
					} else {
						query.setParameter(params[i], values[i]);
					}
				}
				return query.list();
			}
		});
		return result;
	}

	@Override
	public void heartBeat() {
		// TODO: crazyjohn slove mysql connection 8 hours problems

	}

	/**
	 * 模板接口;
	 * 
	 * @author crazyjohn
	 *
	 */
	interface IHibernateTemplate {
		public <T> T doCallback(IHibernateOperationCallback<T> callback);
	}

	/**
	 * 操作回调;
	 * 
	 * @author crazyjohn
	 *
	 * @param <T>
	 */
	interface IHibernateOperationCallback<T> {
		/**
		 * 执行回调;
		 * 
		 * @param session
		 * @return
		 */
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

	@Override
	public void startup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

}
