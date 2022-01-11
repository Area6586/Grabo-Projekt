import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	//public static void main(String[] args) {
		//launch(args);		//Test für Commitsssssss
		//test from Gautam
		// TODO Auto-generated method stub

	//}

	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        //Anfängliche scene erzeugen und setzen
		Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
		
		
	}

}
