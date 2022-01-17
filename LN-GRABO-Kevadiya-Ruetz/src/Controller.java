

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
	// selectedList wird beim Tabwechsel geändert, die anderen Items werden geladen
	Items[] selectedList = lists.getObstItems();

	int listcounter = 0;

	// Datenliste für TableView und TableView für die angeklickten Items
	// Je eine Column für Anzahl und Name der Items

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
		// ausgewählt ist!
		// Das läuft über das selectionModel der itemTable
		minus.disableProperty().bind(Bindings.isNull(itemTable.getSelectionModel().selectedItemProperty()));
		plus.disableProperty().bind(Bindings.isNull(itemTable.getSelectionModel().selectedItemProperty()));

		// Cell Value Factories (woraus die neuen Zellen bzw Reihen gebildet werden)
		// setzen
		// Sie werden pro Column auf die Properties aus "Items" gesetzt
		anzCol.setCellValueFactory(new PropertyValueFactory<Items, Integer>("anzahl"));

		nameCol.setCellValueFactory(new PropertyValueFactory<Items, String>("name"));

		itemTable.setItems(items);

	}

	// Wird beim drücken eines Item-Buttons ausgeführt
	public void itemAction(ActionEvent e) {

		// Hole Quelle des ActionEvents als Button und lies den Text davon aus
		Button src = (Button) e.getSource();
		Items sel = null;
		System.out.println(src.getText());

		// Hole zum Buttontext gehörendes Item aus der selectedList
		for (int i = 0; 0 <= selectedList.length; i++) {
			sel = selectedList[i];
			
			if (src.getText().equals(sel.getName())) {

				// Wenn angeklicktes Item noch nicht in items-Liste drin, füge hinzu

				if (!items.contains(sel)) {
					//
					selectedList[i].addItem();
					
					//Item zur observablelist hinzufügen
					items.add(selectedList[i]);

				} else {

					//anzCol.setEditable(true);
					sel.addItem();

					// Manuelles setzen des Items an Index i
					// wird anscheinend zum aktualisieren der View gebraucht.
					// ObservableList wird nur beim adden neuer Elemente berücksichtigt, nicht bei
					// änderung von Werten innerhalb der Items in der ObservableList
					// -Lazy Evaluation trotzdem gewährleistet, da nur bei den Klicks ausgeführt

					itemTable.getItems().set(items.indexOf(sel), sel);

				}

				break;
			}

		}

		// Summe erhöhen, Summe auf TextFeld schreiben
		eco.changeSum(sel.getPreis());
		summe.setText("Summe:              " + eco.getSum() + "€");

	}

	// TODO: Tabelleninhalt bei selected index auf Items-Anzahlproperty binden!- nur
	// vielleicht

	// Behandle klicks auf funktionale Buttons, also + / - / Abkassieren(bzw.
	// Rückgeld/OK)
	public void functionAction(ActionEvent e) {

		Button src = (Button) e.getSource();

		// Item (+dessen Index in der TableView) holen, welches gerade in der TableView
		// durch anklicken einer Reihe ausgewählt ist.
		// Das läuft über das SelectionModel der TableView
		Items focusItem = tableSelect.getSelectedItem();
		int indexOfFocus = tableSelect.getFocusedIndex();

		switch (src.getText()) {

		case "+":

			// Zähler des ausgewählten Items erhöhen, Summe erhöhen
			focusItem.addItem();
			// Das hier wieder zum aktualisieren
			itemTable.getItems().set(indexOfFocus, focusItem);
			eco.changeSum(focusItem.getPreis());

			break;

		case "-":

			// Zähler des ausgewählten Items dekrementieren
			// Wenn er dadurch auf 0 fällt, lösche aus der ObservableList
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

			abkassieren.setText("Rückgeld");
			// Der User soll nun was ins gegeben-TextField eingeben!

			break;
		case "Rückgeld":
			// Formatiere Eingabe im TextField dafür auf
			Double rückEingabe;
			//Wenn Text in gegeben unverändert (nichts eingegeben) ändere Buttontext nicht!
			//d.h. "Status" verändert sich beim Klick nicht und man kommt beim drückversuch wieder hier an
			if (gegeben.getText().length() <= 10) {
				
				System.out.println("Nichts eingegeben!");
				

			} else {
				//Hole hier den zurückgegebenen Geldwert aus dem TextField
				String rückText = (String) gegeben.getText().subSequence(10, gegeben.getText().length());
				rückEingabe = Double.parseDouble(rückText);
				double rückgeld = eco.getChange(rückEingabe);
				//Wenn zu wenig Rückgeld, ändere ebenfalls den Status nicht
				if (rückgeld >= 0) {
					abkassieren.setText("OK");
					gegeben.setText("Rückgeld: " + rückgeld);
				}
			}
			break;

		case "OK":
			//Beende die Transaktion und setze Summe und ObservableList zurück und setze alle
			//Disables/visibles/Texte auf den Anfangswert zurück
			
			Iterator<Items> iter = items.iterator();

			// Bei allen Items die Anzahl zurücksetzen
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
		summe.setText("Summe:              " + eco.getSum() + "€");
	}

	// Ändere selectedList beim wechseln des Tabs
	public void tabAction(Event e) {
		Tab src = (Tab) e.getSource();

		switch (src.getText()) {

		case "Obst":

			selectedList = lists.getObstItems();
			break;
		case "Gemuese":
			System.out.println("Gemüse?!?");
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
