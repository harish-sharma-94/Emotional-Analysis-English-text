package edu.insight.wordnet;

import java.io.File;
import java.io.IOException;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;

public class WordSynset {
	public static String wnhome = "/home/harish/WordNet-3.0";
	public static String path = wnhome + File.separator + "dict" ;
	public static IDictionary dict ;

	public WordSynset() throws IOException {
		dict = new Dictionary(new File(path));
		dict.open();
	}
	public ISynset wordSynsets(String inputWord) throws IOException {
		
		IIndexWord idxWord =dict.getIndexWord("dog", POS.NOUN);
		for(POS po : POS.values()) {
			 idxWord = dict.getIndexWord("reunited",po);
			 if(idxWord == null) {
				 continue;
			 } else {
				 break;
			 }
		}
		ISynset synset ;
		if(idxWord == null) {
			synset = null;
			return synset;
		}
		IWordID wordID = idxWord.getWordIDs().get(0);
		IWord word = dict . getWord ( wordID ) ;
		synset = word . getSynset () ;
		return synset;
	}
}
