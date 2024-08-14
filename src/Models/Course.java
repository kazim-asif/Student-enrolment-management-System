
package Models;

import java.util.ArrayList;
import java.util.List;

// Course class represents a course with its information
public class Course {
	
    private String courseId;
    private String courseName;
    private int capacity;
    private String year;
    private String deliveryMode;
    private String dayOfLecture;
    private String timeOfLecture;
    private double durationOfLecture;

    private List<User> enrolledStudents;
    private List<Course> courses;

    public Course(String courseId, String courseName, int capacity, String year, String deliveryMode,
                  String dayOfLecture, String timeOfLecture, double durationOfLecture2) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.capacity = capacity;
        this.year = year;
        this.deliveryMode = deliveryMode;
        this.dayOfLecture = dayOfLecture;
        this.timeOfLecture = timeOfLecture;
        this.durationOfLecture = durationOfLecture2;
        this.enrolledStudents = new ArrayList<>();
    }

    // Getters and setters

    public Course() {
		// TODO Auto-generated constructor stub
    	this.courses = new ArrayList<Course>();
	}

	public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getDayOfLecture() {
        return dayOfLecture;
    }

    public void setDayOfLecture(String dayOfLecture) {
        this.dayOfLecture = dayOfLecture;
    }

    public String getTimeOfLecture() {
        return timeOfLecture;
    }

    public void setTimeOfLecture(String timeOfLecture) {
        this.timeOfLecture = timeOfLecture;
    }

    public double getDurationOfLecture() {
        return durationOfLecture;
    }

    public void setDurationOfLecture(int durationOfLecture) {
        this.durationOfLecture = durationOfLecture;
    }

    public List<User> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void enrollStudent(User student) {
        enrolledStudents.add(student);
    }

    public void withdrawStudent(User student) {
        enrolledStudents.remove(student);
    }

    public void addCourse(Course course) {
    	this.courses.add(course);
    }

    public List<Course> getAllCourses() {
        return this.courses;
    }

    public Course getCourseById(String courseId) {
        for (Course course : this.courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }
}
