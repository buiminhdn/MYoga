package com.example.yogaandroid.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.yogaandroid.database.AppDatabase;
import com.example.yogaandroid.database.DAO.ClassDao;
import com.example.yogaandroid.entities.enums.ClassAction;
import com.example.yogaandroid.entities.models.ClassSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ClassesVM extends AndroidViewModel {
    private final MutableLiveData<List<ClassSession>> classes; // Internal MutableLiveData
    private final ClassDao classDao;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public ClassesVM(@NonNull Application application) {
        super(application);
        classDao = AppDatabase.getDatabase(application).classDao();
        classes = new MutableLiveData<>();
        loadClasses(); // Preload the classes data on ViewModel creation
    }

    public void reloadClasses() {
        loadClasses(); // Reload data
    }

    private void loadClasses() {
        List<ClassSession> classList = classDao.getAllClasses(ClassAction.DELETE); // Fetch from the database
        classes.setValue(classList);
    }

    public LiveData<List<ClassSession>> getClasses() {
        return classes;
    }

    public ClassSession getClassById(int classId) {
        return classDao.getClassById(classId, ClassAction.DELETE);
    }

    // Filter Classes by teacher name
    public void filterClasses(String searchTerm) {
        List<ClassSession> filteredClasses = classDao.getClassesFiltered(searchTerm, ClassAction.DELETE);
        classes.setValue(filteredClasses);
    }

    // Filter by date of week based on current classes not from DAO
    public void getClassesFiltered(String selectedDay, String searchTerm) {
        filterClasses(searchTerm);

        // Retrieve the current list of classes from LiveData
        List<ClassSession> currentClasses = classes.getValue();
        if (classes == null) return;

        List<ClassSession> filteredClasses = new ArrayList<>();
        for (ClassSession classSession : currentClasses) {
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateFormat.parse(classSession.getDate()));

                // Get the day of the week as a string (e.g., "Monday")
                String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

                // Check if the day matches the selected day or if "All" is selected
                if (dayOfWeek.equalsIgnoreCase(selectedDay) || selectedDay.equalsIgnoreCase("All")) {
                    filteredClasses.add(classSession);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        classes.setValue(filteredClasses); // Update LiveData with the filtered list
    }

}
