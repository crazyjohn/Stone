package com.i4joy.akka.kok.sharding;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.i4joy.akka.kok.io.protocol.Heart;

public class ArenaSingleServer extends UntypedActor {

	public static Props props(String serverName) {
		return Props.create(ArenaSingleServer.class, serverName);
	}

	protected final Log logger = LogFactory.getLog(getClass());
	private Cancellable heart;// 心跳
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
	private String serverName;

	public ArenaSingleServer(String serverName) {
		this.serverName = serverName;
	}

	@Override
	public void preStart() throws Exception {
		this.heart = getContext().system().scheduler()// 心跳 每10秒更新一次
				.schedule(Duration.create(10, "seconds"), Duration.create(10, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		mediator.tell(new DistributedPubSubMediator.Subscribe(serverName, getSelf()), getSelf());

	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		mediator.tell(new DistributedPubSubMediator.Unsubscribe(serverName, getSelf()), getSelf());
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {

	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
//		LoadingCache<String, String> cache2 = CacheBuilder.newBuilder().maximumSize(1000).build();
//		String str = cache2.get("key", new Callable<String>() {
//
//			@Override
//			public String call() throws Exception {
//				System.out.println("call");
//				return "Value";
//			}
//		});
//		System.out.println(str);

		LoadingCache<String, String> cache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.SECONDS).maximumSize(4).build(new CacheLoader<String, String>() {

			@Override
			public String load(String key) throws Exception {
				System.out.println("load " + key);
				return "value";
			}

		});
		System.out.println(cache.get("key"));
		System.out.println(cache.get("key"));
		System.out.println(cache.get("key2"));
		System.out.println(cache.get("key2"));
		System.out.println(cache.size());
		Thread.sleep(2000);
		cache.cleanUp();
		System.out.println(cache.size());
		Thread.sleep(8000);
		System.out.println("#############");
		cache.cleanUp();
		System.out.println(cache.size());
		cache.put("key2", "key2");
		System.out.println(cache.get("key2"));
		cache.cleanUp();
		System.out.println(cache.size());
		
	}

}
