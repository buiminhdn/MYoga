import React, { useState, useCallback } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  FlatList,
  Alert,
  ScrollView,
  SafeAreaView,
  KeyboardAvoidingView,
  Platform,
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import colors from "../../assets/styles/Colors";
import ClassItem from "../../components/ClassItem/ClassItem";
import { useUser } from "../../context/UserContext";
import { ClassSession } from "../../models/classSession.interface";
import { fetchClassesInCart } from "../../services/cart.service";
import { placeOrder } from "../../services/order.service";
import { useFocusEffect } from "@react-navigation/native";
import styles from "./CartScreen.style";

function CartScreen() {
  const userContext = useUser();
  const userId = userContext ? userContext.userId : null;
  const [orderedClasses, setOrderedClasses] = useState<ClassSession[]>([]);
  const [email, setEmail] = useState<string>("");

  // Fetch classes when the screen is focused
  const loadClassesInCart = useCallback(async () => {
    if (userId) {
      const classes = await fetchClassesInCart(userId);
      setOrderedClasses(classes);
    }
  }, [userId]);

  // Reload data when the screen is focused
  useFocusEffect(
    useCallback(() => {
      loadClassesInCart();
    }, [loadClassesInCart])
  );

  const handlePlaceOrder = async () => {
    if (!email) {
      Alert.alert("Error", "Please enter an email address.");
      return;
    }

    if (orderedClasses.length === 0) {
      Alert.alert("Error", "Your cart is empty.");
      return;
    }

    if (!userId) {
      Alert.alert("Error", "User ID is missing.");
      return;
    }

    const orderResult = await placeOrder(
      userId,
      email,
      orderedClasses.map((cls) => ({ id: cls.id }))
    );

    if (orderResult.success) {
      Alert.alert("Success", "Your order has been placed successfully!");
      setOrderedClasses([]);
    } else {
      Alert.alert("Error", "Failed to place order.");
    }
  };

  return (
    <SafeAreaView style={{ flex: 1, backgroundColor: "#F7F8FA" }}>
      <KeyboardAvoidingView
        style={{ flex: 1 }}
        behavior={Platform.OS === "ios" ? "padding" : undefined}
      >
        <ScrollView contentContainerStyle={{ flexGrow: 1 }}>
          <Text style={styles.title}>Cart</Text>
          <Text style={styles.subtitle}>
            {`${orderedClasses.length} Ordered Classes`}
          </Text>

          <View style={{ flex: 1 }}>
            <FlatList
              data={orderedClasses}
              keyExtractor={(item) => item.id}
              renderItem={({ item }) => <ClassItem item={item} />}
              contentContainerStyle={styles.orderedClassesContainer}
            />
          </View>

          <View style={styles.divider} />
          <Text style={styles.sectionTitle}>Customer Detail</Text>
          <TextInput
            style={styles.input}
            placeholder="Email"
            keyboardType="email-address"
            value={email}
            onChangeText={setEmail}
          />
          <TouchableOpacity
            style={styles.placeOrderButton}
            onPress={handlePlaceOrder}
          >
            <Ionicons name="send-outline" color={colors.white} size={18} />
            <Text style={styles.placeOrderText}>Place order</Text>
          </TouchableOpacity>
        </ScrollView>
      </KeyboardAvoidingView>
    </SafeAreaView>
  );
}

export default CartScreen;
