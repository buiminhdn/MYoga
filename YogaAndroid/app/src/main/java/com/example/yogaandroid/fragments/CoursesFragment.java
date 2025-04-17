package com.example.yogaandroid.fragments;

import static com.example.yogaandroid.utils.ConnectUtils.isConnected;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.yogaandroid.R;
import com.example.yogaandroid.activities.DetailCourseActivity;
import com.example.yogaandroid.adapters.CourseAdapter;
import com.example.yogaandroid.entities.models.Course;
import com.example.yogaandroid.viewModels.CoursesVM;
import com.example.yogaandroid.workers.SyncWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CoursesFragment extends Fragment {

    private RecyclerView rvCourses;
    private CourseAdapter courseAdapter;
    private CoursesVM coursesVM;
    private TextView numCourses;
    private ViewFlipper viewFlipper;
    private Button btnSync;

    public CoursesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        initUI(view);

        schedulePeriodicSyncWork(requireContext());

        btnSync.setOnClickListener(v ->
        {
            Toast.makeText(requireContext(), "Sync data", Toast.LENGTH_SHORT).show();
            triggerOneTimeSyncWork(requireContext());
        });

        if (isConnected(requireContext())) {
            ActionViewFlipper();
        } else {
            Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_LONG).show();
        }

        // Initialize the ViewModel
        coursesVM = new ViewModelProvider(requireActivity()).get(CoursesVM.class);

        // Observe LiveData
        coursesVM.getCourses().observe(getViewLifecycleOwner(), courses -> {
            courseAdapter.setCourses(courses);
            numCourses.setText(courses.size() + " Courses Available");
        });

        return view;
    }

    private void schedulePeriodicSyncWork(Context context) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicSyncWorkRequest = new PeriodicWorkRequest.Builder(SyncWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "PeriodicSyncWork",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicSyncWorkRequest
        );
    }

    // Helper method to trigger one-time sync
    private void triggerOneTimeSyncWork(Context context) {
        if (!isConnected(context)) {
            Toast.makeText(context, "No internet connection. Unable to sync.", Toast.LENGTH_SHORT).show();
            return;
        }

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest oneTimeSyncWorkRequest = new OneTimeWorkRequest.Builder(SyncWorker.class)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(context).enqueue(oneTimeSyncWorkRequest);
    }

    private void initUI(View view) {
        btnSync = view.findViewById(R.id.btnSync);
        numCourses = view.findViewById(R.id.numOfCourses);
        viewFlipper = view.findViewById(R.id.viewflipper);
        rvCourses = view.findViewById(R.id.rvCourses);
        courseAdapter = new CourseAdapter(null, courseId -> {
            // Handle course click event
            Intent intent = new Intent(requireContext(), DetailCourseActivity.class);
            intent.putExtra("COURSE_ID", courseId); // Pass the courseId
            startActivity(intent);
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rvCourses.setLayoutManager(linearLayoutManager);
        rvCourses.setAdapter(courseAdapter);
    }

    private void ActionViewFlipper() {
        // Create a list of image URLs for the advertisements
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://t4.ftcdn.net/jpg/07/11/73/49/360_F_711734922_3XfZ5aDzqE8Pb8G1FAFYylb46BqXKOXQ.jpg");
        mangquangcao.add("https://bizweb.dktcdn.net/100/514/950/products/cover-khi-nao-ban-khong-can-tap-yoga.jpg?v=1717648220867");
        mangquangcao.add("https://img.freepik.com/free-vector/flat-background-international-yoga-day-celebration_23-2150388305.jpg");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(requireContext());
            Glide.with(requireContext()).load(mangquangcao.get(i)).into(imageView);
            Log.d("CoursesFragment", "Adding image to flipper: " + mangquangcao.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    @Override
    public void onResume() {
        super.onResume();
        coursesVM.reloadCourses();
    }
}