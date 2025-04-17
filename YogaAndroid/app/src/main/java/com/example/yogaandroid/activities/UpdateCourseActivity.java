package com.example.yogaandroid.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.yogaandroid.R;
import com.example.yogaandroid.entities.enums.CourseAction;
import com.example.yogaandroid.entities.enums.CourseType;
import com.example.yogaandroid.entities.models.Course;
import com.example.yogaandroid.viewModels.CourseDetailVM;

import java.util.Calendar;

public class UpdateCourseActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText etTitle, etDescription, etPrice, etDuration, etCapacity, etTime;
    private Spinner spinnerDayOfWeek;
    private RadioGroup rgCourseType;
    private Button btnSave;
    private int courseId;
    private CourseDetailVM courseVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUI();
        setupListeners();

        courseId = getIntent().getIntExtra("COURSE_ID", -1);
        courseVM = new ViewModelProvider(this).get(CourseDetailVM.class);

        if (courseId != -1) {
            loadCourseData();
        }

    }

    private void loadCourseData() {
        Course course = courseVM.getCourseById(courseId);
        if (course == null) {
            Toast.makeText(this, "Course not found", Toast.LENGTH_SHORT).show();
            return;
        }
        etTitle.setText(course.getName());
        etDescription.setText(course.getDescription());
        etPrice.setText(String.valueOf(course.getPrice()));
        etDuration.setText(String.valueOf(course.getDuration()));
        etCapacity.setText(String.valueOf(course.getCapacity()));
        etTime.setText(course.getTime());
        rgCourseType.check(course.getType().getId());
        spinnerDayOfWeek.setSelection(getSpinnerIndex(course.getDayOfWeek()));
    }

    // Helper method to get the index of a value in the Spinner
    private int getSpinnerIndex(String value) {
        for (int i = 0; i < spinnerDayOfWeek.getCount(); i++) {
            if (spinnerDayOfWeek.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0;
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
        });
        btnSave.setOnClickListener(v -> {
            updateCourse();
        });
        etTime.setOnClickListener(v -> {
            showTimePickerDialog();
        });
    }

    private void updateCourse() {
        if (courseId == -1) {
            Toast.makeText(this, "Invalid course ID", Toast.LENGTH_SHORT).show();
            return;
        }

        if (validateInput()) {
            Course course = collectCourseData();
            try {
                courseVM.updateCourse(course);
                Toast.makeText(this, "Course updated successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Error saving course", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Course collectCourseData() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        double price = Double.parseDouble(etPrice.getText().toString().trim());
        int duration = Integer.parseInt(etDuration.getText().toString().trim());
        int capacity = Integer.parseInt(etCapacity.getText().toString().trim());
        String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
        String time = etTime.getText().toString().trim();
        CourseType type = CourseType.fromId(rgCourseType.getCheckedRadioButtonId());

        return new Course(courseId, title, description, dayOfWeek, time, duration, capacity, price, type, CourseAction.UPDATE, false);
    }

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

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Use the custom style for the TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this, // Use the context
                R.style.CustomTimePickerStyle, // Apply custom style
                (view, selectedHour, selectedMinute) -> {
                    etTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                },
                hour, minute, true
        );

        timePickerDialog.show();
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBackUpdate);
        etTitle = findViewById(R.id.etTitleUpdate);
        etDescription = findViewById(R.id.etDescriptionUpdate);
        etPrice = findViewById(R.id.etPriceUpdate);
        etDuration = findViewById(R.id.etDurationUpdate);
        etCapacity = findViewById(R.id.etCapacityUpdate);
        etTime = findViewById(R.id.etTimeUpdate);
        spinnerDayOfWeek = findViewById(R.id.spinnerDayOfWeekUpdate);
        rgCourseType = findViewById(R.id.rgClassTypeUpdate);
        btnSave = findViewById(R.id.btnUpdateCourse);
    }
}