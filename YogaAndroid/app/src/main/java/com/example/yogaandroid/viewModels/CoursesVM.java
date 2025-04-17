package com.example.yogaandroid.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.yogaandroid.database.AppDatabase;
import com.example.yogaandroid.database.DAO.CourseDao;
import com.example.yogaandroid.entities.enums.CourseAction;
import com.example.yogaandroid.entities.models.Course;

import java.util.List;

public class CoursesVM extends AndroidViewModel {
    private final MutableLiveData<List<Course>> courses;
    private final CourseDao courseDao;

    public CoursesVM(Application application) {
        super(application);
        courseDao = AppDatabase.getDatabase(application).courseDao();
        courses = new MutableLiveData<>();
        loadCourses(); // Preload the courses data on ViewModel creation
    }

    // Load all courses into LiveData
    private void loadCourses() {
        List<Course> courseList = courseDao.getAllCourses(CourseAction.DELETE); // Fetch from the database
        courses.setValue(courseList);
    }

    // Methods for returning LiveData
    public LiveData<List<Course>> getCourses() {
        return courses;
    }

    public void reloadCourses() {
        loadCourses(); // Reload data
    }

    public Course getCourseById(int courseId) {
        return courseDao.getCourseById(courseId, CourseAction.DELETE);
    }

    public void filterCourses(String dayOfWeek, boolean flowYogaChecked, boolean aerialYogaChecked, boolean familyYogaChecked, String searchTerm) {
        courses.setValue(courseDao.filterCourses(dayOfWeek, flowYogaChecked, aerialYogaChecked, familyYogaChecked, searchTerm, CourseAction.DELETE));
    }



}
