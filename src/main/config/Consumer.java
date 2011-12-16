package main.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Consumer {
	private static Consumer consumer = null;
	private String token;
	private String secret;
	
	private Consumer() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("consumer.txt")));
			String s;
			while((s = br.readLine()) != null){
				token = s.split(",")[0];
				secret = s.split(",")[1];
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Consumer getInstance(){
		if(consumer != null) return consumer;
		return new Consumer();
	}
	
	public String getToken(){
		return token;
	}
	
	public String getSecret(){
		return secret;
	}
	
	public static void main(String args[]){
		new Consumer();
	}
}
