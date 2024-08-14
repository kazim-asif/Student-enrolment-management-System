package application;
	
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	Stage stage;
	FXMLLoader loader = new FXMLLoader();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			String fxmlDocPath = "src/Views/Login.fxml";
			FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
			//Create the Pane and all Details
			AnchorPane root = (AnchorPane) loader.load(fxmlStream);	
			Scene scene = new Scene(root,710,468);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Student Enrollment Management System");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
