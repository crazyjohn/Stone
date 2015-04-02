package com.stone.test.concurrent.deadlock;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class HelperDeadLockTest {
	static class Taxi {
		private Point location;
		private Point destination;
		private Dispatcher dispatcher;

		public Taxi(Dispatcher dispatcher) {
			this.dispatcher = dispatcher;
		}

		public synchronized Point getLocation() {
			return location;
		}

		public synchronized void setLocation(Point location) {
			this.location = location;
			if (location.equals(destination)) {
				this.dispatcher.notifyAvailable(this);
			}
		}

	}

	static class LocationImage {

		public void drawMarker(Point location) {
			// do something
		}
	}

	static class Dispatcher {
		private Set<Taxi> taxis = new HashSet<Taxi>();
		private Set<Taxi> aviableTaxies = new HashSet<Taxi>();

		public synchronized void notifyAvailable(Taxi taxi) {
			this.aviableTaxies.add(taxi);
		}

		public synchronized LocationImage getImage() {
			LocationImage image = new LocationImage();
			for (Taxi t : taxis) {
				image.drawMarker(t.getLocation());
			}
			return image;
		}
	}
}
