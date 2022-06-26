package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Dictionary {

	private List<String> dictionary;
	private String language;
	
	public Dictionary() {

	}
	
	public boolean loadDictionary(String language) {
		
		if(dictionary != null && this.language.equals(language)) 
			return true;
		
		dictionary = new ArrayList<String>();
		//dictionary = new LinkedList<String>();
		this.language = language;
		
		FileReader fr;
		try {
			fr = new FileReader("src/main/resources/"+language+".txt");
			BufferedReader br = new BufferedReader(fr);
			String word;
			while((word =br.readLine()) != null) {
				dictionary.add(word.toLowerCase());
			}
			
			Collections.sort(dictionary);

			br.close();
			System.out.println("Dizionario " + language + " caricato. Trovate " + dictionary.size() + " parole.");
			
			return true;
			
		} catch (IOException e) {
			System.out.println("Errore nella lettura del file");
			return false;
		}
		
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList) {
		
		//List<RichWord> parole = new ArrayList<RichWord>();
		List<RichWord> parole = new LinkedList<RichWord>();
		
		for(String p:inputTextList) 
			parole.add(new RichWord(p, dictionary.contains(p.toLowerCase())));
		
		
		return parole;
		
	}
	
	public List<RichWord> spellCheckTextLinear (List<String> inputTextList) {
		
		//List<RichWord> parole = new ArrayList<RichWord>();
		List<RichWord> parole = new LinkedList<RichWord>();
		
		for(String p:inputTextList) {
			RichWord rw = new RichWord(p);
			boolean found = false;
			for (String word : dictionary) {
				if(word.equalsIgnoreCase(p)) {
					found = true;
					break;
				}
			}
			
			if (found) {
				rw.setCorrect(true);	
			} else {
				rw.setCorrect(false);
			}

			parole.add(rw);
		}
		
		
		return parole;
		
	}
	
	public List<RichWord> spellCheckDichotomic (List<String> inputTextList) {
		
		//List<RichWord> parole = new ArrayList<RichWord>();
		List<RichWord> parole = new LinkedList<RichWord>();
		
		for(String p:inputTextList) {
			RichWord rw = new RichWord(p);
			
			if (binarySearch(p.toLowerCase())) {
				rw.setCorrect(true);	
			} else {
				rw.setCorrect(false);
			}

			parole.add(rw);
		}
		
		
		return parole;
		
	}
	
	private boolean binarySearch(String stemp) {
		int inizio = 0;
		int fine = dictionary.size();
		
		while(inizio != fine) {
			int medio = inizio + (fine-inizio)/2;
			if(stemp.compareToIgnoreCase(dictionary.get(medio)) == 0)
				return true;
			else if(stemp.compareToIgnoreCase(dictionary.get(medio)) > 0)
				inizio = medio + 1;
			else 
				fine = medio;
		}
		
		return false;
	}
	
}
