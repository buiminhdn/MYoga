// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore"
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyDLADTwIx4pw6lbEHi9qKnDMLevd-DGiWg",
  authDomain: "bmyoga-e90c0.firebaseapp.com",
  projectId: "bmyoga-e90c0",
  storageBucket: "bmyoga-e90c0.firebasestorage.app",
  messagingSenderId: "303012731454",
  appId: "1:303012731454:web:106e08bdb76fb506604f6f"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const db = getFirestore()