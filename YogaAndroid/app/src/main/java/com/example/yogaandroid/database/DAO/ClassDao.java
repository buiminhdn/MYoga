package com.example.yogaandroid.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.yogaandroid.entities.enums.ClassAction;
import com.example.yogaandroid.entities.models.ClassSession;

import java.util.List;

@Dao
public interface ClassDao {
    @Insert
    void insertClass(ClassSession aClass);

    @Query("SELECT * FROM classes WHERE `action` != :deleteAction")
    List<ClassSession> getAllClasses(ClassAction deleteAction);

    // Filter by teacher name and comment
    @Query("SELECT * FROM classes WHERE (teacherName LIKE '%' || :searchTerm || '%' OR comment LIKE '%' || :searchTerm || '%') AND `action` != :deleteAction")
    List<ClassSession> getClassesFiltered(String searchTerm, ClassAction deleteAction);

    @Query("SELECT * FROM classes WHERE id = :classId AND `action` != :deleteAction")
    ClassSession getClassById(int classId, ClassAction deleteAction);

    @Query("SELECT * FROM classes WHERE courseId = :courseId AND `action` != :deleteAction")
    List<ClassSession> getClassesForCourse(int courseId, ClassAction deleteAction);

    @Query("UPDATE classes SET `action` = :classAction, isSynced = 0 WHERE id = :classId")
    void deleteClass(int classId, ClassAction classAction);

    @Update
    void updateClass(ClassSession aClass);

    // Delete all classes with courseId
    @Query("DELETE FROM classes WHERE courseId = :courseId")
    void deleteAllClassesForCourse(int courseId);

    @Query("SELECT * FROM classes WHERE isSynced = 0") // Fetch unsynced data
    List<ClassSession> getUnsyncedClasses();

    @Query("UPDATE classes SET isSynced = 1 WHERE id = :classId")
    void updateClassSyncStatus(int classId);

    @Query("DELETE FROM classes WHERE id = :classId")
    void deleteClassById(int classId);
}
