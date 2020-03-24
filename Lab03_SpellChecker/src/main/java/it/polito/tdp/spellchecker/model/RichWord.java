package it.polito.tdp.spellchecker.model;

public class RichWord {
	
	private String word;
	private boolean correct;
	
	public RichWord(String word, boolean correct) {
		this.word = word;
		this.correct = correct;
	}
	
	public String getWord() {
		return word;
	}
	
	public boolean getCorrect() {
		return correct;
	}

}
