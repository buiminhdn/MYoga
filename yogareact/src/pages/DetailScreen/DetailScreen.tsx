import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  Button,
  ScrollView,
  TouchableOpacity,
  Alert,
} from "react-native";
import InfoRow from "../../components/InfoRow/InfoRow";
import colors from "../../assets/styles/Colors";
import { Ionicons } from "@expo/vector-icons";
import { useRoute, RouteProp } from "@react-navigation/native";
import { Course } from "../../models/course.interface";
import { fetchClassById, fetchCourseById } from "../../services/class.service";
import { useNavigation } from "@react-navigation/native";
import {
  ClassDetailStackParams,
  ClassSession,
} from "../../models/classSession.interface";
import { useUser } from "../../context/UserContext";
import { addToCart } from "../../services/cart.service";
import styles from "./DetailScreen.style";

type CourseDetailScreenRouteProp = RouteProp<
  ClassDetailStackParams,
  "DetailScreen"
>;

function DetailScreen() {
  const userContext = useUser();
  const userId = userContext ? userContext.userId : null;
  const route = useRoute<CourseDetailScreenRouteProp>();
  const navigation = useNavigation();
  const { id } = route.params;

  const [classDetail, setClassDetail] = useState<ClassSession | null>(null);
  const [courseDetail, setCourseDetail] = useState<Course | null>(null);

  useEffect(() => {
    async function loadClassDetails() {
      const classData = await fetchClassById(id);
      setClassDetail(classData);

      if (classData?.courseId) {
        const courseData = await fetchCourseById(classData.courseId.toString());
        setCourseDetail(courseData);
      }
    }

    loadClassDetails();
  }, [id]);

  const handleAddToCart = async () => {
    if (!userId || !classDetail) {
      Alert.alert("Error", "User ID or class details not available.");
      return;
    }

    const response = await addToCart(userId, {
      id: classDetail.id,
    });

    Alert.alert(response.success ? "Success" : "Error", response.message);
  };

  const formatYogaType = (yogaType: string) => {
    if (!yogaType) return "N/A";
    return yogaType
      .toLowerCase()
      .split("_")
      .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
      .join(" ");
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <TouchableOpacity
        onPress={() => navigation.goBack()}
        style={styles.backButton}
      >
        <Ionicons name="arrow-back" color={colors.darkBlue} size={20} />
      </TouchableOpacity>

      <Text style={styles.title}>{courseDetail?.name || "Yoga Class"}</Text>

      <Text style={styles.description}>
        {courseDetail?.description ||
          "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content."}
      </Text>

      <View style={styles.infoContainer}>
        <InfoRow label="Day of Week" value={courseDetail?.dayOfWeek || "N/A"} />
        <InfoRow label="Time" value={courseDetail?.time || "N/A"} />
        <InfoRow
          label="Duration"
          value={`${courseDetail?.duration || "N/A"} minutes`}
        />
        <InfoRow
          label="Type"
          value={formatYogaType(courseDetail?.type ?? "N/A")}
        />
        <InfoRow
          label="Capacity"
          value={`${courseDetail?.capacity || "N/A"} people`}
        />
        <InfoRow label="Price" value={`$ ${courseDetail?.price || "N/A"}`} />
      </View>

      <View style={styles.divider} />

      <View style={styles.infoContainer}>
        <InfoRow label="Teacher" value={classDetail?.teacherName || "N/A"} />
        <InfoRow label="Date" value={classDetail?.date || "N/A"} />
      </View>

      <Text style={styles.description}>{classDetail?.comment || "N/A"}</Text>

      <TouchableOpacity
        style={styles.addToCartButton}
        onPress={handleAddToCart}
      >
        <Ionicons name="cart-outline" color={colors.white} size={18} />
        <Text style={styles.addToCartText}>ADD TO CART</Text>
      </TouchableOpacity>
    </ScrollView>
  );
}

export default DetailScreen;
