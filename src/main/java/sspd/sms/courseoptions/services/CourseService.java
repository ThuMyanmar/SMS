package sspd.sms.courseoptions.services;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import sspd.sms.courseoptions.db.Courseimpl;
import sspd.sms.courseoptions.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {



    private  Courseimpl cdb ;

    @Autowired
    public void setCdb(Courseimpl cdb) {
        this.cdb = cdb;
    }

    private List<Course> courseList = new ArrayList<>();



    public List<Course> getAllCourses() {
        courseList.clear();
        courseList.addAll(cdb.getAllTask());
        return courseList;
    }

    public Course getCourseByName(String courseName) {

        if (courseName == null) {
            return null;
        }

        return getAllCourses().stream()
                .filter(course -> courseName.equalsIgnoreCase(course.getCourse_name())) // Case-insensitive match
                .findFirst()
                .orElse(null);
    }




    public int insertCourse(Course course) {
        return handleCourseChange(() -> cdb.insertTask(course), "Course Data Insert Successfully!!!");

    }

    public void updateCourse(Course course) {
        handleCourseChange(() -> cdb.updateTask(course), "Course Data Updated Successfully!!!");
    }

    public void deleteCourse(Course course) {
        handleCourseChange(() -> cdb.deleteTask(course), "Course Data Deleted Successfully!!!");
    }

    public void importCourse(List<Course> list) {
        handleCourseChange(() -> cdb.bactchTask(list), "Courses Imported Successfully!!!");
    }

    private int handleCourseChange(Runnable action, String successMessage) {
        int result = 0;
        int oldSize = getAllCourses().size();
        action.run();
        int newSize = getAllCourses().size();
        if (oldSize != newSize) {
            result = 1;
            showInformationDialog("Operation Successful", "Course", successMessage);
        }
        return result;
    }

    private void showInformationDialog(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    public int getCoursesCount() {
        return courseList.size();
    }

    public int findCourseByName(String name) {
        return getAllCourses().stream()
                .filter(course -> course.getCourse_name().equals(name))
                .map(Course::getCourse_id)
                .findFirst()
                .orElse(-1);
    }

    public List<String> getCourseNames() {
        return getAllCourses().stream()
                .map(Course::getCourse_name)
                .sorted()
                .collect(Collectors.toList());
    }
}