package com.example.yogaandroid.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaandroid.R;
import com.example.yogaandroid.entities.models.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> mListCourse;
    private final OnCourseClickListener onCourseClickListener;

    public interface OnCourseClickListener {
        void onCourseClick(int courseId);
    }

    public CourseAdapter(List<Course> list, OnCourseClickListener listener) {
        this.mListCourse = list;
        this.onCourseClickListener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCourses(List<Course> courses) {
        this.mListCourse = courses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = mListCourse.get(position);
        if (course == null) {
            return;
        }

        holder.name.setText(course.getName());
        holder.description.setText(course.getDescription());
        holder.dayOfWeek.setText(course.getDayOfWeek());
        holder.time.setText(course.getTime());
        holder.duration.setText(String.valueOf(course.getDuration()));

        holder.itemView.setOnClickListener(view -> {
            if (onCourseClickListener != null) {
                onCourseClickListener.onCourseClick(course.getId()); // Pass the courseId
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListCourse != null) {
            return mListCourse.size();
        }
        return 0;
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView name, description, dayOfWeek, time, duration;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvNameCourse);
            description = itemView.findViewById(R.id.tvDescriptionCourse);
            dayOfWeek = itemView.findViewById(R.id.tvDateOfWeekCourse);
            time = itemView.findViewById(R.id.tvTimeCourse);
            duration = itemView.findViewById(R.id.tvDurationCourse);
        }
    }
}
