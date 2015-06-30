package edu.insight.readFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLDataReader {

	private final static String INSTANCEPATTERNSTRING = "<instance id=\"(.*)\">(.*)</instance>";

	public Map<String, String> set() throws IOException {

		FileReader fr = new FileReader("src/main/resources/affectivetext_trial.xml");
		ArrayList<String> ar = new ArrayList<String>();
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		Map<String, String> mp = new HashMap<String, String>();

		Pattern instancepattern = Pattern.compile(INSTANCEPATTERNSTRING);

		while((line = br.readLine()) != null) {
			Matcher instanceMatcher = instancepattern.matcher(line);
			if(instanceMatcher.find()) {
				String id = instanceMatcher.group(1).trim();
				String headline = instanceMatcher.group(2).trim();
				ar.add(headline);
				mp.put(id, headline);
			}
		}

		br.close();
		fr.close();

		return mp;
	}
	public static void main(String[] args) {

	}
}
