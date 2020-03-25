package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.*;

public class Dictionary {
	
	private List<String> dictionary = new LinkedList<>();
	private List<RichWord> list = new LinkedList<>();
	
	public List<String> getDictionary() {
		return dictionary;
	}

	public List<RichWord> getList() {
		return list;
	}

	public void loadDictionary(String language) {
		String nomeFile = ""+language+".txt";
		try {
			FileReader fr = new FileReader("src/main/resources/"+nomeFile);
			BufferedReader br = new BufferedReader(fr);
			String word = "";
			while ((word = br.readLine()) != null) {
				dictionary.add(word);
			}
			br.close();
		}
		catch (IOException ioe){
			System.out.println("Errore nella lettura del file");
		}
	}
	
	public boolean searchWord(String w) {
		for(String s : this.dictionary)
			if(s.equals(w))
				return true;
		return false;
	}

	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		
		this.list.clear();
		for(int i=0; i<inputTextList.size(); i++)
		{
			RichWord rw = null;
			String w = inputTextList.get(i);
			if(this.searchWord(w)==true)
				rw = new RichWord(w, true);
			else 
				rw = new RichWord(w, false);
			list.add(rw);
		}
		return list;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		
		int n = this.dictionary.size();
		this.list.clear();
		for(int i=0; i<inputTextList.size(); i++)
		{
			RichWord rw = null;
			String w = inputTextList.get(i);
			int index = ((int) (n/2));
			int indexMax = n;
			boolean found=false;
			while(found==true) {
				if(w.compareTo(this.dictionary.get(index))==0)
				{
					rw = new RichWord(w, true);
					found = true;
				}	
				else if(w.compareTo(this.dictionary.get(index))<0)
				{
					indexMax = index;
					index = ((int) (indexMax/2));
					if(index==0 && w.compareTo(this.dictionary.get(index))!=0)
					{
						rw = new RichWord(w, false);
						found = true;
					}
				}
				else
				{
					index += ((int) ((indexMax-index)/2));
					if(index==(n-1) && w.compareTo(this.dictionary.get(index))!=0)
					{
						rw = new RichWord(w, false);
						found = true;
					}
				}
				if(index==indexMax) 
				{
					rw = new RichWord(w, false);
					found = true;
				}
			}
			list.add(rw);
		}
		return list;
	}
	
	

	public List<String> wrongWords() {
		List<String> wrongList = new LinkedList<>();
		for(RichWord r : this.list)
			if(r.getCorrect()==false)
				wrongList.add(r.getWord());
		return wrongList;
	}
}
