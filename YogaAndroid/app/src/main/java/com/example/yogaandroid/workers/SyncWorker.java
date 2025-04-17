package com.example.yogaandroid.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.yogaandroid.database.AppDatabase;
import com.example.yogaandroid.database.DAO.ClassDao;
import com.example.yogaandroid.database.DAO.CourseDao;
import com.example.yogaandroid.entities.models.ClassSession;
import com.example.yogaandroid.entities.models.Course;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SyncWorker extends Worker {
    private final CourseDao courseDao;
    private final ClassDao classSessionDao;
    private final FirebaseFirestore firestore;

    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        AppDatabase db = AppDatabase.getDatabase(context);
        courseDao = db.courseDao();
        classSessionDao = db.classDao();
        firestore = FirebaseFirestore.getInstance(); // Initialize Firestore
    }

    @NonNull
    @Override
    public Result doWork() {
        // Sync Courses
        syncCourses();

        // Sync ClassSessions
        syncClassSessions();

        return Result.success();
    }

    // Sync Courses
    private void syncCourses() {
        List<Course> unsyncedCourses = courseDao.getUnsyncedCourses();
        for (Course course : unsyncedCourses) {
            switch (course.getAction()) {
                case ADD:
                    syncUpsertCourse(course);
                    break;
                case UPDATE:
                    syncUpsertCourse(course);
                    break;
                case DELETE:
                    syncDeleteCourse(course);
                    break;
            }
        }
    }

    private void syncUpsertCourse(Course course) {
        firestore.collection("courses").document(String.valueOf(course.getId()))
                .set(course)
                .addOnSuccessListener(aVoid -> {
                    // Mark as synced after successful upsert
                    courseDao.updateCourseSyncStatus(course.getId(), true);
                })
                .addOnFailureListener(e -> {
                    Log.e("SyncWorker", "Error syncing new course: " + course.getId(), e);
                });
    }

    private void syncDeleteCourse(Course course) {
        firestore.collection("courses").document(String.valueOf(course.getId()))
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Delete course locally after successful deletion in Firestore
                    courseDao.deleteCourseById(course.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("SyncWorker", "Error deleting course: " + course.getId(), e);
                });
    }

    private void syncClassSessions() {
        List<ClassSession> unsyncedClasses = classSessionDao.getUnsyncedClasses();
        for (ClassSession classSession : unsyncedClasses) {
            switch (classSession.getAction()) {
                case ADD:
                    syncUpsertClass(classSession);
                    break;
                case UPDATE:
                    syncUpsertClass(classSession);
                    break;
                case DELETE:
                    syncDeleteClass(classSession);
                    break;
            }
        }
    }

    private void syncUpsertClass(ClassSession classSession) {
        firestore.collection("classes").document(String.valueOf(classSession.getId()))
                .set(classSession)
                .addOnSuccessListener(aVoid -> {
                    // Mark as synced after successful upsert
                    classSessionDao.updateClassSyncStatus(classSession.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("SyncWorker", "Error syncing new class session: " + classSession.getId(), e);
                });
    }

    private void syncDeleteClass(ClassSession classSession) {
        firestore.collection("classes").document(String.valueOf(classSession.getId()))
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Delete class locally after successful deletion in Firestore
                    classSessionDao.deleteClassById(classSession.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("SyncWorker", "Error deleting class session: " + classSession.getId(), e);
                });
    }
}
