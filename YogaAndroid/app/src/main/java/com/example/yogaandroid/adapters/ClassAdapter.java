package com.example.yogaandroid.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaandroid.R;
import com.example.yogaandroid.entities.models.ClassSession;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private List<ClassSession> mListClass;
    private final OnClassClickListener classClickListener;
    private final boolean showIcons;

    public ClassAdapter(List<ClassSession> mListClass, OnClassClickListener classClickListener, boolean showIcons) {
        this.mListClass = mListClass;
        this.classClickListener = classClickListener;
        this.showIcons = showIcons;
    }

    public interface OnClassClickListener
    {
        void onClassEdit(ClassSession classSession);
        void onClassDelete(int classId);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setClasses(List<ClassSession> classes) {
        this.mListClass = classes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        return new ClassAdapter.ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassSession classSession = mListClass.get(position);

        if (classSession == null) {
            return;
        }

        holder.tvTeacherClass.setText(classSession.getTeacherName());
        holder.tvDateClass.setText(classSession.getDate());
        holder.tvCommentClass.setText(classSession.getComment());

        if (showIcons) {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnEdit.setVisibility(View.VISIBLE);

            // Set delete and edit button listeners
            holder.btnDelete.setOnClickListener(view -> {
                classClickListener.onClassDelete(classSession.getId());
            });
            holder.btnEdit.setOnClickListener(view -> {
                classClickListener.onClassEdit(classSession);
            });
        } else {
            holder.btnDelete.setVisibility(View.GONE);
            holder.btnEdit.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (mListClass != null) {
            return mListClass.size();
        }
        return 0;
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {

        TextView tvTeacherClass, tvDateClass, tvCommentClass;
        ImageView btnDelete, btnEdit;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTeacherClass = itemView.findViewById(R.id.tvTeacherClass);
            tvDateClass = itemView.findViewById(R.id.tvDateClass);
            tvCommentClass = itemView.findViewById(R.id.tvCommentClass);
            btnDelete = itemView.findViewById(R.id.ivDeleteClass);
            btnEdit = itemView.findViewById(R.id.ivEditClass);

        }
    }

}
