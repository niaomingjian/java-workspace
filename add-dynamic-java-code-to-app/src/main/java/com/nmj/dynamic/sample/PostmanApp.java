package com.nmj.dynamic.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import com.nmj.dynamic.utils.DynaCode;


public class PostmanApp {

	public static void main(String[] args) throws Exception {
		BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));

		Postman postman = getPostman();

		while (true) {
			System.out.print("Enter a message: ");
			String msg = sysin.readLine();

			postman.deliverMessage(msg);
		}
	}

	private static Postman getPostman() {
		DynaCode dynacode = new DynaCode();
		dynacode.addSourceDir(new File("src/main/java"));
		return (Postman) dynacode.newProxyInstance(Postman.class,
				"com.nmj.dynamic.dynacode.PostmanImpl");
	}

}
