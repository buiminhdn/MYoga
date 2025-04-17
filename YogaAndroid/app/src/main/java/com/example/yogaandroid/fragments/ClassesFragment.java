package com.example.yogaandroid.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yogaandroid.R;
import com.example.yogaandroid.adapters.ClassAdapter;
import com.example.yogaandroid.entities.models.ClassSession;
import com.example.yogaandroid.viewModels.ClassesVM;

public class ClassesFragment extends Fragment {

    private RecyclerView rvClasses;
    private ClassAdapter classAdapter;
    private ClassesVM classesVM;
    private TextView numClasses;
    private EditText searchInput;
    private LinearLayout dayContainer;
    private String[] days = {"All", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private TextView selectedDayView;
    private String selectedDay = "All";

    public ClassesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_classes, container, false);

        initUI(view);

        createDayButtons();

        // Initialize the ViewModel
        classesVM = new ViewModelProvider(requireActivity()).get(ClassesVM.class);

        // Observe LiveData
        classesVM.getClasses().observe(getViewLifecycleOwner(), classes -> {
            classAdapter.setClasses(classes); // Update the adapter when data changes
            numClasses.setText(classes.size() + " Classes Available");
        });

        // Handle search input on change text
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                // After the text has been changed, filter the classes by teacher's name.
                String searchTerm = editable.toString().trim();
                classesVM.getClassesFiltered(selectedDay, searchTerm);
            }
        });


        return view;
    }

    private void initUI(View view) {
        numClasses = view.findViewById(R.id.numOfClasses);
        searchInput = view.findViewById(R.id.searchInputClass);
        rvClasses = view.findViewById(R.id.rvClasses);
        dayContainer = view.findViewById(R.id.day_container);

        classAdapter = new ClassAdapter(null, new ClassAdapter.OnClassClickListener() {
            @Override
            public void onClassEdit(ClassSession classSession) {}
            @Override
            public void onClassDelete(int classId) {}
        }, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rvClasses.setLayoutManager(linearLayoutManager);
        rvClasses.setAdapter(classAdapter);
    }

    private void createDayButtons() {
        for (String day : days) {
            TextView dayView = new TextView(requireContext());
            dayView.setText(day);
            dayView.setPadding(40, 15, 40, 15);
            dayView.setBackgroundResource(R.drawable.day_button_background);  // Set default background
            dayView.setTextColor(Color.parseColor("#333333"));  // Default text color
            dayView.setTextSize(16);
            dayView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDay((TextView) v);
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 20, 0);
            dayContainer.addView(dayView, params);

            if (day.equals("All")) {
                selectedDayView = dayView;
                selectedDayView.setBackgroundResource(R.drawable.selected_day_button_background);
                selectedDayView.setTextColor(Color.WHITE);
            }
        }
    }

    private void selectDay(TextView selectedView) {
        if (selectedDayView != null) {
            // Reset previously selected day style
            selectedDayView.setBackgroundResource(R.drawable.day_button_background);
            selectedDayView.setTextColor(Color.parseColor("#333333"));
        }

        // Set new selected day style
        selectedDayView = selectedView;
        selectedDayView.setBackgroundResource(R.drawable.selected_day_button_background);
        selectedDayView.setTextColor(Color.WHITE);

        // Get the selected day as a string
        selectedDay = selectedView.getText().toString();

        String searchTerm = searchInput.getText().toString();
        classesVM.getClassesFiltered(selectedDay, searchTerm);
    }

    @Override
    public void onResume() {
        super.onResume();
        classesVM.reloadClasses();
    }
}