package com.example.yogaandroid.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.yogaandroid.database.AppDatabase;
import com.example.yogaandroid.database.DAO.ClassDao;
import com.example.yogaandroid.database.DAO.CourseDao;
import com.example.yogaandroid.entities.enums.ClassAction;
import com.example.yogaandroid.entities.enums.CourseAction;
import com.example.yogaandroid.entities.models.ClassSession;
import com.example.yogaandroid.entities.models.Course;

import java.util.List;

public class CourseDetailVM extends AndroidViewModel {
    private final CourseDao courseDao;
    private final ClassDao classDao;
    private final MutableLiveData<List<ClassSession>> classes;

    public CourseDetailVM(Application application) {
        super(application);
        courseDao = AppDatabase.getDatabase(application).courseDao();
        classDao = AppDatabase.getDatabase(application).classDao();
        classes = new MutableLiveData<>();
    }

    // Insert course into database using DAO
    public void insertCourse(Course course) {
        courseDao.insertCourse(course);
    }

    public void updateCourse(Course course) {
        courseDao.updateCourse(course);
    }

    public void deleteCourse(int courseId) {
        courseDao.deleteCourse(courseId, CourseAction.DELETE);
        List<ClassSession> classesToDelete = classDao.getClassesForCourse(courseId, ClassAction.DELETE);
        for (ClassSession classSession : classesToDelete) {
            classDao.deleteClass(classSession.getId(), ClassAction.DELETE);
        }
    }

    public Course getCourseById(int courseId) {
        return courseDao.getCourseById(courseId, CourseAction.DELETE);
    }

    // For Classes
    public LiveData<List<ClassSession>> getClasses() {
        return classes;
    }

    public void loadClasses(int courseId) {
        classes.setValue(classDao.getClassesForCourse(courseId, ClassAction.DELETE));
    }

    public void insertClassSession(ClassSession classSession) {
        classDao.insertClass(classSession);
    }

    public void updateClassSession(ClassSession classSession) {
        classDao.updateClass(classSession);
    }

    public void deleteClassSession(int classSessionId) {
        classDao.deleteClass(classSessionId, ClassAction.DELETE);
    }


}
