package com.stone.core.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpApi {

	public static void main(String[] args) throws IOException {
		// create server
		HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8888), 100);
		// create context
		server.createContext("/test", new HttpHandler() {
			@Override
			public void handle(HttpExchange exchange) throws IOException {
				String body = "This is sun's htttp server.";
				response(exchange, body.getBytes("UTF-8"));
				System.out.println("Test handler.");
			}
		});
		// start server
		server.start();
	}

	/**
	 * Do response;
	 * 
	 * @param exchange
	 * @param datas
	 * @throws IOException
	 */
	protected static void response(HttpExchange exchange, byte[] datas) throws IOException {
		exchange.sendResponseHeaders(200, datas.length);
		exchange.getResponseBody().write(datas);
		exchange.getResponseBody().close();
	}

}
