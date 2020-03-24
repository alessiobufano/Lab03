/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class FXMLController {

	private ObservableList<String> list = FXCollections.observableArrayList();
	private Dictionary model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> choiceLang;

    @FXML
    private TextArea txtInput;

    @FXML
    private TextArea txtOutput;
    
    @FXML
    private Button btmCheck;
    
    @FXML
    private Button btmClear;

    @FXML
    private Label txtNumErrors;

    @FXML
    private Text txtTime;

    @FXML
    void doClearText(ActionEvent event) {
    	this.txtOutput.clear();
    	this.txtInput.clear();
    	this.txtTime.setText("");
    	this.txtNumErrors.setText("");
    	this.choiceLang.setDisable(false);
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	long startTime = System.currentTimeMillis();
    	
    	this.txtOutput.clear();
    	String language = this.choiceLang.getValue();
    	this.choiceLang.setDisable(true);
    	this.model.loadDictionary(language);
    
    	String text = this.txtInput.getText();
    	text = text.replaceAll("[.,?\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
    	
    	List<String> textList = new LinkedList<>();
    	String array[] = text.split(" ");
    	for(int i=0; i<array.length;i++)
			textList.add(array[i]);
		this.model.spellCheckText(textList);
		
		for(int j=0; j<this.model.wrongWords().size(); j++)
			this.txtOutput.appendText(""+this.model.wrongWords().get(j)+"\n");
		this.txtNumErrors.setText("The text contains "+this.model.wrongWords().size()+" errors");
		
    	long endTime = System.currentTimeMillis();
    	double time = (endTime-startTime)/1000.0;
    	this.txtTime.setText("SpellCheck completed in "+time+" seconds");
    	
    	this.choiceLang.setDisable(false);
    }

    @FXML
    void initialize() {
        assert choiceLang != null : "fx:id=\"choiceLang\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btmCheck != null : "fx:id=\"btmCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumErrors != null : "fx:id=\"txtNumErrors\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btmClear != null : "fx:id=\"btmClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTime != null : "fx:id=\"txtTime\" was not injected: check your FXML file 'Scene.fxml'.";

        list.addAll("English", "Italian");
        choiceLang.setItems(list);
        choiceLang.setValue("");
    }

	public void setModel(Dictionary model) {
		this.model = model;
	}
}
