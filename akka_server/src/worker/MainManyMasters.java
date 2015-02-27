package worker;

import static worker.Main.startBackend;
import static worker.Main.startFrontend;
import static worker.Main.startWorker;
import akka.actor.Address;

public class MainManyMasters {

  public static void main(String[] args) throws InterruptedException {
    Address joinAddress = startBackend(null, "backend-shard1");
    Thread.sleep(5000);
    startBackend(joinAddress, "backend-shard1");
    startWorker(joinAddress);
    Thread.sleep(5000);
    startFrontend(joinAddress);

    startBackend(joinAddress, "backend-shard2");
    startBackend(joinAddress, "backend-shard2");
    startWorker(joinAddress);
    startWorker(joinAddress);
    startWorker(joinAddress);
  }

}