package Models;

import java.util.ArrayList;
import java.util.List;

//User class represents a user's profile
public class User {
 private String username;
 private String password;
 private String firstName;
 private String lastName;
 private String studentNumber;
 
 private List<User> users ;
 private List<Course> enrolledCourses;

 public User(String username, String password, String firstName, String lastName, String studentNumber) {
     this.username = username;
     this.password = password;
     this.firstName = firstName;
     this.lastName = lastName;
     this.studentNumber = studentNumber;
     this.enrolledCourses = new ArrayList<Course>();
 }

 // Getters and setters

 public User() {
	// TODO Auto-generated constructor stub
	 this.users = new ArrayList<>();
 }

public List<User> getUsers() {
	return users;
}

public void setUsers(List<User> users) {
	this.users = users;
}

public List<Course> getEnrolledCourses() {
	return enrolledCourses;
}

public void setEnrolledCourses(List<Course> enrolledCourses) {
	this.enrolledCourses = enrolledCourses;
}

public String getUsername() {
     return username;
 }

 public void setUsername(String username) {
     this.username = username;
 }

 public String getPassword() {
     return password;
 }

 public void setPassword(String password) {
     this.password = password;
 }

 public String getFirstName() {
     return firstName;
 }

 public void setFirstName(String firstName) {
     this.firstName = firstName;
 }

 public String getLastName() {
     return lastName;
 }

 public void setLastName(String lastName) {
     this.lastName = lastName;
 }

 public String getStudentNumber() {
     return studentNumber;
 }

 public void setStudentNumber(String studentNumber) {
     this.studentNumber = studentNumber;
 }
 
 public void addUser(User user) {
     users.add(user);
 }

 public User getUserByUsername(String username) {
     for (User user : users) {
         if (user.getUsername().equals(username)) {
             return user;
         }
     }
     return null;
 }
 
 
}




