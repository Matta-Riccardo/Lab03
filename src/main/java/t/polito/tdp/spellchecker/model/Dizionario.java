package t.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.event.ActionEvent;

public class Dizionario {

	private String language;
	private ArrayList <String> elencoDizionario;
	
	public Dizionario() {
		
	}
	
	public boolean caricaDizionario (String language) {
		
		if(elencoDizionario != null && this.language.equals(language)) {
			return true;
		}
		
		this.language = language;
		elencoDizionario = new ArrayList <String>();
		
		try {
			FileReader fr = new FileReader("src/main/resources/"+language+".txt");
			BufferedReader br = new BufferedReader(fr);
			
			String word;
			while((word = br.readLine())!= null) {
				elencoDizionario.add(word);
			}
			
			br.close();
			fr.close();
			
			return true;
			
		} catch (IOException e) {
			System.err.println("Errore nella lettura del file!");
			e.printStackTrace();
			return false;
		}
		
		
		
	}
	
	public List<RichWord> doSpellCheck(ArrayList<String> elenco) {
		LinkedList <RichWord> elencoParoleCercate = new LinkedList <RichWord>();
		
		for(String s : elenco) {
			
			if(elencoDizionario.contains(s)) {
				RichWord rw = new RichWord(s, true);
				elencoParoleCercate.add(rw);
			}else {
				RichWord rw = new RichWord(s, false);
				elencoParoleCercate.add(rw);
			}
		}
		
		return elencoParoleCercate;
    }
	
	public List<RichWord> doSpellCheckLinear(ArrayList<String> elenco) {
		LinkedList <RichWord> elencoParoleCercate = new LinkedList <RichWord>();
		
		for(String s : elenco) {
			boolean trovato = false;
			for(String ss : elencoDizionario) {
				if(s.equals(ss)) {
					trovato = true;
				}	
			}
			
			RichWord rw = new RichWord(s,trovato);
			elencoParoleCercate.add(rw);
			
		}
		
		return elencoParoleCercate;
	}
	
	public List<RichWord> doSpellCheckDichotomic(ArrayList<String> elenco) {
		LinkedList <RichWord> elencoParoleCercate = new LinkedList <RichWord>();
		
		for(String s : elenco) {
			boolean correct = false; 
			if(binarySearch(s)) {
				correct = true;
			}
			
			RichWord rw = new RichWord (s, correct);
			elencoParoleCercate.add(rw);
		}
		
		return elencoParoleCercate;
	}
	
	private boolean binarySearch (String daCercare) {
		
		int inizio = 0;
		int fine = elencoDizionario.size();
		int medio;
		
		while(inizio != fine) {
			medio = (inizio + fine)/2;
			
			if(daCercare.compareToIgnoreCase(elencoDizionario.get(medio)) == 0) {
				return true;
			}else if(daCercare.compareToIgnoreCase(elencoDizionario.get(medio)) > 0) {
				inizio = medio +1;
			}else {
				fine = medio; 
			}
		}
		
		return false;
	}
}
