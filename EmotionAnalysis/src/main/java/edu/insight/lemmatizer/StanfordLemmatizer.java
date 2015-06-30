package edu.insight.lemmatizer;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordLemmatizer {
	static Properties prop;
	static StanfordCoreNLP pipeline;
	public StanfordLemmatizer() {
	    prop = new Properties();
		prop.setProperty("annotators", "tokenize, ssplit, pos, lemma");
		pipeline = new StanfordCoreNLP(prop);
	}
	public String lemmatize(String word) {
		
		Annotation document  = new Annotation(word);
		pipeline.annotate(document);
		String lemmaword = "";
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for(CoreMap sentence: sentences) {
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
				lemmaword = token.get(LemmaAnnotation.class)    ;
				System.out.println(lemmaword);
			}
		}
		return lemmaword;
	}
}
