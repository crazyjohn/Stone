package com.i4joy.akka.kok.camel;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.duration.Duration;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.japi.Function;

import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.protocol.PCreateDBDateSource;
import com.i4joy.util.Tools;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ump.impl.JdbcRandom_nameDAO;
import com.ump.model.Random_name;

/**
 * 
 * @author DongLei Endpoint 管理
 */
public class KOKEndpointManager extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static ArrayList<String> boyNames = new ArrayList<String>();// 随机名字男
	public static ArrayList<String> girlNames = new ArrayList<String>();// 随机名字女

	public KOKEndpointManager() {
		int initPort = Integer.parseInt(TextProperties.getText("HTTPPORT"));// HTTP初始端口
		for (int i = 0; i < 5; i++) {
			getContext().actorOf(MyEndpoint.getProps("jetty:http://" + TextProperties.getText("HTTPIP") + ":" + (initPort + i) + "/" + MyEndpoint.LOGIN, MyEndpoint.LOGIN), "LoginEndpoint" + i);
			getContext().actorOf(MyEndpoint.getProps("jetty:http://" + TextProperties.getText("HTTPIP") + ":" + (initPort + i) + "/" + MyEndpoint.REGISTE, MyEndpoint.REGISTE), "RegistEndpoint" + i);
			getContext().actorOf(MyEndpoint.getProps("jetty:http://" + TextProperties.getText("HTTPIP") + ":" + (initPort + i) + "/" + MyEndpoint.PLAYERCREATE, MyEndpoint.PLAYERCREATE),
					"PCEndpoint" + i);
			getContext().actorOf(MyEndpoint.getProps("jetty:http://" + TextProperties.getText("HTTPIP") + ":" + (initPort + i) + "/" + MyEndpoint.GETRANDOMNAME, MyEndpoint.GETRANDOMNAME),
					"RNEndpoint" + i);
			getContext().actorOf(MyEndpoint.getProps("jetty:http://" + TextProperties.getText("HTTPIP") + ":" + (initPort + i) + "/" + MyEndpoint.PLAYERLOGIN, MyEndpoint.PLAYERLOGIN),
					"PLEndpoint" + i);
			getContext().actorOf(MyEndpoint.getProps("jetty:http://" + TextProperties.getText("HTTPIP") + ":" + (initPort + i) + "/" + MyEndpoint.UPDATE, MyEndpoint.UPDATE), "UPEndpoint" + i);
			getContext().actorOf(MyEndpoint.getProps("jetty:http://" + TextProperties.getText("HTTPIP") + ":" + (initPort + i) + "/" + MyEndpoint.QUICKREGIST, MyEndpoint.QUICKREGIST),
					"QREndpoint" + i);
			getContext().actorOf(MyEndpoint.getProps("jetty:http://" + TextProperties.getText("HTTPIP") + ":" + (initPort + i) + "/" + MyEndpoint.CHANGEPWD, MyEndpoint.CHANGEPWD), "CPEndpoint" + i);
		}
		init();
	}

	public void init() {
		PCreateDBDateSource pdbds_config = new PCreateDBDateSource(TextProperties.getText("DB_USER"), TextProperties.getText("DB_PASSWORD"), TextProperties.getText("DB_ADDRESS")
				+ TextProperties.getText("DB_NAME_CONFIG") + "?rewriteBatchedStatements=true", TextProperties.getText("DB_DRIVER"), 1, 1, 1, 1, 1, TextProperties.getText("DB_NAME_CONFIG"));
		ComboPooledDataSource ds = getDBResource(pdbds_config);
		JdbcRandom_nameDAO dao = new JdbcRandom_nameDAO(ds);
		HashMap<String, Random_name> hm = dao.getALL();
		ds.close();
		Random_name[] randomNames = new Random_name[hm.size()];
		hm.values().toArray(randomNames);
		for (int i = 0; i < randomNames.length; i++) {
			if (randomNames[i].getSex() == 0) {
				girlNames.add(randomNames[i].getName());
			} else {
				boyNames.add(randomNames[i].getName());
			}
		}
		Tools.printlnInfo("随机名字初始化完成！", logger);
	}

	@Override
	public void onReceive(Object message) throws Exception {

	}

	private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("10 second"), new Function<Throwable, Directive>() {
		public Directive apply(Throwable t) {
			if (t instanceof ArithmeticException) {
				return resume();
			} else if (t instanceof NullPointerException) {
				return restart();
			} else if (t instanceof IllegalArgumentException) {
				return stop();
			} else {
				return escalate();
			}
		}
	});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}

	public static ComboPooledDataSource getDBResource(PCreateDBDateSource pcdd) {
		try {
			ComboPooledDataSource dataSource = new ComboPooledDataSource();
			dataSource.setPassword(pcdd.getPassword());
			dataSource.setUser(pcdd.getUser());
			dataSource.setJdbcUrl(pcdd.getJdbcUrl());
			dataSource.setDriverClass(pcdd.getDirver());
			dataSource.setInitialPoolSize(pcdd.getInitialPoolSize());
			dataSource.setMinPoolSize(pcdd.getMinPoolSize());
			dataSource.setMaxPoolSize(pcdd.getMaxPoolSize());
			dataSource.setMaxStatements(pcdd.getMaxStatements());
			dataSource.setMaxIdleTime(pcdd.getMaxIdleTime());
			return dataSource;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
