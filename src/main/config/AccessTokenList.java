package main.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import twitter4j.auth.AccessToken;

public class AccessTokenList extends ArrayList<AccessToken>{
	private static AccessTokenList list;
	
	private AccessTokenList() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("accesstokens.txt")));
			String s;
			while((s = br.readLine()) != null){
				this.add(new AccessToken(s.split(",")[0], s.split(",")[1]));
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static AccessTokenList getInstance() {
		if(list != null) return list;
		return new AccessTokenList();
	}
}
