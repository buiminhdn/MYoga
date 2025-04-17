package com.example.yogaandroid.entities.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.yogaandroid.entities.enums.CourseAction;
import com.example.yogaandroid.entities.enums.CourseType;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;

    private String dayOfWeek;
    private String time;
    private int duration;
    private int capacity;
    private double price;
    private CourseType type;

    private CourseAction action;
    private boolean isSynced;

    public Course(int id, String name, String description, String dayOfWeek, String time, int duration, int capacity, double price, CourseType type, CourseAction action, boolean isSynced) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.duration = duration;
        this.capacity = capacity;
        this.price = price;
        this.type = type;
        this.action = action;
        this.isSynced = isSynced;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CourseAction getAction() {
        return action;
    }

    public void setAction(CourseAction action) {
        this.action = action;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }
}
