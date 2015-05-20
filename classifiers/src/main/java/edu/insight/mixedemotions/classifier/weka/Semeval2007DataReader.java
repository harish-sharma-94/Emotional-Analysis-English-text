package edu.insight.mixedemotions.classifier.weka;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.insight.mixedemotions.classifier.common.BasicFileTools;

public class Semeval2007DataReader {

	public static enum Emotions {
		ANGER, DISGUST, FEAR, JOY, SADNESS, SURPRISE;
	}

	private final static String INSTANCEPATTERNSTRING = "<instance id=\"(.*)\">(.*)</instance>";


	public static Map<String, Map<Emotions, Integer>> readData(String dataFilePath, String goldFilePath){
		Map<String, Map<Emotions, Integer>> data = new HashMap<String, Map<Emotions, Integer>>();
		Map<String, String> sentencesMap = new HashMap<String, String>();
		BufferedReader xmlBr = BasicFileTools.getBufferedReaderFile(dataFilePath);
		BufferedReader goldBr = BasicFileTools.getBufferedReaderFile(goldFilePath); 

		Pattern instancePattern = Pattern.compile(INSTANCEPATTERNSTRING);

		String line = null;
		try {
			while((line=xmlBr.readLine())!=null){
				Matcher instanceMatcher = instancePattern.matcher(line);
				if(instanceMatcher.find()){
					String id = instanceMatcher.group(1).trim();
					String instance = instanceMatcher.group(2).trim();
					sentencesMap.put(id, instance);
					//System.out.println(instance);
				}
			}
			line = null;
			while((line=goldBr.readLine())!=null){
				if(!"".equals(line.trim())){
					System.out.println(line);
					String[] goldMatcher = line.split("\\s+");
					String id = goldMatcher[0].trim();
					String anger = goldMatcher[1].trim();
					String disgust = goldMatcher[2].trim();
					String fear = goldMatcher[3].trim();
					String joy = goldMatcher[4].trim();
					String sadness = goldMatcher[5].trim();
					String surprise = goldMatcher[6].trim();
					Map<Emotions, Integer> instanceEmotions = new HashMap<Emotions, Integer>();
					instanceEmotions.put(Emotions.ANGER, Integer.parseInt(anger)); 	
					instanceEmotions.put(Emotions.FEAR, Integer.parseInt(fear)); 	
					instanceEmotions.put(Emotions.DISGUST, Integer.parseInt(disgust)); 	
					instanceEmotions.put(Emotions.JOY, Integer.parseInt(joy)); 	
					instanceEmotions.put(Emotions.SADNESS, Integer.parseInt(sadness)); 	
					instanceEmotions.put(Emotions.SURPRISE, Integer.parseInt(surprise)); 	
					data.put(sentencesMap.get(id), instanceEmotions);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void main(String[] args) {
		String dataFilePath = "src/main/resources/Semeval  2007/trial/affectivetext_trial.xml";
		String goldFilePath = "src/main/resources/Semeval  2007/trial/affectivetext_trial.emotions.gold";
		Map<String, Map<Emotions, Integer>> data = Semeval2007DataReader.readData(dataFilePath, goldFilePath);
		for(String sentence : data.keySet()){
			Map<Emotions, Integer> emotions = data.get(sentence);
			Integer anger = emotions.get(Semeval2007DataReader.Emotions.ANGER);
			System.out.println(sentence);
			System.out.println(anger);
		}

	}

}
