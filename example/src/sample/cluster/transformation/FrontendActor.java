package sample.cluster.transformation;

import static sample.cluster.transformation.JobMessages.BACKEND_REGISTRATION;

import java.util.ArrayList;
import java.util.List;

import sample.cluster.transformation.JobMessages.JobFailed;
import sample.cluster.transformation.JobMessages.Job;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;

//#frontend
public class FrontendActor extends UntypedActor {

	List<ActorRef> backends = new ArrayList<ActorRef>();
	int jobCounter = 0;

	@Override
	public void onReceive(Object message) {
		if ((message instanceof Job) && backends.isEmpty()) {
			Job job = (Job) message;
			getSender().tell(new JobFailed("Service unavailable, try again later", job), getSender());

		} else if (message instanceof Job) {
			Job job = (Job) message;
			jobCounter++;
			backends.get(jobCounter % backends.size()).forward(job, getContext());

		} else if (message.equals(BACKEND_REGISTRATION)) {
			getContext().watch(getSender());
			backends.add(getSender());

		} else if (message instanceof Terminated) {
			Terminated terminated = (Terminated) message;
			backends.remove(terminated.getActor());

		} else {
			unhandled(message);
		}
	}

}
// #frontend
