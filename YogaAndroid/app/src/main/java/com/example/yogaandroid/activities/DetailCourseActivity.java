package com.example.yogaandroid.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaandroid.R;
import com.example.yogaandroid.adapters.ClassAdapter;
import com.example.yogaandroid.entities.enums.ClassAction;
import com.example.yogaandroid.entities.models.ClassSession;
import com.example.yogaandroid.entities.models.Course;
import com.example.yogaandroid.viewModels.CourseDetailVM;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DetailCourseActivity extends AppCompatActivity {

    private TextView tvCourseName, tvCourseDescription, tvDayOfWeek, tvTime, tvDuration, tvType, tvCapacity, tvPrice;
    private RelativeLayout btnBack, btnDelete, btnAddClass;
    private int courseId;
    private CourseDetailVM courseDetailVM;
    private Button btnUpdate;
    private String dateOfWeek;

    private RecyclerView rvClasses;
    private ClassAdapter classAdapter;

    private ActivityResultLauncher<Intent> updateActivityLauncher;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUI();
        setupListeners();

        courseDetailVM = new ViewModelProvider(this).get(CourseDetailVM.class);

        // Get courseId from intent
        courseId = getIntent().getIntExtra("COURSE_ID", -1);

        if (courseId != -1) {
            // Fetch course details using courseId
            loadCourseDetails(courseId);

            courseDetailVM.loadClasses(courseId);

            // Observe classes LiveData
            courseDetailVM.getClasses().observe(this, new Observer<List<ClassSession>>() {
                @Override
                public void onChanged(List<ClassSession> classSessions) {
                    classAdapter.setClasses(classSessions); // Update adapter when data changes
                }
            });
        } else {
            Toast.makeText(this, "Invalid Course ID", Toast.LENGTH_SHORT).show();
        }

        updateActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadCourseDetails(courseId);
                    }
                });
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
        });
        btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog();
        });
        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdateCourseActivity.class);
            intent.putExtra("COURSE_ID", courseId); // Pass the courseId
            updateActivityLauncher.launch(intent);
        });
        btnAddClass.setOnClickListener(v -> showUpsertClassDialog(null));
    }

    private void loadCourseDetails(int courseId) {
        Course course = courseDetailVM.getCourseById(courseId);
        if (course != null) {
            tvCourseName.setText(course.getName());
            tvCourseDescription.setText(course.getDescription());
            tvDayOfWeek.setText("Day of Week: " + course.getDayOfWeek());
            tvTime.setText("Time: " + course.getTime());
            tvDuration.setText("Duration: " + course.getDuration() + " minutes");
            tvType.setText("Type: " + course.getType().getName());
            tvCapacity.setText("Capacity: " + course.getCapacity());
            tvPrice.setText("Price: $" + course.getPrice());

            dateOfWeek = course.getDayOfWeek();
        } else {
            Toast.makeText(this, "Course not found", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to show the upsert class dialog
    private void showUpsertClassDialog(ClassSession classSession) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_upsert_class);

        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        // Initialize dialog views
        TextView tvTitle = dialog.findViewById(R.id.tvTitleClass);
        EditText etTitle = dialog.findViewById(R.id.etTitleClass);
        EditText etDate = dialog.findViewById(R.id.etDateClass);
        EditText etComment = dialog.findViewById(R.id.etCommentClass);
        Button btnCancel = dialog.findViewById(R.id.btnCancelClass);
        Button btnSave = dialog.findViewById(R.id.btnSaveClass);

        // If classSession is not null, update the dialog
        if (classSession != null) {
            tvTitle.setText("UPDATE CLASS");
            btnSave.setText("UPDATE");
            etTitle.setText(classSession.getTeacherName());
            etDate.setText(classSession.getDate());
            etComment.setText(classSession.getComment());
        }

        // Handle date picker
        etDate.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    R.style.DatePickerDialogTheme,
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear, selectedMonth, selectedDay);

                        // Check if the selected date matches the required day of the week
                        String selectedDayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault()).format(selectedDate.getTime());

                        if (!selectedDayOfWeek.equalsIgnoreCase(dateOfWeek)) {
                            etDate.setError("Selected date must be a " + dateOfWeek);
                            Toast.makeText(this, "Please select a " + dateOfWeek + " class", Toast.LENGTH_SHORT).show();
                        } else {
                            etDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                            etDate.setError(null); // Clear any previous errors
                        }
                    }, year, month, day);

            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        });

        // Handle cancel button click
        btnCancel.setOnClickListener(view -> dialog.dismiss());

        // Handle save button click
        btnSave.setOnClickListener(view -> {
            // Get input values
            String teacherName = etTitle.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String comment = etComment.getText().toString().trim();

            if (teacherName.isEmpty()) {
                etTitle.setError("Teacher name is required");
                return;
            }

            if (date.isEmpty()) {
                etDate.setError("Date is required");
                return;
            }

            if (classSession != null) {
                // Update existing class session
                classSession.setTeacherName(teacherName);
                classSession.setDate(date);
                classSession.setComment(comment);
                classSession.setAction(ClassAction.UPDATE);
                classSession.setSynced(false);
                courseDetailVM.updateClassSession(classSession);
                courseDetailVM.loadClasses(courseId);
                Toast.makeText(this, "Class updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Add a new class session
                ClassSession newClassSession = new ClassSession(teacherName, date, comment, courseId, ClassAction.ADD, false);
                courseDetailVM.insertClassSession(newClassSession);
                courseDetailVM.loadClasses(courseId);
                Toast.makeText(this, "Class added successfully", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        dialog.show();
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Course")
                .setMessage("Are you sure you want to delete this course?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    courseDetailVM.deleteCourse(courseId);
                    Toast.makeText(this, "Course deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void initUI() {
        tvCourseName = findViewById(R.id.tvTitle);
        tvCourseDescription = findViewById(R.id.tvDescriptionDetail);
        tvDayOfWeek = findViewById(R.id.tvDayOfWeekDetail);
        tvTime = findViewById(R.id.tvTimeDetail);
        tvDuration = findViewById(R.id.tvDurationDetail);
        tvType = findViewById(R.id.tvTypeDetail);
        tvCapacity = findViewById(R.id.tvCapacityDetail);
        tvPrice = findViewById(R.id.tvPriceDetail);

        btnBack = findViewById(R.id.btnBackDetail);
        btnDelete = findViewById(R.id.btnDeleteDetail);
        btnUpdate = findViewById(R.id.btnUpdateDetail);
        btnAddClass = findViewById(R.id.btnAddClassDetail);

        // For List Class
        rvClasses = findViewById(R.id.rvClassesDetail);
        classAdapter = new ClassAdapter(null, new ClassAdapter.OnClassClickListener() {
            @Override
            public void onClassEdit(ClassSession classSession) {
                showUpsertClassDialog(classSession);
            }

            @Override
            public void onClassDelete(int classId) {
                showClassDeleteConfirmation(classId);
            }
        }, true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvClasses.setLayoutManager(linearLayoutManager);
        rvClasses.setAdapter(classAdapter);
    }

    private void showClassDeleteConfirmation(int classId) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Class")
                .setMessage("Are you sure you want to delete this class?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    courseDetailVM.deleteClassSession(classId);// Delete the class session
                    courseDetailVM.loadClasses(courseId);
                    Toast.makeText(this, "Class deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}