import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller implements Initializable {
	
	ListItems lists = new ListItems();
	Economics eco = new Economics();
	Items[] selectedList = lists.getObstItems();
	
	HashMap<Items,Integer> counts = new HashMap<>();
		//Für die ListView

	
	ObservableList<String> items = FXCollections.observableArrayList();
	
		
		//mode 0: default, mode 1 :rückgeld eintragen
	int mode = 0;
	@FXML
	private ListView <String> selectedItems = new ListView <String>();
	
	
	@FXML
	private Button abkassieren;
	@FXML
	private Button plus;
	@FXML
	private Button minus;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		selectedItems.setItems(items);
		selectedItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		
		//BlaBla
	}
	
	
	public void itemAction(ActionEvent e) {
		
		System.out.println("Item button pressed");
		Button src = (Button)e.getSource();
		Items sel;
		//Hole zum Button gehörendes Item
		for(int i = 0; 0<= selectedList.length; i++) 
		{
			sel = selectedList[i];
			if(src.getText().equals(sel.getName())) 
			{
				
				
				Integer c = counts.get(sel);
				//Wenn noch keine Zählung drin, füge eine hinzu
				System.out.println("Item!Count: "+ c);
				if(c== null) 
				{
					
				counts.put(sel, 1);
				items.add("1x   "+sel.getName());
			
				}else 
				{
					//Ansonsten erhöhe Zähler um 1
					counts.put(sel, counts.get(sel)+1);
					
					//ersete eintrag in ObservableList items durch aktualisierten wert
					//
					int ind =items.indexOf(sel.getName());
					
					//AAAAH; warum die scheiss listview. tableview stattdessen ?!?
					items.set(0,counts.get(sel)+"x   "+sel.getName());
				
				}
				
				
				
				break;
			}
		
		
		}
		// Schau, ob schon in count-hashmap
		
		
			//eco.changeSum(selectedList[i].getPreis());
				
				
				
				
			
			
				
				
				
			}
			
		
		
		
		
		
		

		
	

	//Behandle klicks auf funktionale Buttons
	public void functionAction(ActionEvent e) {
		Button src = (Button)e.getSource();
		
		
	}
	
	//Ändere selectedList bei wechseln des Tabs
	public void tabAction(ActionEvent e) {
		Tab src = (Tab)e.getSource();
		
		switch (src.getText()) {

		case "Obst":
		
			//selectedList = lists.getObst(); 
			break;
		case "Gemuese":
			
			//selectedList = lists.getGemuese();
			break;
		case "Eis":
			
			//selectedList = lists.getEis();
			break;
		case "Other":
			
			//selectedList = lists.getOther();
			break;
			
		default:
			break;
		
		}
		
	}
	
	
	
	
	
}
