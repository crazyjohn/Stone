package com.stone.core.db.cassandra;

import java.util.HashMap;
import java.util.Map;

import com.alvazan.orm.api.base.Bootstrap;
import com.alvazan.orm.api.base.DbTypeEnum;
import com.alvazan.orm.api.base.NoSqlEntityManager;
import com.alvazan.orm.api.base.NoSqlEntityManagerFactory;

public class CassandraORMTest {

	public static void main(String[] args) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Bootstrap.AUTO_CREATE_KEY, "create");
		// props
		String clusterName = "PlayerCluster";
		String seeds = "localhost:9042";
		String keyspace = "stone";
		Bootstrap.createAndAddBestCassandraConfiguration(properties, clusterName, keyspace, seeds);
		NoSqlEntityManagerFactory factory = Bootstrap.create(DbTypeEnum.CASSANDRA, properties);
		NoSqlEntityManager manager = factory.createEntityManager();

		PlayerEntity player = new PlayerEntity();
		player.setId(100000);
		player.setPuid("bot100000");
		manager.put(player);
		manager.flush();
		
	}

	public static class PlayerEntity {
		private int id;
		private String puid;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getPuid() {
			return puid;
		}

		public void setPuid(String puid) {
			this.puid = puid;
		}
	}

}
