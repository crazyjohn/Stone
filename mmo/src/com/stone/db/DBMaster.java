package com.stone.db;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Router;

import com.stone.core.actor.router.RouterFactory;
import com.stone.core.data.msg.IDBMessage;
import com.stone.core.data.uuid.IUUIDService;
import com.stone.core.data.uuid.UUIDService;
import com.stone.core.data.uuid.UUIDType;
import com.stone.core.db.service.orm.IEntityDBService;
import com.stone.core.entity.IHumanSubEntity;
import com.stone.db.actor.DBEntityActor;
import com.stone.db.actor.DBHumanActor;
import com.stone.db.actor.DBLoginActor;
import com.stone.db.actor.DBRoleActor;
import com.stone.db.actor.router.BoundedHumanRoutingLogic;
import com.stone.db.entity.HumanEntity;
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
	protected final IEntityDBService dbService;
	/** entity actor */
	protected final Map<Class<?>, ActorRef> entityActors = new HashMap<Class<?>, ActorRef>();
	/** human actor */
	protected final Router humanRouter;
	/** uuid */
	private IUUIDService uuidActor;
	private Logger logger = LoggerFactory.getLogger(DBMaster.class);

	public DBMaster(IEntityDBService dbService) {
		this.dbService = dbService;
		// uuid
		int regionId = 1;
		int serverId = 1;
		// FIXME: crazyjohn actor or singleton?
		uuidActor = UUIDService.buildUUIDService(dbService, UUIDType.values(), regionId, serverId);
		// this.getContext().actorOf(UUIDService.props(dbService,
		// UUIDType.values(), regionId, serverId));
		// this.getContext().watch(uuidActor);
		// login actor use router
		loginRouter = RouterFactory.createChildActorRouteeRouter(this.getContext(), new RoundRobinRoutingLogic(), DBLoginActor.class,
				DEFAULT_ROUTEES_COUNT, dbService, uuidActor);
		// role actor
		roleActor = this.getContext().actorOf(Props.create(DBRoleActor.class, dbService, uuidActor), "dBRoleActor");
		this.getContext().watch(roleActor);
		// human actor
		humanRouter = RouterFactory.createChildActorRouteeRouter(this.getContext(), new BoundedHumanRoutingLogic(), DBHumanActor.class,
				DEFAULT_ROUTEES_COUNT, 10000/**
				 * config this
				 */
				, dbService);

	}

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return super.supervisorStrategy();
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
		} else if (msg instanceof IDBMessage) {
			// handle db entity operation
			IDBMessage dbMessage = (IDBMessage) msg;
			handleDBOperation(dbMessage);
		} else if (msg instanceof Terminated) {
			Terminated terminate = (Terminated) msg;
			logger.info(String.format("Actor terminated: %s", terminate.getActor()));
		} else {
			this.unhandled(msg);
		}
	}

	/**
	 * Handle the db operation;
	 * 
	 * @param msg
	 */
	private void handleDBOperation(IDBMessage msg) {
		// is sub entity
		if (IHumanSubEntity.class.isAssignableFrom(msg.getEntityClass())) {
			this.humanRouter.route(msg, getSender());
			return;
		}
		// is human entity
		if (msg.getEntityClass().equals(HumanEntity.class)) {
			humanRouter.route(msg, getSender());
			return;
		}
		// other entity
		ActorRef entityActor = this.entityActors.get(msg.getEntityClass());
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

	public static Props props(IEntityDBService dbService) {
		return Props.create(DBMaster.class, dbService);
	}

}
