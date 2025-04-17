package com.example.yogaandroid.entities.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.yogaandroid.entities.enums.ClassAction;

@Entity(tableName = "classes")
public class ClassSession {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String teacherName;
    private String date;
    private String comment;
    private int courseId; // Foreign key reference to Course table
    private ClassAction action;
    private boolean isSynced;

    public ClassSession(String teacherName, String date, String comment, int courseId, ClassAction action, boolean isSynced) {
        this.teacherName = teacherName;
        this.date = date;
        this.comment = comment;
        this.courseId = courseId;
        this.action = action;
        this.isSynced = isSynced;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }

    public ClassAction getAction() {
        return action;
    }

    public void setAction(ClassAction action) {
        this.action = action;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
