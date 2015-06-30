package edu.insight.emoAna;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import edu.insight.lemmatizer.*;
import edu.insight.esa.*;
import edu.insight.wordnet.*;
import edu.insight.readFile.*;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.morph.WordnetStemmer;

public class EmotionAnalysis {

	public static Map<String, String> idHeadline = new HashMap<String, String>();

	public enum Emotions {
		anger, disgust, fear, joy, sadness, surprise;
	}
	public static void main(String[] args) throws IOException {

		File file = new File("src/main/resources/headlineEmotions.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw= new BufferedWriter(fw);

		XMLDataReader reader = new XMLDataReader();
		idHeadline = reader.set();

		WordSynset wordsyn = new WordSynset();		
		ClesaService clesa = new ClesaService();
		StanfordLemmatizer lemma = new StanfordLemmatizer();

		double sco = 0.0;
		double headlinescore = 0.0;
		for(Emotions emotion : Emotions.values()) {
			for(String id : idHeadline.keySet()) {
				String headline = idHeadline.get(id);
				String[] headlineWords = headline.split("\\s+");
				headlinescore = 0.0;
				for(int i = 0; i < headlineWords.length; i++) {
					String word = headlineWords[i].toLowerCase().trim();
					word = word.replaceAll("[^a-z]", "");
					System.out.println(word);
					String lemmaWord = lemma.lemmatize(word);
					if("".equals(lemmaWord.trim())){
						continue;
					}
					if("get".equalsIgnoreCase(lemmaWord)){
						System.out.println("harish");
					}
					ISynset synset = wordsyn.wordSynsets(lemmaWord);
					sco = 0.0;
					if(synset == null) {
						System.out.println("abcddfsdgfjsarklgjdsfioghdasfoigas");
						headlinescore += clesa.score(word, "en", emotion.name(), "en");
						continue;
					}
					double num = 0;
					for ( IWord w : synset . getWords () ) {
						num++;
						//System.out.println(w.getLemma());
						sco = sco + clesa.score(w.getLemma(), "en", emotion.name(), "en");
					}
					sco = (double)(sco*1.0) / (double)(num*1.0) ;
					headlinescore += sco;
				}
				bw.write(id + "\t" + headlinescore + "\n");
			}
		}

		bw.close();
		fw.close();

	}
}
