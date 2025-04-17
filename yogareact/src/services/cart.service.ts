// cart.service.js
import { db } from "../config/firebase";
import { doc, getDoc, updateDoc, arrayUnion, setDoc, collection, getDocs } from "firebase/firestore";
import { ClassSession } from "../models/classSession.interface";

export async function addToCart(userId: string, classData: { id: string }) {
  try {
    const cartRef = doc(db, "carts", userId); 
    const cartSnap = await getDoc(cartRef); 

    if (cartSnap.exists()) {
      // Cart exists, check if the class is already in the cart
      const cartData = cartSnap.data();
      const classExists = cartData.classes.some(
        (cartClass: { id: string }) => cartClass.id === classData.id
      );

      if (classExists) {
        return { success: false, message: "Class already in cart" };
      }

      // Add only the class id to the cart using arrayUnion (to avoid duplicates)
      await updateDoc(cartRef, {
        classes: arrayUnion({ id: classData.id }), // Store only the class id
      });
    } else {
      // If the cart document doesn't exist, create it with the class id
      await setDoc(cartRef, { classes: [{ id: classData.id }] }); // Store only the class id
    }

    return { success: true, message: "Class added to cart" };
  } catch (error) {
    return { success: false, message: "Error adding to cart" };
  }
}

export const fetchClassesInCart = async (userId: string): Promise<ClassSession[]> => {
  try {
    // Reference to the user's cart document
    const cartRef = doc(db, "carts", userId);
    const cartSnap = await getDoc(cartRef);

    if (!cartSnap.exists()) {
      return [];
    }

    // Extract class IDs from the cart document
    const cartData = cartSnap.data();
    const classIds: string[] = cartData.classes.map((classItem: { id: string }) => classItem.id);

    if (classIds.length === 0) {
      return [];
    }

    // Fetch full class details for each classId
    const classDetailsPromises = classIds.map(async (classId) => {
      const classRef = doc(db, "classes", classId); // Reference to the full class details
      const classSnap = await getDoc(classRef);
      
      if (classSnap.exists()) {
        return classSnap.data() as ClassSession;
      } else {
        return null;
      }
    });

    // Wait for all class details to be fetched
    const classDetails = await Promise.all(classDetailsPromises);

    // Filter out any null results if any classes are not found
    return classDetails.filter((classDetail) => classDetail !== null) as ClassSession[];
  } catch (error) {
    return [];
  }
};