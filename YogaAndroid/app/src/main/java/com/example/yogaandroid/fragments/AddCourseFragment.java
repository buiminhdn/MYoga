package com.example.yogaandroid.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yogaandroid.R;
import com.example.yogaandroid.entities.enums.CourseAction;
import com.example.yogaandroid.entities.enums.CourseType;
import com.example.yogaandroid.entities.models.Course;
import com.example.yogaandroid.viewModels.CourseDetailVM;

import java.util.Calendar;

public class AddCourseFragment extends Fragment {

    private EditText etTitle, etDescription, etPrice, etDuration, etCapacity, etTime;
    private Spinner spinnerDayOfWeek;
    private RadioGroup rgCourseType;
    private Button btnSave;
    private CourseDetailVM courseVM;

    public AddCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_course, container, false);

        // Initialize UI components
        initUI(view);

        // Set up listeners
        setupListeners();

        // Initialize ViewModel
        courseVM = new ViewModelProvider(this).get(CourseDetailVM.class);

        return view;
    }

    private void setupListeners() {
        // Set up click listener for the time EditText
        etTime.setOnClickListener(v -> {
            showTimePickerDialog();
        });

        // Set up click listener for the save button
        btnSave.setOnClickListener(v -> {
            if (validateInput()) {
                Course course = collectCourseData();
                try {
                    courseVM.insertCourse(course);
                    Toast.makeText(requireContext(), "Course created successfully", Toast.LENGTH_SHORT).show();
                    resetFrields();
                } catch (Exception e) {
                    Toast.makeText(requireContext(), "Error saving course", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Helper method to validate input
    private boolean validateInput() {
        if (etTitle.getText().toString().trim().isEmpty()) {
            etTitle.setError("Title is required");
            return false;
        }
        if (etDescription.getText().toString().trim().isEmpty()) {
            etDescription.setError("Description is required");
            return false;
        }
        if (etPrice.getText().toString().trim().isEmpty()) {
            etPrice.setError("Price is required");
            return false;
        }
        if (etTime.getText().toString().trim().isEmpty()) {
            etTime.setError("Time is required");
            return false;
        }
        if (etDuration.getText().toString().trim().isEmpty()) {
            etDuration.setError("Duration is required");
            return false;
        }
        if (etCapacity.getText().toString().trim().isEmpty()) {
            etCapacity.setError("Capacity is required");
            return false;
        }

        return true;
    }

    // Helper method to collect course data from EditText fields
    private Course collectCourseData() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        double price = Double.parseDouble(etPrice.getText().toString().trim());
        int duration = Integer.parseInt(etDuration.getText().toString().trim());
        int capacity = Integer.parseInt(etCapacity.getText().toString().trim());
        String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
        String time = etTime.getText().toString().trim();
        CourseType type = CourseType.fromId(rgCourseType.getCheckedRadioButtonId());

        return new Course(0, title, description, dayOfWeek, time, duration, capacity, price, type, CourseAction.ADD, false);
    }

    // Helper method to show time picker dialog
    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Use the custom style for the TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(), // Use the context
                R.style.CustomTimePickerStyle, // Apply custom style
                (view, selectedHour, selectedMinute) -> {
                    etTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                },
                hour, minute, true
        );

        timePickerDialog.show();
    }

    private void initUI(View view) {
        etTitle = view.findViewById(R.id.etTitleCourse);
        etDescription = view.findViewById(R.id.etDescriptionCourse);
        etPrice = view.findViewById(R.id.etPriceCourse);
        etDuration = view.findViewById(R.id.etDurationCourse);
        etCapacity = view.findViewById(R.id.etCapacityCourse);
        etTime = view.findViewById(R.id.etTimeCourse);
        spinnerDayOfWeek = view.findViewById(R.id.spinnerDayOfWeekCourse);
        rgCourseType = view.findViewById(R.id.rgClassTypeCourse);
        btnSave = view.findViewById(R.id.btnSaveCourse);
    }

    // Helper method to reset fields
    private void resetFrields() {
        etTitle.setText("");
        etDescription.setText("");
        etPrice.setText("");
        etDuration.setText("");
        etCapacity.setText("");
        etTime.setText("");
        spinnerDayOfWeek.setSelection(0);
    }
}