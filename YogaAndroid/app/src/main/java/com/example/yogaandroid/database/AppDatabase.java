package com.example.yogaandroid.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.yogaandroid.database.DAO.ClassDao;
import com.example.yogaandroid.database.DAO.CourseDao;
import com.example.yogaandroid.entities.models.ClassSession;
import com.example.yogaandroid.entities.models.Course;

@Database(entities = {Course.class, ClassSession.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "yoga_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract CourseDao courseDao();
    public abstract ClassDao classDao();
}
