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
		
		public void openSetLocation(Point location) {
			boolean reachedDestination;
			synchronized (this) {
				this.location = location;
				reachedDestination = this.destination.equals(location);
			}
			if (reachedDestination) {
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
		
		public LocationImage openGetImage() {
			// do a copy
			Set<Taxi> copy;
			synchronized (this) {
				copy = new HashSet<Taxi>(this.taxis);
			}
			// iterate this copy
			LocationImage image = new LocationImage();
			for (Taxi t : copy) {
				image.drawMarker(t.getLocation());
			}
			return image;
		}
	}
}
