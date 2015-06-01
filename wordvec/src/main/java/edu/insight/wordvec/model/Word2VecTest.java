package edu.insight.wordvec.model;

import java.io.File;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.util.SerializationUtils;

public class Word2VecTest {

	public static void main(String[] args) {
		Word2Vec vec = SerializationUtils.readObject(new File(args[0]));
		System.out.println(args[1] + "\t" + args[2] + vec.similarity(args[1], args[2]));
	}

}
