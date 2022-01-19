


import javafx.fxml.Initializable;

import javafx.scene.control.Button;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Controller implements Initializable {

	ListItems allItemLists = new ListItems();
	Economics economy = new Economics();
	
	//selectedList wird beim Tabwechsel ge�ndert, die entsprechenden Items werden geladen
	Items[] selectedList = allItemLists.getObstItems();

	int listcounter = 0;



	ObservableList<Items> itemsObsList = FXCollections.observableArrayList();
	@FXML
	TableView<Items> itemTable = new TableView<Items>();
	@FXML
	TableColumn<Items, Integer> anzCol = new TableColumn<Items, Integer>();
	@FXML
	TableColumn<Items, String> nameCol = new TableColumn<Items, String>();

	

	@FXML
	private Button abkassierenButton;
	@FXML
	private Button plusButton;
	@FXML
	private Button minusButton;
	@FXML
	private TextField summeTextField;
	@FXML
	private TextField gegebenTextField = new TextField();

	private Alert alert = new Alert(AlertType.NONE);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		alert.setAlertType(AlertType.WARNING);
		// DisableProperty der +/- Buttons daran binden, ob etwas in der Tabelle
		// ausgew�hlt ist!
		minusButton.disableProperty().bind(Bindings.isNull(itemTable.getSelectionModel().selectedItemProperty()));
		plusButton.disableProperty().bind(Bindings.isNull(itemTable.getSelectionModel().selectedItemProperty()));
		//Festlegen, dass Neue Zellen bzw Rows aus den Properties der Items gebildet werden
		anzCol.setCellValueFactory(new PropertyValueFactory<Items, Integer>("anzahl"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Items, String>("name"));
		
		itemTable.setItems(itemsObsList);

	}

	//ActionHandler f�r verschiedene Arten von Buttons. "onAction" in GUI.fxml festgelegt
	//Generell: Auswahl der Funktion anhand von Text im Button
	public void itemAction(ActionEvent e) {
		
		Button src = (Button) e.getSource();
		Items sel = null;
		
		// Hole zum Buttontext geh�rendes Item aus der selectedList
		for (int i = 0; 0 <= selectedList.length; i++) {
			sel = selectedList[i];
			
			if (src.getText().equals(sel.getName())) {

				if (!itemsObsList.contains(sel)) {
					
					selectedList[i].addItem();
					itemsObsList.add(selectedList[i]);
					itemTable.scrollTo(itemsObsList.size());

				} else {
			
					sel.addItem();
					// Manuelles setzen des Items an Index i
					// -wird anscheinend zum aktualisieren der View gebraucht.
					// ObservableList wird nur beim adden neuer Elemente ber�cksichtigt, nicht bei
					// �nderung von Werten innerhalb der Items in der ObservableList!
					// -Lazy Evaluation trotzdem gew�hrleistet, da nur bei Klicks ausgef�hrt
					itemTable.getItems().set(itemsObsList.indexOf(sel), sel);
					
				}

				break;
			}

		}
		
		economy.changeSum(sel.getPreis());
		summeTextField.setText("Summe:              " + economy.getSum() + "�");

	}
	
	public void functionAction(ActionEvent e) {

		Button src = (Button) e.getSource();
	
		Items focusItem = itemTable.getSelectionModel().getSelectedItem();	
		//Dieser Index wird wieder zur manuellen Aktualisierung der TableView ben�tigt
		int indexOfFocus = itemTable.getSelectionModel().getFocusedIndex();

		switch (src.getText()) {
			case "+":

				plus(focusItem, indexOfFocus);
				summeTextField.setText("Summe:              " + economy.getSum() + "�");	
				break;

			case "-":

				minus(focusItem, indexOfFocus);
				summeTextField.setText("Summe:              " + economy.getSum() + "�");
				break;

			case "Abkassieren":
	
				abkassieren();
				break;
			
			case "R�ckgeld":
			
				r�ckgeld();
				break;

			case "OK":
			
				reset();
				summeTextField.setText("Summe:              " + economy.getSum() + "�");
				break;

			default:
				break;

		}
		
	}

	
	public void tabAction(Event e) {
	
		Tab src = (Tab) e.getSource();
		
		switch (src.getText()) {

			case "Obst":

				selectedList = allItemLists.getObstItems();
				break;
			
			case "Gemuese":
			
				selectedList = allItemLists.getGemueseItems();
				break;
			
			case "Eis":

				selectedList = allItemLists.getEisItems();
				break;
			
			case "Other":

				selectedList = allItemLists.getOtherItems();
				break;

			default:
				
				break;
			
		}

	}
	

	
	private void plus(Items focusedItem, int focusindex) {
	
		focusedItem.addItem();
		itemTable.getItems().set(focusindex, focusedItem);
		economy.changeSum(focusedItem.getPreis());

	
	}
	
	private void minus(Items focusedItem, int focusindex) {
		focusedItem.decItem();
	
		if (focusedItem.getAnzahl() <= 0) {

			itemsObsList.remove(focusedItem);

		} else {

			itemTable.getItems().set(focusindex, focusedItem);
		}

		economy.changeSum(-focusedItem.getPreis());

	
	}

	//Beim dr�cken auf abkassieren/r�ckgeld/ok wird anhand des Textwechsels der Zustand des Programms gewechselt
	//�ndere den Zustand bei Fehleingaben nicht!
	
	private void abkassieren() {

		gegebenTextField.setVisible(true);
		gegebenTextField.setDisable(false);
		summeTextField.setDisable(true);
		abkassierenButton.setText("R�ckgeld");
	
	}

	private void r�ckgeld() {
	
		Double gegebenerWert =0.0;
		if (gegebenTextField.getText().length() <= 10) {
		
			alert.setContentText("Nichts eingegeben!");
			alert.show();
			gegebenTextField.setText("Gegeben:  ");
	
		}else{
			//Hole hier den zur�ckgegebenen Geldwert aus dem TextField
			String gegebenText = (String) gegebenTextField.getText().subSequence(10, gegebenTextField.getText().length());
		
			try {
				
				gegebenerWert = Double.parseDouble(gegebenText);
				double r�ckgeld = economy.getChange(gegebenerWert);
		
				if (r�ckgeld >= 0) {
				
					abkassierenButton.setText("OK");
					gegebenTextField.setText("R�ckgeld: " + r�ckgeld);
		
				}else {
				
					alert.setContentText("Zu wenig R�ckgeld!");
					alert.show();
					gegebenTextField.setText("Gegeben:  ");
				
				}
		
			
			}catch(Exception ex){
		
				alert.setContentText("Text eingegeben! Nur Zahlen erlaubt!");
				alert.show();
				gegebenTextField.setText("Gegeben:  ");
		
			}
		
		}
	
	}


	private void reset() {
	
		Iterator<Items> iter = itemsObsList.iterator();
		// Bei allen Items in der ObservableList die Anzahl zur�cksetzen
		while (iter.hasNext()) {
		
			Items i = (Items) iter.next();
			i.clear();
		
		}
	
		abkassierenButton.setText("Abkassieren");
		economy.resetSum();
		itemsObsList.clear();
		summeTextField.setDisable(false);
		gegebenTextField.setDisable(true);
		gegebenTextField.setVisible(false);
		gegebenTextField.setText("Gegeben:  ");
	
	}
	

}


