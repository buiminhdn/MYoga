import { db } from "../config/firebase";
import { doc, setDoc, collection, updateDoc, arrayRemove, deleteDoc, where, query, getDocs  } from "firebase/firestore";
import { Order } from "../models/order.interface";


export const placeOrder = async (userId: string, email: string, orderedClasses: { id: string }[]) => {
    try {
      const orderRef = doc(collection(db, "orders")); 
      const orderData = {
        userId: userId,
        email: email,
        classes: orderedClasses, 
        createdAt: new Date(),
      };
  
      // Save the order to Firestore
      await setDoc(orderRef, orderData);
  
      // Now remove the classes from the cart
      const cartRef = doc(db, "carts", userId);
      await updateDoc(cartRef, {
        classes: arrayRemove(...orderedClasses), // Remove the ordered classes
      });

      await deleteDoc(cartRef);
      return { success: true, message: "Order placed successfully" };
    } catch (error) {
      return { success: false, message: "Error placing order" };
    }
  };


  export const fetchOrders = async (userId: string): Promise<Order[]> => {
    try {
      // Reference to the orders collection
      const ordersRef = collection(db, "orders");
  
      // Query to fetch orders by userId
      const q = query(ordersRef, where("userId", "==", userId));
      const querySnapshot = await getDocs(q);
  
      if (querySnapshot.empty) {
        console.log("No orders found for this user.");
        return [];
      }
  
      // Map the query snapshot to the Order model
      const orders: Order[] = querySnapshot.docs.map((doc) => {
        const orderData = doc.data();
        return {
          classes: orderData.classes,
          createdAt: orderData.createdAt.toDate(),
          email: orderData.email,
          userId: orderData.userId,
        };
      });
  
      return orders;
    } catch (error) {
      console.error("Error fetching orders:", error);
      return [];
    }
  };