
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller<T> implements Initializable {

	ListItems allItemLists = new ListItems();
	Economics economy = new Economics();

	Items[] selectedList = allItemLists.getEisItems();

	int listcounter = 0;
	ArrayList<String> al = new ArrayList<String>();
	List<String> myArray = new ArrayList<String>();
	ObservableList<Items> itemsObsList = FXCollections.observableArrayList();
	ObservableList<String> OrderedList = FXCollections.observableArrayList();

	@FXML
	TableView<Items> itemTable = new TableView<Items>();
	@FXML
	TableColumn<Items, Integer> count = new TableColumn<Items, Integer>();
	@FXML
	TableColumn<Items, String> nameCol = new TableColumn<Items, String>();

	@FXML
	private ListView<String> myListView;

	ObservableList<String> myList;

	@FXML
	private Button abkassierenButton;
	@FXML
	private Button plusButton;
	@FXML
	private Button minusButton;
	@FXML
	private Button Endofday;
	@FXML
	private TextField summeTextField;
	@FXML
	private TextField gegebenTextField = new TextField();

	private Alert alert = new Alert(AlertType.NONE);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		alert.setAlertType(AlertType.WARNING);

		minusButton.disableProperty().bind(Bindings.isNull(itemTable.getSelectionModel().selectedItemProperty()));
		plusButton.disableProperty().bind(Bindings.isNull(itemTable.getSelectionModel().selectedItemProperty()));

		count.setCellValueFactory(new PropertyValueFactory<Items, Integer>("anzahl"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Items, String>("name"));

		itemTable.setItems(itemsObsList);
		myListView.setItems(OrderedList);

	}

	public void itemAction(ActionEvent e) {

		Button src = (Button) e.getSource();
		Items sel = null;

		for (int i = 0; 0 <= selectedList.length; i++) {
			sel = selectedList[i];

			if (src.getText().equals(sel.getName())) {

				if (!itemsObsList.contains(sel)) {

					selectedList[i].addItem();
					itemsObsList.add(selectedList[i]);
					itemTable.scrollTo(itemsObsList.size());

				} else {

					sel.addItem();
					itemTable.getItems().set(itemsObsList.indexOf(sel), sel);

				}

				break;
			}

		}

		economy.changeSum(sel.getPreis());
		summeTextField.setText("Summe:" + economy.getSum() + "€");

	}

	public void functionAction(ActionEvent e) throws IOException {

		Button src = (Button) e.getSource();

		Items focusItem = itemTable.getSelectionModel().getSelectedItem();
		int indexOfFocus = itemTable.getSelectionModel().getFocusedIndex();

		switch (src.getText()) {
		case "+":

			plus(focusItem, indexOfFocus);
			summeTextField.setText("Summe:" + economy.getSum() + "€");
			break;

		case "-":

			minus(focusItem, indexOfFocus);
			summeTextField.setText("Summe:" + economy.getSum() + "€");
			break;

		case "Payout":

			abkassieren();
			break;

		case "rückgeld":

			rückgeld();

			break;

		case "Endofday":

			saveInDB();
			break;

		case "OK":

			reset();

			summeTextField.setText("Summe:" + economy.getSum() + "€");
			break;

		default:
			break;

		}

	}

	public void tabAction(Event e) {

		Tab src = (Tab) e.getSource();

		switch (src.getText()) {

		case "Eis":

			selectedList = allItemLists.getEisItems();
			break;

		case "Shake":

			selectedList = allItemLists.getShakeItems();
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

	private void abkassieren() {

		showListInGUI();
		gegebenTextField.setVisible(true);
		gegebenTextField.setDisable(false);
		summeTextField.setDisable(true);
		abkassierenButton.setText("rückgeld");

	}

	public void showListInGUI() {
		String s2 = "";
		for (Items i : itemsObsList) {
			String s1 = (String) (i.getAnzahl() + "x" + i.getName() + "");
			s2 = s2 + " " + s1;

		}
		OrderedList.add(s2);
	}

	public void saveInDB() throws IOException {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

		Workbook wb = new HSSFWorkbook();
//		try (OutputStream fileOut = new FileOutputStream(
//				"C:\\Users\\kevad\\Documents\\MEGAsync2\\1SEM\\Grabo\\uebung\\ExcelProject\\Javatpoint1.xls")) {
//			Sheet sheet1 = wb.createSheet("First Sheet");
//			// Sheet sheet2 = wb.createSheet("Second Sheet");
//			wb.write(fileOut);
//			System.out.println("Here");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

		OutputStream file = new FileOutputStream("TagesAbrechnung " + dateFormat.format(date) + ".xls");
		Sheet sheet1 = wb.createSheet("First Sheet");
		wb.write(file);
		System.out.println("Here");
		//BufferedWriter out = new BufferedWriter(new FileWriter(file));

//		for (String i : myListView.getItems()) {
//			System.out.println(i);
//			out.write(i);
//			out.newLine();
//		}
//		// out.write(total);
//		out.close();
		myListView.getItems().clear();

	}

	private void rückgeld() {

		Double gegebenerWert = 0.0;
		if (gegebenTextField.getText().length() <= 1) {
			alert.setContentText("Nichts eingegeben!");
			alert.show();
			gegebenTextField.setText("Geben:");

		} else {

			String gegebenText = (String) gegebenTextField.getText().subSequence(10,
					gegebenTextField.getText().length());

			try {

				gegebenerWert = Double.parseDouble(gegebenText);
				double rückgeld = economy.getChange(gegebenerWert);

				if (rückgeld >= 0) {

					abkassierenButton.setText("OK");
					gegebenTextField.setText("Rückgeld: " + rückgeld);

				} else {

					alert.setContentText("Zu wenig rückgeld!");
					alert.show();
					gegebenTextField.setText("Gegeben:  ");

				}

			} catch (Exception ex) {

				alert.setContentText("Text eingegeben! Nur Zahlen erlaubt!");
				alert.show();
				gegebenTextField.setText("Gegeben:  ");

			}

		}

		// System.out.println(economy.getTotalDay());

	}

	private void reset() {

		Iterator<Items> iter = itemsObsList.iterator();

		while (iter.hasNext()) {

			Items i = (Items) iter.next();
			i.clear();

		}

		abkassierenButton.setText("Payout");
		economy.resetSum();
		itemsObsList.clear();
		summeTextField.setDisable(false);
		gegebenTextField.setDisable(true);
		gegebenTextField.setVisible(false);
		gegebenTextField.setText("Gegeben:  ");

	}

}
