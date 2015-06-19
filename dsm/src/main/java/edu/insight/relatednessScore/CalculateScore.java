package edu.insight.relatednessScore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.insight.dsm.ClesaService;
import edu.insight.relatednessScore.accuracy.CalculatePrecision;


public class CalculateScore {
	static ClesaService clesa = new ClesaService();
	public static enum Emotions {
		anger, disgust, fear, joy, sadness, surprise ;
	}
	public static void main(String[] args) throws IOException {


		ArrayList<String> headlineArraylist = new ArrayList<String>();
		Map<String, String> mp = new HashMap<String, String>();
		Map<String , double[] > output = new HashMap <String,double[]>();
		XMLDataReader reader = new XMLDataReader();
		try {
			mp = reader.set();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File fileanger = new File("src/main/resources/outputanger.txt");
		if(!fileanger.exists()) {
			fileanger.createNewFile();
		}
		FileWriter fw1 = new FileWriter(fileanger);
		BufferedWriter bw1 = new BufferedWriter(fw1);

		File filedisgust = new File("src/main/resources/outputdisgust.txt");
		if(!filedisgust.exists()) {
			filedisgust.createNewFile();
		}
		FileWriter fw2 = new FileWriter(filedisgust);
		BufferedWriter bw2 = new BufferedWriter(fw2);

		File filefear = new File("src/main/resources/outputfear.txt");
		if(!filefear.exists()) {
			filefear.createNewFile();
		}
		FileWriter fw3 = new FileWriter(filefear);
		BufferedWriter bw3 = new BufferedWriter(fw3);

		File filejoy = new File("src/main/resources/outputjoy.txt");
		if(!filejoy.exists()) {
			filejoy.createNewFile();
		}
		FileWriter fw4 = new FileWriter(filejoy);
		BufferedWriter bw4 = new BufferedWriter(fw4);

		File filesadness = new File("src/main/resources/outputsadness.txt");
		if(!filesadness.exists()) {
			filesadness.createNewFile();
		}
		FileWriter fw5 = new FileWriter(filesadness);
		BufferedWriter bw5 = new BufferedWriter(fw5);

		File filesurprise = new File("src/main/resources/outputsurprise.txt");
		if(!filesurprise.exists()) {
			filesurprise.createNewFile();
		}
		FileWriter fw6 = new FileWriter(filesurprise);
		BufferedWriter bw6 = new BufferedWriter(fw6);

		for(String id : mp.keySet()) {
			String headline = mp.get(id);
			//System.out.println(headline + "    " + id);
			double max = 0.0;
			String emot = "";
			double[] arr = new double[6];
			double sum = 0;
			for(Emotions emotion : Emotions.values() ) {
				double score = clesa.score(emotion.name(), "en", headline, "en");
				arr[emotion.ordinal()] = score;
				sum = sum + score;
				if(max < score) {
					max = score;
					emot = emotion.name();
					System.out.println(emot);
				}
			}
			//normalizing data 
			/*for(int i =0;i<6;i++) {
				arr[i] = (arr[i]*100.0);
				arr[i] = (double)arr[i]/(double)sum ;
				System.out.println(arr[i]);
				if(arr[i] > 50) {
					arr[i] = 1;
				} else {
					arr[i] = 0;
				}
			}*/
			bw1.write(id + "\t");
			bw2.write(id + "\t");
			bw3.write(id + "\t");
			bw4.write(id + "\t");
			bw5.write(id + "\t");
			bw6.write(id + "\t");
			

			for(int j = 0;j< 6;j++) {
				System.out.print(arr[j] + "  ");
			}
			output.put(id, arr);
			
			bw1.write(arr[0] + "\n");
			bw2.write(arr[1] + "\n");
			bw3.write(arr[2] + "\n");
			bw4.write(arr[3] + "\n");
			bw5.write(arr[4] + "\n");
			bw6.write(arr[5] + "\n");

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

		//CalculatePrecision calpre = new CalculatePrecision();
		//double precisionvalue = calpre.precision(output);
		//System.out.println(precisionvalue);
		CalculatePrecision calpre = new CalculatePrecision();
		for(Emotions emotion : Emotions.values()) {
			double precisionvalue = calpre.precision(output, emotion.name());
			System.out.println(emotion.name() + "   " + precisionvalue);
		}
	}

}
