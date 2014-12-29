package com.stone.aop;

import java.util.Arrays;

import org.aspectj.bridge.IMessage;
import org.aspectj.bridge.MessageHandler;
import org.aspectj.tools.ajc.Main;

public class AopMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main compiler = new Main();
		MessageHandler handler = new MessageHandler();
		compiler.run(args, handler);
		IMessage[] msg = handler.getMessages(null, true);
		System.out.println("messages: " + Arrays.asList(msg));
	}

}
