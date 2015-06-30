package edu.insight.stanfordCoreNlp.reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlReader {
	private final static String INSTANCEPATTERNSTRING = "<instance id=\"(.*)\">(.*)</instance>";

	public String readFile() throws IOException {
		String path = "src/main/resources/headlineFile.txt";
		File file = new File(path);
		if(!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		File readHeadlineFile = new File("src/main/resources/affectivetext_trial.xml");
		FileReader fr = new FileReader(readHeadlineFile);
		BufferedReader br = new BufferedReader(fr);
		Pattern instancePattern = Pattern.compile(INSTANCEPATTERNSTRING);
		
		String line = "";
		while((line = br.readLine()) != null) {
			Matcher instanceMatch = instancePattern.matcher(line);
			if(instanceMatch.find()) {
				String id = instanceMatch.group(1).trim();
				String headline = instanceMatch.group(2).trim();
				String temp = headline.replaceAll("[^A-Za-z']", " ").replaceAll("\\s+", " ").replaceAll("[']", "").trim();
				temp = temp.toLowerCase();
				bw.write(id + " "+ temp + "\n");
			}
		}
		br.close();
		fr.close();
		bw.close();
		fw.close();
		
		return path;
	
	}
}
	
