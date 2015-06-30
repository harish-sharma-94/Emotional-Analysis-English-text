package edu.insight.wordvec.model;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.insight.wordvec.model.xmlRead.XmlFileReader;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.util.SerializationUtils;


public class Word2VecTest {

	public enum Emotions {
		anger, disgust, fear, joy, saadness, surprise;
	}

	public static void main(String[] args) throws IOException {
		File file =  new File("src/main/resources/wikiWord2Vec.model") ;
		Word2Vec vec = SerializationUtils.readObject(file);
		//System.out.println("computer" + "\t" + "desktop" + vec.similarity("computer", "desktop"));
		String path = "/home/harish/workspace/mixedemotions/classifier/src/main/resources/Semeval  2007/trial/affectivetext_trial.xml";
		//String path = "/home/harish/affectivetext_trial.xml";
		XmlFileReader reader = new XmlFileReader(path);
		Map<String, String> headlineMap = new HashMap<String, String>();
		Map<String, Double> lineScore = new HashMap<String, Double>();
		headlineMap = reader.readfile();
		for(Emotions emotion : Emotions.values()) {
			System.out.println(emotion.name() + "   started");
			double headlinescore = 0.0;
			File fi = new File("/home/harish/" + emotion.name() + "word2vec.txt");
			if(!fi.exists()) {
				fi.createNewFile();
			}
			FileWriter fw = new FileWriter(fi);
			BufferedWriter bw = new BufferedWriter(fw);
			for(String id : headlineMap.keySet()) {
				headlinescore = 0.0;
				String headline = headlineMap.get(id);
				String indiWords[] = headline.split("\\s+");
				for(int j = 0 ;j< indiWords.length ; j++) {
					double score = vec.similarity(emotion.name(), indiWords[j]);
					headlinescore = score+headlinescore;
				}
				System.out.println(headline + "   " + headlinescore);
				
				bw.write(id + "\t");
				bw.write(headlinescore + "\n");
				lineScore.put(headline, headlinescore);																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																					
			}
			System.out.println(emotion.name() + "   completed");
			bw.close();
			fw.close();
			
			System.out.println( emotion.name()+   "   file closed");
		}
	}

}
