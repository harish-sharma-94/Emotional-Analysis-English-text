package edu.insight.esa;


import edu.upm.clesa.impl.CLESA;
import eu.monnetproject.clesa.core.lang.Language;
import eu.monnetproject.clesa.core.utils.Pair;


public class ClesaService {

	public static CLESA clesa;
	private static boolean flag = false;
	//private static final String apiVersion = "1.0.0";

	public ClesaService() {		
		if(!flag){
			//String configFile = "/Users/kartik/Work/Workspaces/Workspace-1/ClesaRest/eu.monnetproject.clesa.CLESA.properties";
			String configFile = "src/main/resources/load/eu.monnetproject.clesa.CLESA.properties";
			//String configFile = "load/eu.monnetproject.clesa.CLESA.properties";			
			flag = true;
			clesa = new CLESA(configFile);
		}
	}

	public double score(String text1, String lang1, String text2, String lang2) {
		//System.out.println(text1 + " " +lang1 + " "+ text2+ " " + lang2);
		Pair<String, Language> pair1 = new Pair<String, Language>(text1, Language.getByIso639_1(lang1));
		Pair<String, Language> pair2 = new Pair<String, Language>(text2, Language.getByIso639_1(lang2));
		double score = clesa.score(pair1, pair2);
		return score;
	}

	public static void main(String[] args) {
		ClesaService clesa = new ClesaService();
		double score = clesa.score("joy", "en", "anger", "en");
		System.out.println(score);
	}

}
