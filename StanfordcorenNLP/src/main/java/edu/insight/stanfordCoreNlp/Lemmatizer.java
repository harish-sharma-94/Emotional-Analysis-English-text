package edu.insight.stanfordCoreNlp;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import edu.insight.stanfordCoreNlp.reader.XmlReader;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Lemmatizer {
	public static void main(String[] args) throws IOException {

		XmlReader reader = new XmlReader();
		String path = reader.readFile();

		File headlineFile = new File(path);
		FileReader fr = new FileReader(headlineFile);
		BufferedReader br = new BufferedReader(fr);

		File lemmaFile = new File("src/main/resources/headlineLemmaFile.txt");
		FileWriter fw = new FileWriter(lemmaFile);
		BufferedWriter bw = new BufferedWriter(fw);

		Properties prop = new Properties();
		prop.setProperty("annotators", "tokenize, ssplit, pos, lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);

		String text = "";

		while((text = br.readLine()) != null) {
			String[] headlineWords = text.split("\\s+");
			bw.write(headlineWords[0] + "\t");

			for(int i = 1; i< headlineWords.length;i++) {
				Annotation document  = new Annotation(headlineWords[i]);
				pipeline.annotate(document);
				List<CoreMap> sentences = document.get(SentencesAnnotation.class);
				for(CoreMap sentence: sentences) {
					// traversing the words in the current sentence
					// a CoreLabel is a CoreMap with additional token-specific methods
					for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
						String lemmaword = token.get(LemmaAnnotation.class)    ;
						System.out.println(lemmaword);
						if(i == headlineWords.length -1) {
							bw.write(lemmaword + "\n");
						} else {
							bw.write(lemmaword + " ");
						}
					}
				}
			}
		}
		br.close();
		fr.close();
		bw.close();
		fw.close();

	}

}
