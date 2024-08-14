package GUIControllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.hpsf.Array;

import Controllers.MainController;
import Models.Course;
import Models.User;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Dashboard {
	
	private MainController controller;

    public void setController(MainController controller) {
        this.controller = controller;
    }
	
	public MainController getController() {
		return controller;
	}
	
	
	User loggedinUser=null;
	List<Course> courses = new ArrayList<>();
	List<Course> enrolledcourses = new ArrayList<>();
	
	
    @FXML
    private TextArea allcoursesarea;

    @FXML
    private TextArea allenrolledcoursesarea;

    @FXML
    private Button editbtn;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField password;

    @FXML
    private Button refresh1;

    @FXML
    private Button refresh2;

    @FXML
    private TextArea stdinfoarea;

    @FXML
    private TextField ecid;
    @FXML
    private TextField wcid;
    
    @FXML
    private TextField filename;
    
    @FXML
    void RefreshAllCourses(ActionEvent event) {
    	courses = controller.viewAllCourses();
    	refreshcourses();
    }

    @FXML
    void RefreshEnrolledCourses(ActionEvent event) {
        enrolledcourses = controller.viewEnrolledCoursesTimetable();
    	refreshEnrolledCourses();
    }
    
    @FXML
    void handleEdit(ActionEvent event) {
    	if(!fname.getText().isEmpty() && !lname.getText().isEmpty() && !password.getText().isEmpty()) {
    		if(controller.editUserProfile(fname.getText(), lname.getText(), password.getText())) {
    			System.out.println("Profile edited!.");
    			Alert alert = new Alert(AlertType.INFORMATION, "Successfully edited! It'll be written to file once you logged out");
                alert.showAndWait();
    		}
    		else {
    			Alert alert = new Alert(AlertType.ERROR, "Errorin updating info.");
                alert.showAndWait();
    		}
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR, "Please enter all fields.");
            alert.showAndWait();
    	}
    }


    public void refreshcourses() {
    	allcoursesarea.setText("");
    	for (Course c : courses) {
    		allcoursesarea.setText(allcoursesarea.getText()+"------------------------\n"
    				+ "Course ID: "+c.getCourseId()
    				+ "\nCourse Name: "+c.getCourseName()
    				+ "\nDelivery mode: "+c.getDeliveryMode()
    				+ "\nDay of lecture: +"+c.getDayOfLecture()
    				+ "\nTime of lecture: "+c.getTimeOfLecture()
    				+ "\nDuration of lecture (hour): "+c.getDurationOfLecture()
    				+ "\n");
		}
    }
    
    public void refreshEnrolledCourses() {
    	allenrolledcoursesarea.setText("");
    	for (Course course : enrolledcourses) {
    		allenrolledcoursesarea.setText(allenrolledcoursesarea.getText()+"------------------------\n"
    		+ "Course ID: "+course.getCourseId()
    		+ "\nCourse Name: " + course.getCourseName()
    		+"\nDay of Lecture: " + course.getDayOfLecture()
    		+"\nTime of Lecture: " + course.getTimeOfLecture()
    		+"\nDuration of Lecture: " + course.getDurationOfLecture() + " hours\n");
		}
    }
	
	
    @FXML
    void handleLogout(ActionEvent event) throws IOException {

    	controller.logout();
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
	
    
    public void initializeDashboard() {
        if (controller != null) {
            loggedinUser = controller.getLoggedInUser();
            courses = controller.viewAllCourses();
            enrolledcourses = controller.viewEnrolledCoursesTimetable();

            stdinfoarea.setText("\t\t\tStudent Number: " + loggedinUser.getStudentNumber() + "\t\tFirst Name: " + loggedinUser.getFirstName() + "\t\tLast Name: " + loggedinUser.getLastName());
        } else {
            System.out.println("Controller object is null.");
        }
    }
    
    
    @FXML
    void WithdrawCourse(ActionEvent event) {

    	if(!wcid.getText().isEmpty() && !wcid.getText().equals("")) {
    		if(controller.withdrawFromCourse(wcid.getText())) {
	    		Alert alert = new Alert(AlertType.INFORMATION, "Course is withdrawed!");
	            alert.showAndWait();
    		}
    		else {
    			Alert alert = new Alert(AlertType.INFORMATION, "Error: Can't withdraw!");
	            alert.showAndWait();
    		}
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR, "Please enter a Course ID of already enrolled course in field.");
            alert.showAndWait();
    	}
    	
    	
    }

    @FXML
    void enrollcourse(ActionEvent event) {

    	if(!ecid.getText().isEmpty() && !ecid.getText().equals("")) {
    		if(controller.enrollIntoCourse(ecid.getText())) {
	    		Alert alert = new Alert(AlertType.INFORMATION, "Course is enrolled!");
	            alert.showAndWait();
    		}
    		else {
    			Alert alert = new Alert(AlertType.INFORMATION, "Error in enrolling the course!");
	            alert.showAndWait();
    		}
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR, "Please enter Course ID other than already enrolled course");
            alert.showAndWait();
    	}
    }
    
    
    @FXML
    void exportEnrolledCourses( ActionEvent event ) {
    	if(!filename.getText().isBlank() && !filename.getText().equals("")) {
    		controller.exportEnrolledCoursesToCSV(filename.getText());
    		Alert alert = new Alert(AlertType.INFORMATION, "Enrolled courses are exported to file. Check your directory");
            alert.showAndWait();
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR, "Please enter a file name in field.");
            alert.showAndWait();
    	}
    }
    
    
}
