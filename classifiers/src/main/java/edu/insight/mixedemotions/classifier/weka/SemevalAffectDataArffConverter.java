package edu.insight.mixedemotions.classifier.weka;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import edu.insight.mixedemotions.classifier.weka.Semeval2007DataReader.Emotions;

public class SemevalAffectDataArffConverter {

	private static String dataFilePath = "src/main/resources/Semeval  2007/trial/affectivetext_trial.xml";
	private static String goldFilePath = "src/main/resources/Semeval  2007/trial/affectivetext_trial.emotions.gold";

	public static void main(String[] args) throws IOException {	
		String emotionsArff = "src/main/resources/emotionanger.arff";	
		Map<String, Map<Emotions, Integer>> datamap = Semeval2007DataReader.readData(dataFilePath, goldFilePath);
		ArrayList<Attribute> atts;
		Instances data;
		double[] vals;
		for(String sentence : datamap.keySet()){
			Map<Emotions, Integer> emotions = datamap.get(sentence);
			Integer anger = emotions.get(Semeval2007DataReader.Emotions.ANGER);
			System.out.println(sentence);
			System.out.println(anger);
		}
		atts = new ArrayList<Attribute>();
		atts.add(new Attribute("Headline", (ArrayList<String>) null));//attribute contains string values
		atts.add(new Attribute("anger"));//added an attribute that contains numeric value
		data = new Instances("anger file", atts, 0);

		for(String sentence : datamap.keySet()){
			Map<Emotions, Integer> emotions = datamap.get(sentence);
			Integer anger = emotions.get(Semeval2007DataReader.Emotions.ANGER);
			vals = new double[data.numAttributes()];//returns number of attributes
			vals[0] = data.attribute(0).addStringValue(sentence);
			vals[1] = anger;
			data.add(new DenseInstance(1.0, vals));
			System.out.println(sentence);
			System.out.println(anger);
		}
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		try {
			//saver.setFile(new File("Output/starsem2015/SWAN_34adviceCorpus.arff")); 
			saver.setFile(new File(emotionsArff));
			//saver.setFile(new File("Output/starsem2015/SWAN_34TestTA.arff")); 
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
		}			
		System.out.println(data);
		arrfForClassificationMultiLabel();
		arrfForClassificationMultiClass();
	}

	public static void arrfForClassificationMultiLabel() {


		String emotionsArff = "src/main/resources/emotionclassificationmultilabel.arff";	
		Map<String, Map<Emotions, Integer>> datamap = Semeval2007DataReader.readData(dataFilePath, goldFilePath);
		ArrayList<Attribute> atts;
		Instances data;
		double[] vals;

		atts = new ArrayList<Attribute>();
		atts.add(new Attribute("Headline", (ArrayList<String>) null));//attribute contains string values
		atts.add(new Attribute("anger"));//added an attribute that contains numeric value
		atts.add(new Attribute("fear"));
		atts.add(new Attribute("disust"));
		atts.add(new Attribute("joy"));
		atts.add(new Attribute("sadness"));
		atts.add(new Attribute("surprise"));
		data = new Instances("classification file", atts, 0);

		for(String sentence : datamap.keySet()){
			Map<Emotions, Integer> emotions = datamap.get(sentence);
			Integer anger = emotions.get(Semeval2007DataReader.Emotions.ANGER);
			Integer fear = emotions.get(Semeval2007DataReader.Emotions.FEAR);
			Integer disgust = emotions.get(Semeval2007DataReader.Emotions.DISGUST);
			Integer joy = emotions.get(Semeval2007DataReader.Emotions.JOY);
			Integer sadness = emotions.get(Semeval2007DataReader.Emotions.SADNESS);
			Integer surprise = emotions.get(Semeval2007DataReader.Emotions.SURPRISE);
			vals = new double[data.numAttributes()];//returns number of attributes
			vals[0] = data.attribute(0).addStringValue(sentence);
			if(anger > 50) { 
				vals[1] = 1;
			} else {
				vals[1] = 0;
			}
			if(fear > 50) { 
				vals[2] = 1;
			} else {
				vals[2] = 0;
			}
			if(disgust > 50) { 
				vals[3] = 1;
			} else {
				vals[3] = 0;
			}
			if(joy > 50) { 
				vals[4] = 1;
			} else {
				vals[4] = 0;
			}
			if(sadness > 50) { 
				vals[5] = 1;
			} else {
				vals[5] = 0;
			}
			if(surprise > 50) { 
				vals[6] = 1;
			} else {
				vals[6] = 0;
			}
			data.add(new DenseInstance(1.0, vals));
		}
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		try {
			saver.setFile(new File(emotionsArff));
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
		}			
		System.out.println(data);

	}

	public static void arrfForClassificationMultiClass() {
		String emotionsArff = "src/main/resources/emotionclassificationmulticlass.arff";	
		Map<String, Map<Emotions, Integer>> datamap = Semeval2007DataReader.readData(dataFilePath, goldFilePath);
		ArrayList<Attribute> atts;
		ArrayList<String> attVals;
		Instances data;
		double[] vals;
		atts = new ArrayList<Attribute>();
		atts.add(new Attribute("Headline", (ArrayList<String>) null));
		attVals = new ArrayList<String>();
		for(Emotions emotion : Emotions.values()){
			attVals.add(emotion.name());
		}
		atts.add(new Attribute("label_class", attVals));
		data = new Instances("classification file", atts, 0);
		for(String sentence : datamap.keySet()){
			Map<Emotions, Integer> emotions = datamap.get(sentence);
			Emotions maxEmotion = null;
			int maxEmotionValue = Integer.MIN_VALUE;
			for(Emotions emotion : emotions.keySet()){				
				if(emotions.get(emotion)>maxEmotionValue){
					maxEmotion = emotion;
					maxEmotionValue = emotions.get(emotion);
				}
			}		
			vals = new double[data.numAttributes()];//returns number of attributes
			vals[0] = data.attribute(0).addStringValue(sentence);
			vals[1] = attVals.indexOf(maxEmotion.name());
			data.add(new DenseInstance(1.0, vals));
		}	
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		try {
			saver.setFile(new File(emotionsArff));
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
		}			
		System.out.println(data);
	}
}

