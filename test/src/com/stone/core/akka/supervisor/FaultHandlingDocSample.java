package com.stone.core.akka.supervisor;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.stop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scala.Option;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.dispatch.Mapper;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.japi.Util;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.stone.core.akka.supervisor.FaultHandlingDocSample.CounterApi.UseStorage;
import com.stone.core.akka.supervisor.FaultHandlingDocSample.CounterServiceApi.CurrentCount;
import com.stone.core.akka.supervisor.FaultHandlingDocSample.CounterServiceApi.Increment;
import com.stone.core.akka.supervisor.FaultHandlingDocSample.CounterServiceApi.ServiceUnavailable;
import com.stone.core.akka.supervisor.FaultHandlingDocSample.StorageApi.Entry;
import com.stone.core.akka.supervisor.FaultHandlingDocSample.StorageApi.Get;
import com.stone.core.akka.supervisor.FaultHandlingDocSample.StorageApi.StorageException;
import com.stone.core.akka.supervisor.FaultHandlingDocSample.StorageApi.Store;
import com.stone.core.akka.supervisor.FaultHandlingDocSample.WorkerApi.Progress;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class FaultHandlingDocSample {

	/**
	 * App main;
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Config config = ConfigFactory.parseString("akka.loglevel = DEBUG \n" + "akka.actor.debug.lifecycle = on");
		// system
		ActorSystem system = ActorSystem.create("FaultToleranceSample", config);
		ActorRef worker = system.actorOf(Props.create(Worker.class), "worker");
		ActorRef listener = system.actorOf(Props.create(Listener.class), "listener");
		worker.tell(WorkerApi.Start, listener);
	}

	/**
	 * Listener;
	 * 
	 * @author crazyjohn
	 *
	 */
	public static class Listener extends UntypedActor {
		final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

		@Override
		public void preStart() throws Exception {
			this.getContext().setReceiveTimeout(Duration.create("15 seconds"));
		}

		@Override
		public void onReceive(Object msg) throws Exception {
			log.debug("received msg {}", msg);
			if (msg instanceof Progress) {
				Progress progress = (Progress) msg;
				log.info("Current progress: {} %", progress.percent);
				if (progress.percent >= 100.0) {
					log.info("That's all, shutting down");
					this.getContext().system().shutdown();
				}
			} else if (msg == ReceiveTimeout.getInstance()) {
				log.error("Shuting down due tu unavailable service");
				this.getContext().system().shutdown();
			} else {
				this.unhandled(msg);
			}
		}

	}

	/**
	 * The worker api;
	 * 
	 * @author crazyjohn
	 *
	 */
	public interface WorkerApi {
		public static final Object Start = "Start";
		public static final Object Do = "Do";

		public static class Progress {
			public final double percent;

			public Progress(double percent) {
				this.percent = percent;
			}

			@Override
			public String toString() {
				return String.format("%s(%s)", getClass().getSimpleName(), percent);
			}
		}
	}

	/**
	 * Worker;
	 * 
	 * @author crazyjohn
	 *
	 */
	public static class Worker extends UntypedActor {
		final LoggingAdapter log = Logging.getLogger(this.getContext().system(), this);
		final Timeout askTimeout = new Timeout(Duration.create(5, "seconds"));
		ActorRef progressListener;
		final ActorRef counterService = this.getContext().actorOf(Props.create(CounterService.class), "counter");
		final int totalCount = 51;

		private static SupervisorStrategy strategy = new OneForOneStrategy(-1, Duration.Inf(), new Function<Throwable, Directive>() {

			@Override
			public Directive apply(Throwable t) throws Exception {
				if (t instanceof ServiceUnavailable) {
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

		@Override
		public void onReceive(Object msg) throws Exception {
			log.debug("Received msg {}", msg);
			if (msg.equals(WorkerApi.Start) && progressListener == null) {
				progressListener = this.getSender();
				this.getContext().system().scheduler()
						.schedule(Duration.Zero(), Duration.create(1, "sencond"), getSelf(), WorkerApi.Do, getContext().dispatcher(), null);
			} else if (msg.equals(WorkerApi.Do)) {
				counterService.tell(new CounterServiceApi.Increment(1), getSelf());
				counterService.tell(new CounterServiceApi.Increment(1), getSelf());
				counterService.tell(new CounterServiceApi.Increment(1), getSelf());
				// pipe
				Patterns.pipe(
						Patterns.ask(counterService, CounterServiceApi.GetCurrentCount, askTimeout).mapTo(Util.classTag(CurrentCount.class))
								.map(new Mapper<CurrentCount, Progress>() {
									@Override
									public Progress apply(CurrentCount c) {
										return new Progress(100.0 * c.count / totalCount);
									}
								}, getContext().dispatcher()), getContext().dispatcher()).to(progressListener);
			} else {
				unhandled(msg);
			}
		}

	}

	/**
	 * CounterService;
	 * 
	 * @author crazyjohn
	 *
	 */
	public interface CounterServiceApi {
		public static final Object GetCurrentCount = "GetCurrentCount";

		public static class CurrentCount {
			public final String key;
			public final long count;

			public CurrentCount(String key, long count) {
				this.key = key;
				this.count = count;
			}

			@Override
			public String toString() {
				return String.format("%s(%s, %s)", getClass().getSimpleName(), key, count);
			}
		}

		public static class Increment {
			public final long n;

			public Increment(long n) {
				this.n = n;
			}

			public String toString() {
				return String.format("%s(%s)", getClass().getSimpleName(), n);
			}
		}

		public static class ServiceUnavailable extends RuntimeException {
			private static final long serialVersionUID = 1L;

			public ServiceUnavailable(String msg) {
				super(msg);
			}

		}
	}

	public static class CounterService extends UntypedActor {
		static final Object Reconnect = "Reconnect";

		private static class SenderMsgPair {
			final ActorRef sender;
			final Object msg;

			SenderMsgPair(ActorRef sender, Object msg) {
				this.sender = sender;
				this.msg = msg;
			}
		}

		final LoggingAdapter log = Logging.getLogger(this.getContext().system(), this);
		final String key = getSelf().path().name();
		ActorRef storage;
		ActorRef counter;
		final List<SenderMsgPair> backlog = new ArrayList<SenderMsgPair>();
		final int MAX_BACKLOG = 10000;

		private static SupervisorStrategy strategy = new OneForOneStrategy(3, Duration.create("5 seconds"), new Function<Throwable, Directive>() {

			@Override
			public Directive apply(Throwable t) throws Exception {
				if (t instanceof StorageException) {
					return restart();
				} else {
					return escalate();
				}
			}

		});

		@Override
		public SupervisorStrategy supervisorStrategy() {
			return strategy;
		}

		void forwardOrPlaceInBacklog(Object msg) {
			if (counter == null) {
				if (backlog.size() >= MAX_BACKLOG) {
					throw new ServiceUnavailable("CounterService not available, lack of initial value");
				}
				backlog.add(new SenderMsgPair(getSender(), msg));
			} else {
				counter.forward(msg, getContext());
			}
		}

		@Override
		public void preRestart(Throwable reason, Option<Object> message) throws Exception {
			initStorage();
		}

		private void initStorage() {
			storage = getContext().watch(getContext().actorOf(Props.create(Storage.class), "storage"));
			if (counter != null) {
				counter.tell(new CounterApi.UseStorage(storage), getSelf());
			}
			storage.tell(new StorageApi.Get(key), getSelf());
		}

		@Override
		public void onReceive(Object msg) throws Exception {
			log.debug("Received msg {}", msg);
			if (msg instanceof Entry && ((Entry) msg).key.equals(key) && counter == null) {
				final long value = ((Entry) msg).value;
				counter = getContext().actorOf(Props.create(Counter.class, key, value));
				counter.tell(new UseStorage(storage), getSelf());
				for (SenderMsgPair each : backlog) {
					counter.tell(each.msg, each.sender);
				}
				backlog.clear();
			} else if (msg instanceof Increment) {
				this.forwardOrPlaceInBacklog(msg);
			} else if (msg.equals(CounterServiceApi.GetCurrentCount)) {
				this.forwardOrPlaceInBacklog(msg);
			} else if (msg instanceof Terminated) {
				storage = null;
				counter.tell(new UseStorage(null), getSelf());
				getContext().system().scheduler().scheduleOnce(Duration.create(10, "seconds"), getSelf(), Reconnect, getContext().dispatcher(), null);
			} else if (msg.equals(Reconnect)) {
				initStorage();
			} else {
				unhandled(msg);
			}
		}

	}

	public interface CounterApi {
		public static class UseStorage {
			public final ActorRef storage;

			public UseStorage(ActorRef storage) {
				this.storage = storage;
			}

			public String toString() {
				return String.format("%s(%s)", getClass().getSimpleName(), storage);
			}
		}
	}

	public static class Counter extends UntypedActor {
		final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
		final String key;
		long count;
		ActorRef storage;

		public Counter(String key, long initValue) {
			this.key = key;
			this.count = initValue;
		}

		@Override
		public void onReceive(Object msg) throws Exception {
			log.debug("Received msg {}", msg);
			if (msg instanceof UseStorage) {
				storage = ((UseStorage) msg).storage;
				storeCount();
			} else if (msg instanceof Increment) {
				count += ((Increment) msg).n;
				storeCount();
			} else if (msg.equals(CounterServiceApi.GetCurrentCount)) {
				getSender().tell(new CounterServiceApi.CurrentCount(key, count), getSelf());
			} else {
				this.unhandled(msg);
			}
		}

		private void storeCount() {
			if (storage != null) {
				storage.tell(new StorageApi.Store(new StorageApi.Entry(key, count)), getSelf());
			}
		}

	}

	public interface StorageApi {
		public static class Store {
			public final Entry entry;

			public Store(Entry entry) {
				this.entry = entry;
			}

			public String toString() {
				return String.format("%s(%s)", getClass().getSimpleName(), entry);
			}
		}

		public static class Entry {
			public final String key;
			public final long value;

			public Entry(String key, long value) {
				this.key = key;
				this.value = value;
			}

			public String toString() {
				return String.format("%s(%s, %s)", getClass().getSimpleName(), key, value);
			}
		}

		public static class Get {
			public final String key;

			public Get(String key) {
				this.key = key;
			}

			public String toString() {
				return String.format("%s(%s)", getClass().getSimpleName(), key);
			}
		}

		public static class StorageException extends RuntimeException {

			private static final long serialVersionUID = 1L;

			public StorageException(String msg) {
				super(msg);
			}

		}
	}

	public static class DummyDB {
		public static final DummyDB instance = new DummyDB();
		private final Map<String, Long> db = new HashMap<String, Long>();

		private DummyDB() {
		}

		public synchronized void save(String key, Long value) throws StorageException {
			if (11 <= value && value <= 14) {
				throw new StorageException("Simulated store failure " + value);
			}
			db.put(key, value);
		}

		public synchronized Long load(String key) throws StorageException {
			return db.get(key);
		}
	}

	public static class Storage extends UntypedActor {
		final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
		final DummyDB db = DummyDB.instance;

		@Override
		public void onReceive(Object msg) throws Exception {
			log.debug("Received msg {}", msg);
			if (msg instanceof Store) {
				Store store = (Store) msg;
				db.save(store.entry.key, store.entry.value);
			} else if (msg instanceof Get) {
				Get get = (Get) msg;
				Long value = db.load(get.key);
				getSender().tell(new StorageApi.Entry(get.key, value == null ? Long.valueOf(0l) : value), getSelf());
			} else {
				this.unhandled(msg);
			}
		}

	}
}
