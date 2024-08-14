package Controllers;

import Models.Course;
import Models.User;
import jxl.common.log.LoggerName;

import java.util.List;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System.Logger;

// Controller class handles user interactions and updates the model and view
public class MainController {
    private User userModel;
    private Course courseModel;

    private User loggedInUser = null;

    
    public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public MainController() {
        this.userModel = new User();
        this.courseModel = new Course();
        
//        String filePath = "C:\\Users\\Taha Asif\\Desktop\\Waqas\\Student_Enrolment_Management_System\\src\\course.csv";
        String fileName = "course.csv";
        importCoursesFromCSV( fileName );
        
        fileName = "user_data.csv"; // Provide the file name you want to read from
        readUserDataFromCSV(fileName);
//        
//        System.out.println("Courses are "+courseModel.getAllCourses().size());
//        System.out.println("USers are "+userModel.getUsers().size());
//        System.out.println("Users courses are "+userModel.getUsers().get(0).getEnrolledCourses().size());
    }

    public void createUser(String username, String password, String firstName, String lastName) {
    	int min = 50;  
    	int max = 500;  
    	String studentNumber = ( (int)(Math.random()*(max-min+1)+min) )+""; 
        User user = new User(username, password, firstName, lastName, studentNumber);
        userModel.addUser(user);
    }

    public User loginUser(String username, String password) {
        User user = userModel.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            loggedInUser = user;
            return loggedInUser;
        }
        return null;
    }
    
    public void logout() {
        loggedInUser = null;
        String fileName = "user_data.csv"; // Provide the file name you want to read from
        writeUserDataToCSV(fileName);
        
        fileName="course.csv";
        writeCourseDataToCSV(fileName);
        
        System.out.println("Logged out successfully.");
    }

    public boolean editUserProfile(String firstName, String lastName, String password) {
        if (loggedInUser != null) {
            loggedInUser = userModel.getUserByUsername(loggedInUser.getUsername());
            loggedInUser.setFirstName(firstName);
            loggedInUser.setLastName(lastName);
            loggedInUser.setPassword(password);
            return true;
        }
        return false;
    }

    
    
    
    public List<Course> viewAllCourses() {
        return courseModel.getAllCourses();
    }

    public Course searchCourse(String courseId) {
        return courseModel.getCourseById(courseId);
    }

    public boolean enrollIntoCourse(String courseId) {
        Course course = courseModel.getCourseById(courseId);
        if (course != null) {
            boolean success = course.getEnrolledStudents().size() < course.getCapacity() &&
                    !course.getEnrolledStudents().contains(loggedInUser);
            if (success) {
                course.enrollStudent(loggedInUser);
                loggedInUser.getEnrolledCourses().add(course);
            }
            displayEnrollmentStatus(success);
            return success;
        }
        return false;
    }

    public boolean withdrawFromCourse(String courseId) {
        Course course = courseModel.getCourseById(courseId);
        
        if (course != null) {
            boolean success = course.getEnrolledStudents().contains(loggedInUser);
            if (success) {
                course.withdrawStudent(loggedInUser);
                course.setCapacity((course.getCapacity()+1));
                loggedInUser.getEnrolledCourses().remove(course);
               
//              System.out.println(course.getCapacity()+"   and  "+course.getEnrolledStudents().size() +" and "+course.getEnrolledStudents().contains(loggedInUser));
            }
            displayWithdrawalStatus(success);
            return success;
        }
        return false;
    }

    public void displayEnrollmentStatus(boolean success) {
        if (success) {
            System.out.println("Enrollment successful.");
        } else {
            System.out.println("Enrollment failed. Course is full or you are already enrolled.");
        }
    }

    public void displayWithdrawalStatus(boolean success) {
        if (success) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal failed. You are not enrolled in the course.");
        }
    }


    public List<Course> viewEnrolledCoursesTimetable() {
        return loggedInUser.getEnrolledCourses();
    }

    

    public void importCoursesFromCSV(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine(); // Skip the header line

            int row=1;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                String courseId = row+"";
                String courseName = values[0];
                String cap = values[1];
                int capacity = 0;
                if(cap.contains("N/A")) {
                	capacity=Integer.MAX_VALUE;
                }
                else {
                	capacity = Integer.parseInt(values[1]);
                }
                 
                String year = values[2];
                String deliveryMode = values[3];
                String dayOfLecture = values[4];
                String timeOfLecture = values[5];
                double durationOfLecture = Double.parseDouble(values[6]);

                Course course = new Course(courseId, courseName, capacity, year, deliveryMode, dayOfLecture, timeOfLecture, durationOfLecture);
                courseModel.addCourse(course);
                
                row++;
                
            }

            System.out.println("Courses imported successfully from CSV.");
        } catch (Exception e) {
            System.out.println("Error importing courses from CSV: " + e.getMessage());
        }
    }

    public void writeCourseDataToCSV(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write the CSV header
            writer.append("Course Name, Capacity, Year, Delivery Mode, Day of Lecture, Time of Lecture, Duration of Lecture\n");

            // Write course data
            for (Course course : courseModel.getAllCourses()) {
                writer.append(course.getCourseName()).append(",");
                if( course.getCapacity()>10000 ) {
                	writer.append("N/A").append(",");
                }
                else {
                	writer.append(String.valueOf(course.getCapacity())).append(",");
                }
                
                writer.append(course.getYear()).append(",");
                writer.append(course.getDeliveryMode()).append(",");
                writer.append(course.getDayOfLecture()).append(",");
                writer.append(course.getTimeOfLecture()).append(",");
                writer.append(String.valueOf(course.getDurationOfLecture())).append("\n");
            }

            writer.flush();
            System.out.println("Course data exported to CSV successfully!");
        } catch (IOException e) {
            System.out.println("Error exporting course data to CSV: " + e.getMessage());
        }
    }
    
    
    public void exportEnrolledCoursesToCSV(String fileName) {
    	
    	if(loggedInUser!=null) {
            try (FileWriter writer = new FileWriter(fileName+".csv")) {
                // Write the CSV header
                writer.append("Course ID, Course Name, Year, Delivery Mode, Day of Lecture, Time of Lecture, Duration of Lecture\n");

                // Write the enrolled courses data
                for (Course course : loggedInUser.getEnrolledCourses() ) {
                    writer.append(course.getCourseId()).append(",");
                    writer.append(course.getCourseName()).append(",");
                    writer.append(course.getYear()).append(",");
                    writer.append(course.getDeliveryMode()).append(",");
                    writer.append(course.getDayOfLecture()).append(",");
                    writer.append(course.getTimeOfLecture()).append(",");
                    writer.append(String.valueOf(course.getDurationOfLecture())).append("\n");
                }

                writer.flush();
                System.out.println("Enrolled courses exported to CSV successfully!");
            } catch (IOException e) {
                System.out.println("Error exporting enrolled courses to CSV: " + e.getMessage());
            }
    	}
    }
  
    public void writeUserDataToCSV(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write the CSV header
            writer.append("Username, First Name, Last Name, Student Number, Password ,Enrolled Course IDs\n");

            // Write user data
            for (User user : userModel.getUsers()) {
                writer.append(user.getUsername()).append(",");
                writer.append(user.getFirstName()).append(",");
                writer.append(user.getLastName()).append(",");
                writer.append(user.getStudentNumber()).append(",");
                writer.append(user.getPassword()).append(",");

                // Write enrolled course IDs
                List<Course> enrolledCourses = user.getEnrolledCourses();
                StringBuilder courseIDs = new StringBuilder();
                for (Course course : enrolledCourses) {
                    courseIDs.append(course.getCourseId()).append(",");
                }
                writer.append(courseIDs.toString()).append("\n");
            }

            writer.flush();
            System.out.println("User data exported to CSV successfully!");
        } catch (IOException e) {
            System.out.println("Error exporting user data to CSV: " + e.getMessage());
        }
    }
    
    public void readUserDataFromCSV(String fileName) {
        List<User> userList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }

                String[] data = line.split(",");
                if (data.length >= 5) {
                    String username = data[0];
                    String firstName = data[1];
                    String lastName = data[2];
                    String studentNumber = data[3];
                    String password = data[4];

                    User user = new User(username, password, firstName, lastName, studentNumber);
                    List<Course> enrolledCourses = new ArrayList<>();

                    // Match course IDs
                    for (int i = 5; i < data.length; i++) {
                        String courseId = data[i];
                        Course course = courseModel.getCourseById(courseId);
                        if (course != null) {
                            enrolledCourses.add(course);
                            course.getEnrolledStudents().add(user);
                        }
                    }

                    user.setEnrolledCourses(enrolledCourses);
                    userList.add(user);
                }
            }

            System.out.println("User data imported from CSV successfully!");
        } catch (IOException e) {
            System.out.println("Error importing user data from CSV: " + e.getMessage());
        }

        userModel.setUsers(userList);
    }
    
    
}
