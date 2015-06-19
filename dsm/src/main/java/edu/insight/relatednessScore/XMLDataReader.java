package edu.insight.relatednessScore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLDataReader {
	private final static String INSTANCEPATTERNSTRING = "<instance id=\"(.*)\">(.*)</instance>";
	public Map<String, String> set() throws FileNotFoundException {
		FileReader fr = new FileReader("src/main/resources/affectivetext_trial.xml");
		ArrayList<String> ar = new ArrayList<String>();
		BufferedReader br = new BufferedReader(fr);
		Pattern instancepattern = Pattern.compile(INSTANCEPATTERNSTRING);
		String line = null;
		Map<String, String> mp = new HashMap<String, String>();
		try {
			while((line = br.readLine()) != null) {
				Matcher instanceMatcher = instancepattern.matcher(line);
				if(instanceMatcher.find()) {
					String id = instanceMatcher.group(1).trim();
					String headline = instanceMatcher.group(2).trim();
					ar.add(headline);
					mp.put(id, headline);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fr.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mp;
	}
	public static void main(String[] args) {

	}
}
