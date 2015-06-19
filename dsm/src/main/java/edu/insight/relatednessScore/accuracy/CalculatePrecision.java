package edu.insight.relatednessScore.accuracy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.insight.relatednessScore.CalculateScore.Emotions;

public class CalculatePrecision {
	public enum Emotion {
		anger, disgust, fear, joy, sadness, surprise;
	}
	public Map<String, double[]> realemotion() throws IOException {
		File file = new File("src/main/resources/affectivetext_trial.emotions.gold");
		FileReader fr = new FileReader(file);
		BufferedReader br  = new BufferedReader(fr);
		File fileanger = new File("src/main/resources/realanger.txt");
		if(!fileanger.exists()) {
			fileanger.createNewFile();
		}
		FileWriter fw1 = new FileWriter(fileanger);
		BufferedWriter bw1 = new BufferedWriter(fw1);
		File filedisgust = new File("src/main/resources/realdisgust.txt");
		if(!filedisgust.exists()) {
			filedisgust.createNewFile();
		}
		FileWriter fw2 = new FileWriter(filedisgust);
		BufferedWriter bw2 = new BufferedWriter(fw2);
		File filefear = new File("src/main/resources/realfear.txt");
		if(!filefear.exists()) {
			filefear.createNewFile();
		}
		FileWriter fw3 = new FileWriter(filefear);
		BufferedWriter bw3 = new BufferedWriter(fw3);
		File filejoy = new File("src/main/resources/realjoy.txt");
		if(!filejoy.exists()) {
			filejoy.createNewFile();
		}
		FileWriter fw4 = new FileWriter(filejoy);
		BufferedWriter bw4 = new BufferedWriter(fw4);
		File filesadness = new File("src/main/resources/realsadness.txt");
		if(!filesadness.exists()) {
			filesadness.createNewFile();
		}
		FileWriter fw5 = new FileWriter(filesadness);
		BufferedWriter bw5 = new BufferedWriter(fw5);
		File filesurprise = new File("src/main/resources/realsurprise.txt");
		if(!filesurprise.exists()) {
			filesurprise.createNewFile();
		}
		FileWriter fw6 = new FileWriter(filesurprise);
		BufferedWriter bw6 = new BufferedWriter(fw6);
		Map<String, double[]> emotion = new HashMap<String, double[]>();
		String line = "";
		while((line = br.readLine()) != null) {
			if(!"".equals(line.trim())) {
				String[] goldvalues = line.split("\\s+");
				String id = goldvalues[0].trim();
				int max = 0;
				String emot = "";
				int i = 1;
				String temp = "";
				String finalemotion = "";
				double[] arr = new double[6];
				for(Emotion emotions : Emotion.values()) {
					emot = emotions.name();
					temp = goldvalues[i].trim();
					arr[emotions.ordinal()] = Integer.parseInt(temp);
					//if(arr[emotions.ordinal()] > 50){
					//arr[emotions.ordinal()] = 1;
					//} else {
					//arr[emotions.ordinal()] = 0;
					//}
					if(Integer.parseInt(temp) > max) {
						finalemotion = emot;
						max = Integer.parseInt(temp);
					}
					i++;
				}
				bw1.write(id + "\t");
				bw2.write(id + "\t");
				bw3.write(id + "\t");
				bw4.write(id + "\t");
				bw5.write(id + "\t");
				bw6.write(id + "\t");
				System.out.println(id + "   " + arr[0]);
				bw1.write(arr[0] + "\n");
				bw2.write(arr[1] + "\n");
				bw3.write(arr[2] + "\n");
				bw4.write(arr[3] + "\n");
				bw5.write(arr[4] + "\n");
				bw6.write(arr[5] + "\n");
				emotion.put(id, arr);
			}
		}
		bw1.close();
		fw1.close();
		bw2.close();
		fw2.close();
		bw3.close();
		fw3.close();
		bw4.close();
		fw4.close();
		bw5.close();
		fw5.close();
		bw6.close();
		fw6.close();
		return emotion;
	}
	public double precision(Map <String, double[]> m1, String precisionemotion) throws IOException {
		//precision = number of correct told/ number of told
		Map<String, double[]> m2  = realemotion();
		int count = 0;
		int total = 0;
		int index = 0;
		for(Emotions emot : Emotions.values()) {
			if(emot.name().equals(precisionemotion)) {
				index = emot.ordinal();
				break;
			}
		}
		for(String id : m1.keySet()) {
			double[] emotion = m1.get(id);
			//System.out.println("emotion " + emotion);
			double[] realemotion = m2.get(id);
			//System.out.println("realemotion " + realemotion);
			if(emotion[index] == 1) {
				total++;
				if(realemotion[index] == 1) {
					count++;
				}
			}

		}
		double precisionvalue ;
		//System.out.print(precisionemotion + "  ");
		//System.out.print("count " + count + "  ");
		//System.out.println("size " + total);
		precisionvalue = (double)count/(double)(total*1.0) ;
		return precisionvalue;
	}
	/*
	public double recall(Map <String, String> m1, Map <String, String> m2) {
		//recall = number of correct right told/ total number of correct right.
		double count = 0.0;
		for(String id : m1.keySet()) {
			String emotion = m1.get(id);
			String realemotion = m2.get(id);
			if(emotion.equals(realemotion)) {
				count++;
			}
		}
		double recallvalue ;
		recallvalue = count/(m1.size()) ;
		return recallvalue;
	}
	 */
}
