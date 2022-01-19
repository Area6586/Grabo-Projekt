

//Automatisch immer nach unten scrollen einbauen, siehe Folie 9 bei Properties und Bindings
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
	//selectedList wird beim Tabwechsel ge�ndert, die anderen Items werden geladen
	Items[] selectedList = allItemLists.getObstItems();

	int listcounter = 0;

	// Datenliste f�r TableView und TableView f�r die angeklickten Items
	// Je eine Column f�r Anzahl und Name der Items

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
		// Das l�uft �ber das selectionModel der itemTable
		minusButton.disableProperty().bind(Bindings.isNull(itemTable.getSelectionModel().selectedItemProperty()));
		plusButton.disableProperty().bind(Bindings.isNull(itemTable.getSelectionModel().selectedItemProperty()));

		// Cell Value Factories (woraus die neuen Zellen bzw Reihen gebildet werden)
		// setzen
		// Sie werden pro Column auf die Properties aus "Items" gesetzt
		anzCol.setCellValueFactory(new PropertyValueFactory<Items, Integer>("anzahl"));

		nameCol.setCellValueFactory(new PropertyValueFactory<Items, String>("name"));

		itemTable.setItems(itemsObsList);

	}

	// Wird beim dr�cken eines Item-Buttons ausgef�hrt
	public void itemAction(ActionEvent e) {
		
		// Hole Quelle des ActionEvents als Button und lies den Text davon aus
		Button src = (Button) e.getSource();
		Items sel = null;
		//System.out.println(src.getText());
		

		// Hole zum Buttontext geh�rendes Item aus der selectedList
		for (int i = 0; 0 <= selectedList.length; i++) {
			sel = selectedList[i];
			
			if (src.getText().equals(sel.getName())) {

				// Wenn angeklicktes Item noch nicht in items-Liste drin, f�ge hinzu

				if (!itemsObsList.contains(sel)) {
					//
					selectedList[i].addItem();
					
					//Item zur observablelist hinzuf�gen
					itemsObsList.add(selectedList[i]);
					
					//Immer ans Ende scrollen und aktuellstes Item anzeigen
					itemTable.scrollTo(itemsObsList.size());

				} else {

					
					sel.addItem();

					// Manuelles setzen des Items an Index i
					// wird anscheinend zum aktualisieren der View gebraucht.
					// ObservableList wird nur beim adden neuer Elemente ber�cksichtigt, nicht bei
					// �nderung von Werten innerhalb der Items in der ObservableList
					// -Lazy Evaluation trotzdem gew�hrleistet, da nur bei den Klicks ausgef�hrt

					itemTable.getItems().set(itemsObsList.indexOf(sel), sel);

				}

				break;
			}

		}

		// Summe erh�hen, Summe auf TextFeld schreiben
		economy.changeSum(sel.getPreis());
		summeTextField.setText("Summe:              " + economy.getSum() + "�");

	}

	

	// Behandle klicks auf funktionale Buttons, also + / - / Abkassieren(bzw.
	// R�ckgeld/OK)
	public void functionAction(ActionEvent e) {

		Button src = (Button) e.getSource();

		// Item (+dessen Index in der TableView) holen, welches gerade in der TableView
		// durch anklicken einer Reihe ausgew�hlt ist.
		// Das l�uft �ber das SelectionModel der TableView
		
		Items focusItem = itemTable.getSelectionModel().getSelectedItem();	
		int indexOfFocus = itemTable.getSelectionModel().getFocusedIndex();
		
	

		switch (src.getText()) {

		case "+":

			plus(focusItem, indexOfFocus);
			summeTextField.setText("Summe:              " + economy.getSum() + "�");
			
			
			break;

		case "-":

			// Z�hler des ausgew�hlten Items dekrementieren
			// Wenn er dadurch auf 0 f�llt, l�sche aus der ObservableList
			// Summe verringern

			minus(focusItem, indexOfFocus);
			summeTextField.setText("Summe:              " + economy.getSum() + "�");
			
			break;

		case "Abkassieren":
			// Gegeben-Feld einblenden und enablen, Summenfeld disablen
			abkassieren();
			// Der User soll nun was ins gegeben-TextField eingeben!

			break;
		case "R�ckgeld":
			// Formatiere Eingabe im TextField daf�r auf
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

	// �ndere selectedList beim wechseln des Tabs
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
	// Das hier wieder zum aktualisieren
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

private void abkassieren() {
	gegebenTextField.setVisible(true);
	gegebenTextField.setDisable(false);
	summeTextField.setDisable(true);

	abkassierenButton.setText("R�ckgeld");
	
}

private void r�ckgeld() {
	
	Double r�ckEingabe =0.0;
	//Wenn Text in gegeben unver�ndert (nichts eingegeben) �ndere Buttontext nicht!
	//d.h. "Status" ver�ndert sich beim Klick nicht und man kommt beim dr�ckversuch wieder hier an
	if (gegebenTextField.getText().length() <= 10) {
		
		
		alert.setContentText("Nichts eingegeben!");
		alert.show();
		gegebenTextField.setText("Gegeben:  ");
	
	
		}else{
	
		//Hole hier den zur�ckgegebenen Geldwert aus dem TextField
			String r�ckText = (String) gegebenTextField.getText().subSequence(10, gegebenTextField.getText().length());
		
			try {
				r�ckEingabe = Double.parseDouble(r�ckText);
			
				double r�ckgeld = economy.getChange(r�ckEingabe);
		
				if (r�ckgeld >= 0) {
					abkassierenButton.setText("OK");
					gegebenTextField.setText("R�ckgeld: " + r�ckgeld);
		
				}else {
					//alert.setAlertType(AlertType.ERROR);
					alert.setContentText("Zu wenig R�ckgeld!");
					alert.show();
					gegebenTextField.setText("Gegeben:  ");
				
				}
		
			
			}catch(Exception ex){
		
		
				alert.setContentText("Text eingegeben! Nur Zahlen erlaubt!");
				alert.show();
				gegebenTextField.setText("Gegeben:  ");
		
			}
		
		
		//Wenn zu wenig R�ckgeld, �ndere ebenfalls den Status nicht
		
		}
	
}


private void reset() {
	Iterator<Items> iter = itemsObsList.iterator();

	// Bei allen Items die Anzahl zur�cksetzen
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


