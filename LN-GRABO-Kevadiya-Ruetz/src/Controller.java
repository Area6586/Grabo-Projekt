import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller implements Initializable {
	//	ListItems lists;
		Economics eco;
		String[] selectedList;
		ObservableList<String> items = FXCollections.observableArrayList();
		//= lists.getObst();
	
	//mode 0: default, mode 1 :rückgeld eintragen
		int mode = 0;
	@FXML
	private ListView selectedItems;
	@FXML
	private Button abkassieren;
	@FXML
	private Button plus;
	@FXML
	private Button minus;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		//BlaBla
	}
	
	
	public void itemAction(ActionEvent e) {
		
		System.out.println("Item button pressed");
		Button src = (Button)e.getSource();
		//Item temp;
		for(int i = 0; 0<= selectedList.length; i++) {
			//Abändern auf Item
			if(src.getText().equals(selectedList[i])) {
				//temp = selectedList[i];
				break;
				
			}
			
		}
		
		//eco.changesum(temp.getPrice);
		selectedItems.get
		
		

		
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
