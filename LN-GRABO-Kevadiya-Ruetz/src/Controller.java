

//Automatisch immer nach unten scrollen einbauen, siehe Folie 9 bei Properties und Bindings
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
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

public class Controller implements Initializable {

	ListItems lists = new ListItems();
	Economics eco = new Economics();
	// selectedList wird beim Tabwechsel ge�ndert, die anderen Items werden geladen
	Items[] selectedList = lists.getObstItems();

	int listcounter = 0;

	// Datenliste f�r TableView und TableView f�r die angeklickten Items
	// Je eine Column f�r Anzahl und Name der Items

	ObservableList<Items> items = FXCollections.observableArrayList();
	@FXML
	TableView<Items> itemTable = new TableView<Items>();
	@FXML
	TableColumn<Items, Integer> anzCol = new TableColumn<Items, Integer>();
	@FXML
	TableColumn<Items, String> nameCol = new TableColumn<Items, String>();

	TableViewSelectionModel<Items> tableSelect = itemTable.getSelectionModel();

	@FXML
	private Button abkassieren;
	@FXML
	private Button plus;
	@FXML
	private Button minus;
	@FXML
	private TextField summe;
	@FXML
	private TextField gegeben = new TextField();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// DisableProperty der +/- Buttons daran binden, ob etwas in der Tabelle
		// ausgew�hlt ist!
		// Das l�uft �ber das selectionModel der itemTable
		minus.disableProperty().bind(Bindings.isNull(itemTable.getSelectionModel().selectedItemProperty()));
		plus.disableProperty().bind(Bindings.isNull(itemTable.getSelectionModel().selectedItemProperty()));

		// Cell Value Factories (woraus die neuen Zellen bzw Reihen gebildet werden)
		// setzen
		// Sie werden pro Column auf die Properties aus "Items" gesetzt
		anzCol.setCellValueFactory(new PropertyValueFactory<Items, Integer>("anzahl"));

		nameCol.setCellValueFactory(new PropertyValueFactory<Items, String>("name"));

		itemTable.setItems(items);

	}

	// Wird beim dr�cken eines Item-Buttons ausgef�hrt
	public void itemAction(ActionEvent e) {

		// Hole Quelle des ActionEvents als Button und lies den Text davon aus
		Button src = (Button) e.getSource();
		Items sel = null;
		System.out.println(src.getText());

		// Hole zum Buttontext geh�rendes Item aus der selectedList
		for (int i = 0; 0 <= selectedList.length; i++) {
			sel = selectedList[i];
			
			if (src.getText().equals(sel.getName())) {

				// Wenn angeklicktes Item noch nicht in items-Liste drin, f�ge hinzu

				if (!items.contains(sel)) {
					//
					selectedList[i].addItem();
					
					//Item zur observablelist hinzuf�gen
					items.add(selectedList[i]);

				} else {

					//anzCol.setEditable(true);
					sel.addItem();

					// Manuelles setzen des Items an Index i
					// wird anscheinend zum aktualisieren der View gebraucht.
					// ObservableList wird nur beim adden neuer Elemente ber�cksichtigt, nicht bei
					// �nderung von Werten innerhalb der Items in der ObservableList
					// -Lazy Evaluation trotzdem gew�hrleistet, da nur bei den Klicks ausgef�hrt

					itemTable.getItems().set(items.indexOf(sel), sel);

				}

				break;
			}

		}

		// Summe erh�hen, Summe auf TextFeld schreiben
		eco.changeSum(sel.getPreis());
		summe.setText("Summe:              " + eco.getSum() + "�");

	}

	// TODO: Tabelleninhalt bei selected index auf Items-Anzahlproperty binden!- nur
	// vielleicht

	// Behandle klicks auf funktionale Buttons, also + / - / Abkassieren(bzw.
	// R�ckgeld/OK)
	public void functionAction(ActionEvent e) {

		Button src = (Button) e.getSource();

		// Item (+dessen Index in der TableView) holen, welches gerade in der TableView
		// durch anklicken einer Reihe ausgew�hlt ist.
		// Das l�uft �ber das SelectionModel der TableView
		Items focusItem = tableSelect.getSelectedItem();
		int indexOfFocus = tableSelect.getFocusedIndex();

		switch (src.getText()) {

		case "+":

			// Z�hler des ausgew�hlten Items erh�hen, Summe erh�hen
			focusItem.addItem();
			// Das hier wieder zum aktualisieren
			itemTable.getItems().set(indexOfFocus, focusItem);
			eco.changeSum(focusItem.getPreis());

			break;

		case "-":

			// Z�hler des ausgew�hlten Items dekrementieren
			// Wenn er dadurch auf 0 f�llt, l�sche aus der ObservableList
			// Summe verringern

			focusItem.decItem();
			if (focusItem.getAnzahl() <= 0) {

				items.remove(focusItem);

			} else {

				itemTable.getItems().set(indexOfFocus, focusItem);
			}

			eco.changeSum(-focusItem.getPreis());

			break;

		case "Abkassieren":
			// Gegeben-Feld einblenden und enablen, Summenfeld disablen
			gegeben.setVisible(true);
			gegeben.setDisable(false);
			summe.setDisable(true);

			abkassieren.setText("R�ckgeld");
			// Der User soll nun was ins gegeben-TextField eingeben!

			break;
		case "R�ckgeld":
			// Formatiere Eingabe im TextField daf�r auf
			Double r�ckEingabe;
			//Wenn Text in gegeben unver�ndert (nichts eingegeben) �ndere Buttontext nicht!
			//d.h. "Status" ver�ndert sich beim Klick nicht und man kommt beim dr�ckversuch wieder hier an
			if (gegeben.getText().length() <= 10) {
				
				System.out.println("Nichts eingegeben!");
				

			} else {
				//Hole hier den zur�ckgegebenen Geldwert aus dem TextField
				String r�ckText = (String) gegeben.getText().subSequence(10, gegeben.getText().length());
				r�ckEingabe = Double.parseDouble(r�ckText);
				double r�ckgeld = eco.getChange(r�ckEingabe);
				//Wenn zu wenig R�ckgeld, �ndere ebenfalls den Status nicht
				if (r�ckgeld >= 0) {
					abkassieren.setText("OK");
					gegeben.setText("R�ckgeld: " + r�ckgeld);
				}
			}
			break;

		case "OK":
			//Beende die Transaktion und setze Summe und ObservableList zur�ck und setze alle
			//Disables/visibles/Texte auf den Anfangswert zur�ck
			
			Iterator<Items> iter = items.iterator();

			// Bei allen Items die Anzahl zur�cksetzen
			while (iter.hasNext()) {
				Items i = (Items) iter.next();
				i.clear();
			}
			
			abkassieren.setText("Abkassieren");
			eco.resetSum();
			items.clear();
			summe.setDisable(false);
			gegeben.setDisable(true);
			gegeben.setVisible(false);
			gegeben.setText("Gegeben:  ");

			break;

		default:
			break;

		}
		summe.setText("Summe:              " + eco.getSum() + "�");
	}

	// �ndere selectedList beim wechseln des Tabs
	public void tabAction(Event e) {
		Tab src = (Tab) e.getSource();

		switch (src.getText()) {

		case "Obst":

			selectedList = lists.getObstItems();
			break;
		case "Gemuese":
			System.out.println("Gem�se?!?");
			selectedList = lists.getGemueseItems();

			break;
		case "Eis":

			selectedList = lists.getEisItems();
			break;
		case "Other":

			selectedList = lists.getOtherItems();
			break;

		default:
			break;

		}

	}

}
