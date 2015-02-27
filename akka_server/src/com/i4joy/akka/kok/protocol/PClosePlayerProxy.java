package com.i4joy.akka.kok.protocol;

import java.io.Serializable;

public class PClosePlayerProxy implements Serializable {
	private final String message;

	public PClosePlayerProxy(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
