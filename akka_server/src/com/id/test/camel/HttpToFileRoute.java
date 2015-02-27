package com.id.test.camel;

import java.io.IOException;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class HttpToFileRoute {  
	  
    public static void main(String args[]) throws Exception {  
        CamelContext context = new DefaultCamelContext();  
        context.addRoutes(new RouteBuilder() {  
  
            public void configure() {
                from("jetty:http://127.0.0.1:8080/secret").process(new Processor() {  
                    public void process(Exchange e) throws IOException {  
                            System.out.println("Received exchange: " + e.getIn()); 
                            System.out.println(e.getIn().getBody(String.class));  
                    }  
                });  
            }  
        }); 
        
        context.start();
  
        Thread.sleep(3000000);//let http server works 30 seconds  
        context.stop();  
    }  
}  
