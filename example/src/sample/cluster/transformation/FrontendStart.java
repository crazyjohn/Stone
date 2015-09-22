package sample.cluster.transformation;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import sample.cluster.transformation.JobMessages.Job;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnSuccess;
import akka.util.Timeout;
import static akka.pattern.Patterns.ask;

public class FrontendStart {

	public static void start(String[] args) {
		// Override the configuration of the port when specified as program
		// argument
		final String port = args.length > 0 ? args[0] : "0";
		final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
				.withFallback(ConfigFactory.parseString("akka.cluster.roles = [frontend]")).withFallback(ConfigFactory.load("simpleCluster"));

		ActorSystem system = ActorSystem.create("ClusterSystem", config);
		// create front actor
		final ActorRef frontendActor = system.actorOf(Props.create(FrontendActor.class), "frontend");
		// schedule
		final FiniteDuration interval = Duration.create(2, TimeUnit.SECONDS);
		final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
		final ExecutionContext context = system.dispatcher();
		final AtomicInteger counter = new AtomicInteger();
		system.scheduler().schedule(interval, interval, new Runnable() {
			public void run() {
				ask(frontendActor, new Job("ping-" + counter.incrementAndGet()), timeout).onSuccess(new OnSuccess<Object>() {
					public void onSuccess(Object result) {
						System.out.println(result);
					}
				}, context);
			}

		}, context);

	}
}
