import React, { useState, useCallback } from "react";
import { View, Text, FlatList, TouchableOpacity } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import colors from "../../assets/styles/Colors";
import ClassItem from "../../components/ClassItem/ClassItem"; // Assuming ClassItem component is already created
import { Order } from "../../models/order.interface";
import { fetchOrders } from "../../services/order.service";
import { useUser } from "../../context/UserContext";
import { ClassSession } from "../../models/classSession.interface";
import { fetchClassById } from "../../services/class.service";
import { useFocusEffect } from "@react-navigation/native"; // Import useFocusEffect
import styles from "./ProfileScreen.style";

function ProfileScreen() {
  const userContext = useUser();
  const userId = userContext ? userContext.userId : null;
  const [orders, setOrders] = useState<Order[]>([]); // State to store orders
  const [classes, setClasses] = useState<Record<string, ClassSession>>({}); // State to store classes by ID

  const loadOrdersAndClasses = useCallback(async () => {
    if (userId) {
      try {
        const fetchedOrders = await fetchOrders(userId);
        setOrders(fetchedOrders);

        // Extract unique class IDs and fetch their details
        const classIds = Array.from(
          new Set(
            fetchedOrders.flatMap((order) => order.classes.map((c) => c.id))
          )
        );
        const classDetails = await Promise.all(
          classIds.map((id) => fetchClassById(id.toString()))
        );

        // Map class details by ID
        const classesById = Object.fromEntries(
          classDetails
            .filter(
              (classItem): classItem is ClassSession => classItem !== null
            )
            .map((classItem) => [classItem.id, classItem])
        );
        setClasses(classesById);
      } catch (error) {
        console.error("Error loading orders and classes:", error);
      }
    }
  }, [userId]);

  // Reload data when the screen is focused
  useFocusEffect(
    useCallback(() => {
      loadOrdersAndClasses();
    }, [loadOrdersAndClasses])
  );

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Profile</Text>

      {/* Verified Orders Section */}
      <Text style={styles.sectionTitle}>Booked Classes</Text>
      <FlatList
        data={orders}
        keyExtractor={(order) => order.createdAt.toString()} // Use createdAt for keyExtractor
        renderItem={({ item: order }) => (
          <View style={styles.orderContainer}>
            {order.classes.map((classItem) => {
              const classDetails = classes[classItem.id]; // Get class details by ID
              return classDetails ? (
                <ClassItem key={classItem.id} item={classDetails} />
              ) : null; // Render only if details are available
            })}
          </View>
        )}
        contentContainerStyle={styles.courseList}
        nestedScrollEnabled
      />
    </View>
  );
}

export default ProfileScreen;
