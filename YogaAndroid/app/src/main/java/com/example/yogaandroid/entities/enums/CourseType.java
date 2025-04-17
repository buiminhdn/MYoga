package com.example.yogaandroid.entities.enums;

import com.example.yogaandroid.R;

public enum CourseType {
    FLOW_YOGA(R.id.rbFlowYoga, "Flow Yoga"),
    AERIAL_YOGA(R.id.rbAerialYoga, "Aerial Yoga"),
    FAMILY_YOGA(R.id.rbFamilyYoga, "Family Yoga");

    private final int id;
    private final String name;

    CourseType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static CourseType fromId(int id) {
        for (CourseType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }
}
