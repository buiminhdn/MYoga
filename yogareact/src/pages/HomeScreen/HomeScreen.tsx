import React, { useEffect, useState } from "react";
import { View, Text, TextInput, FlatList, StyleSheet } from "react-native";
import { Picker } from "@react-native-picker/picker";
import ClassItem from "../../components/ClassItem/ClassItem";
import { fetchClasses } from "../../services/class.service";
import { ClassSession } from "../../models/classSession.interface";
import styles from "./HomeScreen.style";

const HomeScreen = () => {
  const [allClasses, setAllClasses] = useState<ClassSession[]>([]); // Stores all fetched classes
  const [classes, setClasses] = useState<ClassSession[]>([]); // Displays filtered classes
  const [searchQuery, setSearchQuery] = useState<string>("");
  const [selectedDay, setSelectedDay] = useState<string>("");

  useEffect(() => {
    async function loadClasses() {
      const fetchedClasses = await fetchClasses();
      setAllClasses(fetchedClasses); // Save all classes
      setClasses(fetchedClasses); // Initially display all classes
    }

    loadClasses();
  }, []);

  // Helper function to get the day of the week from a date
  const getDayOfWeek = (dateString: string) => {
    const days = [
      "Sunday",
      "Monday",
      "Tuesday",
      "Wednesday",
      "Thursday",
      "Friday",
      "Saturday",
    ];

    // Parse the date in DD/MM/YYYY format
    const [day, month, year] = dateString.split("/").map(Number);
    const date = new Date(year, month - 1, day); // Month is 0-based
    return days[date.getDay()];
  };

  // Handle filtering whenever the search query or selected day changes
  useEffect(() => {
    let filtered = allClasses;

    if (selectedDay === "all") {
      setSelectedDay("");
    }

    // Filter by day of the week if a specific day is selected
    if (selectedDay) {
      filtered = filtered.filter(
        (cls) => getDayOfWeek(cls.date) === selectedDay
      );
    }

    // Further filter by search query
    if (searchQuery) {
      filtered = filtered.filter((cls) =>
        cls.teacherName.toLowerCase().includes(searchQuery.toLowerCase())
      );
    }

    setClasses(filtered); // Update displayed classes
  }, [searchQuery, selectedDay, allClasses]);

  return (
    <View style={styles.container}>
      {/* Search Input */}
      <TextInput
        style={styles.searchInput}
        placeholder="Search for..."
        value={searchQuery}
        onChangeText={(text) => setSearchQuery(text)}
      />

      {/* Day of Week Picker */}
      <Picker
        selectedValue={selectedDay}
        onValueChange={(itemValue) => setSelectedDay(itemValue)}
        style={styles.picker}
      >
        <Picker.Item label="All Days" value="all" />
        <Picker.Item label="Sunday" value="Sunday" />
        <Picker.Item label="Monday" value="Monday" />
        <Picker.Item label="Tuesday" value="Tuesday" />
        <Picker.Item label="Wednesday" value="Wednesday" />
        <Picker.Item label="Thursday" value="Thursday" />
        <Picker.Item label="Friday" value="Friday" />
        <Picker.Item label="Saturday" value="Saturday" />
      </Picker>

      {/* Available Classes */}
      <Text style={styles.classCount}>{classes.length} Classes Available</Text>

      {/* Class List */}
      <FlatList
        data={classes}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => <ClassItem item={item} />}
        contentContainerStyle={styles.classList}
      />
    </View>
  );
};

export default HomeScreen;
