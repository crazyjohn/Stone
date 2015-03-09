package com.stone.db;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Router;

import com.stone.core.actor.router.RouterFactory;
import com.stone.core.db.service.IDBService;
import com.stone.db.actor.DBEntityActor;
import com.stone.db.login.DBLoginActor;
import com.stone.db.login.DBRoleActor;
import com.stone.db.msg.internal.IDBOperationWithEntity;
import com.stone.db.msg.internal.InternalCreateRole;
import com.stone.db.msg.internal.InternalGetRoleList;
import com.stone.proto.Auths.CreateRole;
import com.stone.proto.Auths.Login;

/**
 * The db master actor;
 * 
 * @author crazyjohn
 *
 */
public class DBMaster extends UntypedActor {
	/** login actor */
	private Router loginRouter;
	/** role actor */
	private ActorRef roleActor;
	/** default login routees count */
	private static final int DEFAULT_ROUTEES_COUNT = 10;
	/** dbService */
	protected final IDBService dbService;
	/** entity actor */
	protected final Map<Class<?>, ActorRef> entityActors = new HashMap<Class<?>, ActorRef>();

	public DBMaster(IDBService dbService) {
		this.dbService = dbService;
		// login actor use router
		loginRouter = RouterFactory.createRouteeActor(this.getContext(), new RoundRobinRoutingLogic(), DBLoginActor.class, DEFAULT_ROUTEES_COUNT, dbService);
		// role actor
		roleActor = this.getContext().actorOf(Props.create(DBRoleActor.class, dbService));
		this.getContext().watch(roleActor);
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Login.Builder) {
			// router login request
			loginRouter.route(msg, getSender());
		} else if (msg instanceof CreateRole.Builder) {
			// forward create role request
			roleActor.forward(msg, getContext());
		} else if (msg instanceof InternalGetRoleList) {
			roleActor.forward(msg, getContext());
		} else if (msg instanceof InternalCreateRole) {
			roleActor.forward(msg, getContext());
		} else if (msg instanceof IDBOperationWithEntity) {
			// handle db entity operation
			IDBOperationWithEntity dbMessage = (IDBOperationWithEntity) msg;
			handleDBOperation(dbMessage);
		}
	}

	/**
	 * Handle the db operation;
	 * 
	 * @param msg
	 */
	private void handleDBOperation(IDBOperationWithEntity msg) {
		ActorRef entityActor = this.entityActors.get(msg.getClass());
		if (entityActor == null) {
			// create actor
			entityActor = this.getContext().actorOf(DBEntityActor.props(msg.getClass(), dbService));
			// watch this
			this.getContext().watch(entityActor);
			this.entityActors.put(msg.getClass(), entityActor);
		}
		// forward message
		entityActor.forward(msg, getContext());
	}

	public static Props props(IDBService dbService) {
		return Props.create(DBMaster.class, dbService);
	}

}
