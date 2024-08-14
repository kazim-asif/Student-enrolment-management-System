package GUIControllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import GUIControllers.*;
import Models.User;
import Controllers.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login{

	private MainController controller = new MainController();
	
	public MainController getController() {
		return controller;
	}
	public void setController(MainController controller) {
		this.controller = controller;
	}
	
	@FXML
    private Text error;
	
    @FXML
    private Button loginbtn;

    @FXML
    private TextField password;

    @FXML
    private Button sessionend;

    @FXML
    private Button signupbtn;

    @FXML
    private TextField username;

    @FXML
    void EndSession(ActionEvent event) {

    }

    @FXML
    void handleLogin(ActionEvent event) throws IOException {

    	User user = controller.loginUser(username.getText(),password.getText());
    	
    	if (user != null) {
    	    FXMLLoader loader = new FXMLLoader();
    	    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	    String fxmlDocPath = "src/Views/Dashboard.fxml";

    	    FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
    	    AnchorPane pane = (AnchorPane) loader.load(fxmlStream);
    	    
    	    Dashboard obj = loader.getController();
    	    obj.setController(controller);
    	    obj.initializeDashboard();
    	    
    	    Scene scene = new Scene(pane, 960, 590);
    	    currentStage.setScene(scene);
    	    currentStage.show();
    	}


    	else {
    		error.setText("Error: Username or password incorrect!");
    	}
    	
    }

    @FXML
    void handleSignup(ActionEvent event) throws IOException {

    	FXMLLoader loader = new FXMLLoader();
		Stage currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
		String fxmlDocPath = "src/Views/Signup.fxml";
		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
		AnchorPane pane = (AnchorPane) loader.load(fxmlStream);
		Signup obj= loader.getController();
		obj.setController(controller);
		Scene scene = new Scene(pane,710,468);
		currentStage.setScene(scene);
		currentStage.show();
    	
    }
    
    
    
    
    
    
//    
//    
//    
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        // code to be executed when the FXML file is loaded
//    	controller = new MainController();
//    	
//			
//    }
    
    
    

}
