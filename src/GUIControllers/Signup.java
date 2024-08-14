package GUIControllers;

import java.io.FileInputStream;
import java.io.IOException;

import GUIControllers.Login;
import Controllers.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Signup {

	private MainController controller;
	
	public MainController getController() {
		return controller;
	}
	public void setController(MainController controller) {
		this.controller = controller;
	}
	
	@FXML // fx:id="error"
    private Text error; // Value injected by FXMLLoader
	
	@FXML // fx:id="fname"
    private TextField fname; // Value injected by FXMLLoader

    @FXML // fx:id="lname"
    private TextField lname; // Value injected by FXMLLoader

    @FXML // fx:id="loginbtn"
    private Button loginbtn; // Value injected by FXMLLoader

    @FXML // fx:id="password"
    private TextField password; // Value injected by FXMLLoader

    @FXML // fx:id="signupbtn"
    private Button signupbtn; // Value injected by FXMLLoader

    @FXML // fx:id="username"
    private TextField username; // Value injected by FXMLLoader
	
	
	
	@FXML
    void handleLogin(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
		Stage currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
		String fxmlDocPath = "src/Views/Login.fxml";
		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
		AnchorPane pane = (AnchorPane) loader.load(fxmlStream);
		Login obj= loader.getController();
		obj.setController(controller);
		Scene scene = new Scene(pane,710,468);
		currentStage.setScene(scene);
		currentStage.show();
    }

    @FXML
    void handleSignup(ActionEvent event) throws IOException {
    	
    	String uname = username.getText();
    	String pass = password.getText();
    	String firstName = fname.getText();
    	String lastName = lname.getText();

    	if (uname.isEmpty() || pass.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
    	    // Display an error message or handle the empty fields as per your application's requirements
    	    error.setText("Error: Please fill all the fields!");
    	} else {
    	    // Call the createUser() method
    	    controller.createUser(uname, pass, firstName, lastName);
    	    
    	    FXMLLoader loader = new FXMLLoader();
			Stage currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			String fxmlDocPath = "src/Views/Login.fxml";
			FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
			AnchorPane pane = (AnchorPane) loader.load(fxmlStream);
			Login obj= loader.getController();
			obj.setController(controller);
			Scene scene = new Scene(pane,710,468);
			currentStage.setScene(scene);
			currentStage.show();
    	    
    	}
			
    }
    
	
}
