package com.nmj.dynamic.dynacode;

import java.io.PrintStream;

import com.nmj.dynamic.sample.Postman;

public class PostmanImpl implements Postman {

	private PrintStream output;
	
	public PostmanImpl() {
		output = System.out;
	}
	
//	public PostmanImpl() throws IOException {
//		output = new PrintStream(new FileOutputStream("msg.txt"));
//	}
//
	public void deliverMessage(String msg) {
		output.println("[Postman] " + msg);
		output.flush();
	}
}
