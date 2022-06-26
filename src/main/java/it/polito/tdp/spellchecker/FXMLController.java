/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Dictionary dictionary;
	private List<String> inputTextList;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML
	private ComboBox<String> boxLingua;

	@FXML
	private Button btnCancella;

	@FXML
	private Button btnControllo;

	@FXML
	private Label lblParoleSbagliate;

	@FXML
	private Label lblPerformance;

	@FXML
	private TextArea txtCorretto;

	@FXML
	private TextArea txtDaCorreggere;

	@FXML
	void doCancella(ActionEvent event) {

		txtDaCorreggere.clear();
		txtCorretto.clear();
		lblParoleSbagliate.setText("");
		lblPerformance.setText("");

	}

	@FXML
	void doControlloOrtografico(ActionEvent event) {
		
		txtCorretto.clear();
		inputTextList = new ArrayList<String>();
		
		if (boxLingua.getValue() == null) {
			txtDaCorreggere.setText("Seleziona una lingua!");
			return;
		}
    	
    	if (!dictionary.loadDictionary(boxLingua.getValue())) {
			txtDaCorreggere.setText("Errore nel caricamento del dizionario!");
			return;
		}
		
		String inputText = txtDaCorreggere.getText();
    	
    	if (inputText.isEmpty()) {
			txtDaCorreggere.setText("Inserire un testo da correggere!");
			return;
		}
		
		inputText = inputText.replaceAll("\n", " ");
		inputText = inputText.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]?]", "");
		
		StringTokenizer st = new StringTokenizer(inputText, " ");
		
		while(st.hasMoreTokens()) {
			inputTextList.add(st.nextToken());
		}
		
		List<RichWord> outputTextList = dictionary.spellCheckText(inputTextList);
		
		int nErr = 0;
		StringBuilder Err = new StringBuilder();
		
		for(RichWord rw:outputTextList) 
			if(!rw.isCorrect()) {
				nErr++;
				Err.append(rw.getWord()+"\n");
			}
	
		txtCorretto.setText(Err.toString());
		lblParoleSbagliate.setText("Il testo contiene " + nErr + " errori");
		
	}

	@FXML
	void doSlezionaLingua(ActionEvent event) {

		if (boxLingua.getValue() != null) {

			txtDaCorreggere.setDisable(false);
			txtDaCorreggere.clear();
			txtCorretto.clear();
			btnCancella.setDisable(false);
			btnControllo.setDisable(false);

		} else {

			txtDaCorreggere.setDisable(false);
			txtDaCorreggere.clear();
			txtCorretto.clear();
			btnCancella.setDisable(false);
			btnControllo.setDisable(false);
			txtDaCorreggere.setText("Seleziona una lingua");

		}

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {

		assert boxLingua != null : "fx:id=\"boxLingua\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDaCorreggere != null : "fx:id=\"txtDaCorreggere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnControllo != null : "fx:id=\"spellCheckButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorretto != null : "fx:id=\"txtCorretto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblParoleSbagliate != null : "fx:id=\"lblErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCancella != null : "fx:id=\"clearTextButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblPerformance != null : "fx:id=\"lblStato\" was not injected: check your FXML file 'Scene.fxml'.";
		
	}

	public void setModel(Dictionary model) {

		txtDaCorreggere.setDisable(true);
		txtDaCorreggere.setText("Seleziona una lingua");

		boxLingua.getItems().addAll("English", "Italian");

		btnCancella.setDisable(true);
		btnControllo.setDisable(true);

		this.dictionary = model;
	}

}
