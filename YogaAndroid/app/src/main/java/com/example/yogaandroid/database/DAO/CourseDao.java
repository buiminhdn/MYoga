package com.example.yogaandroid.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.yogaandroid.entities.enums.CourseAction;
import com.example.yogaandroid.entities.models.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    void insertCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Query("UPDATE courses SET `action` = :courseAction, isSynced = 0 WHERE id = :courseId")
    void deleteCourse(int courseId, CourseAction courseAction);

    @Query("SELECT * FROM courses WHERE id = :courseId AND `action` != :deleteAction")
    Course getCourseById(int courseId, CourseAction deleteAction);

    @Query("SELECT * FROM courses WHERE `action` != :deleteAction")
    List<Course> getAllCourses(CourseAction deleteAction);

    @Query("SELECT * FROM courses WHERE `action` != :deleteAction AND " +
            "(name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')")
    List<Course> searchCourses(String query, CourseAction deleteAction);

    @Query("SELECT * FROM courses WHERE `action` != :deleteAction AND dayOfWeek = :dayOfWeek AND " +
            "(name LIKE '%' || :searchTerm || '%' OR description LIKE '%' || :searchTerm || '%') AND (" +
            "(:flowYogaChecked = 1 AND type = 'FLOW_YOGA') OR " +
            "(:aerialYogaChecked = 1 AND type = 'AERIAL_YOGA') OR " +
            "(:familyYogaChecked = 1 AND type = 'FAMILY_YOGA') OR " +
            "(:flowYogaChecked = 0 AND :aerialYogaChecked = 0 AND :familyYogaChecked = 0))")
    List<Course> filterCourses(String dayOfWeek, boolean flowYogaChecked, boolean aerialYogaChecked, boolean familyYogaChecked, String searchTerm, CourseAction deleteAction);

    @Query("SELECT * FROM courses WHERE isSynced = 0") // Fetch unsynced data
    List<Course> getUnsyncedCourses();

//    @Query("UPDATE courses SET isSynced = 1 WHERE id = :courseId")
//    void markAsSynced(int courseId);

    @Query("UPDATE courses SET isSynced = :isSynced WHERE id = :id")
    void updateCourseSyncStatus(int id, boolean isSynced);

    @Query("DELETE FROM courses WHERE id = :id")
    void deleteCourseById(int id);
}
