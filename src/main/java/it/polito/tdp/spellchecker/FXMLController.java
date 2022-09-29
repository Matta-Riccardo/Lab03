/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import t.polito.tdp.spellchecker.model.Dizionario;
import t.polito.tdp.spellchecker.model.RichWord;

public class FXMLController {
	
	private Dizionario model;
	private static boolean linear = false;
	private static boolean dichotomic = true;
	
	private long start;
	private long end;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnClear"
    private Button btnClear; // Value injected by FXMLLoader

    @FXML // fx:id="btnSpell"
    private Button btnSpell; // Value injected by FXMLLoader

    @FXML // fx:id="cmbLanguage"
    private ComboBox<String> cmbLanguage; // Value injected by FXMLLoader

    @FXML // fx:id="lblErrors"
    private Label lblErrors; // Value injected by FXMLLoader

    @FXML // fx:id="lblStatus"
    private Label lblStatus; // Value injected by FXMLLoader

    @FXML // fx:id="txtInsertText"
    private TextArea txtInsertText; // Value injected by FXMLLoader

    @FXML // fx:id="txtOutputResultText"
    private TextArea txtOutputResultText; // Value injected by FXMLLoader

    @FXML
    void doClearText(ActionEvent event) {
    	txtInsertText.clear();
    	txtOutputResultText.clear();
    	lblStatus.setText("");
    	lblErrors.setText("");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	txtOutputResultText.clear();
    	lblStatus.setText("");
    	String language = cmbLanguage.getValue();
    	String text = txtInsertText.getText().toLowerCase();
    	
    	ArrayList <String> textList = new ArrayList <String>();
    	List<RichWord> paroleCercate;
    	
    	if(language == null) {
    		lblErrors.setText("Inserisci una lingua");
    		return;
    	}
    	

    	
    	if(!model.caricaDizionario(language)) {
    		lblErrors.setText("Errore nel caricamento dizionario");
    		return;
    	}
    	    	
    	if(text == "") {
    		lblErrors.setText("Inserisci almeno una parola");
    		return;
    	}
    	
    	text = text.replaceAll("[.,\\/#!?$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
    	StringTokenizer st = new StringTokenizer(text, " ");
    	
    	while(st.hasMoreTokens()) {
    		textList.add(st.nextToken());
    	}
    	
    	start = System.nanoTime();
    			
    	if(linear) {
    		paroleCercate = model.doSpellCheckLinear(textList);
    	}else if(dichotomic) {
    		paroleCercate = model.doSpellCheckDichotomic(textList);
    	}else {
    		paroleCercate = model.doSpellCheck(textList);
    	}
    	
    	end = System.nanoTime();
    	
    	int errors = 0;
    	for(RichWord rw : paroleCercate) {
    		if(!rw.isCorrect()) {
    			txtOutputResultText.appendText(rw.getWord()+"\n");
    			errors++;
    		}
    	}
    	
    	lblErrors.setText("hai commesso "+errors+"errori.");
    	lblStatus.setText("hai impiegato " + (end-start)/1E9 +" secondi.");
    
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpell != null : "fx:id=\"btnSpell\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLanguage != null : "fx:id=\"cmbLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblStatus != null : "fx:id=\"lblStatus\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInsertText != null : "fx:id=\"txtInsertText\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtOutputResultText != null : "fx:id=\"txtOutputResultText\" was not injected: check your FXML file 'Scene.fxml'.";
        
        cmbLanguage.getItems().clear();
        cmbLanguage.getItems().add("English");
        cmbLanguage.getItems().add("Italian");
        

    }

    public void setModel(Dizionario model) {
    	this.model = model;
    }
    
}
