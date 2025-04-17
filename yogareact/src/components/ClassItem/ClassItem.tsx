import { View, Text, StyleSheet, TouchableOpacity } from "react-native";
import colors from "../../assets/styles/Colors";
import { StackNavigationProp } from "@react-navigation/stack";
import { useNavigation } from "@react-navigation/native";
import {
  ClassDetailStackParams,
  ClassSession,
} from "../../models/classSession.interface";

function ClassItem({ item }: { item: ClassSession }) {
  const navigation =
    useNavigation<StackNavigationProp<ClassDetailStackParams>>();

  return (
    <TouchableOpacity
      onPress={() =>
        navigation.navigate("DetailScreen", {
          id: item.id,
        })
      }
      style={styles.classItem}
    >
      <Text style={styles.classTeacher}>Teacher: {item.teacherName}</Text>
      <Text style={styles.classDate}>Date: {item.date}</Text>
      <Text style={styles.classDescription} numberOfLines={2}>
        {item.comment}
      </Text>
    </TouchableOpacity>
  );
}

const styles = StyleSheet.create({
  classItem: {
    backgroundColor: "#FFFFFF",
    marginHorizontal: 16,
    borderRadius: 10,
    padding: 15,
    marginBottom: 15,
    shadowColor: "#aaaaaa",
    shadowOffset: {
      width: 0,
      height: 7,
    },
    shadowOpacity: 0.21,
    shadowRadius: 7.68,
    elevation: 10,
  },
  classTeacher: {
    fontSize: 16,
    fontWeight: "bold",
    marginBottom: 5,
  },
  classDate: {
    fontSize: 14,
    marginBottom: 8,
    color: colors.darkGray,
  },
  classDescription: {
    fontSize: 14,
    color: colors.darkGray,
  },
});

export default ClassItem;
