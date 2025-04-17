# **Yoga Booking Mobile App**

## **Overview**

The Yoga Booking app is a **hybrid mobile application** developed using **Android (Java)** and **React Native (TypeScript)**. It offers users a seamless experience to browse yoga classes, manage their cart, and place bookings. The app includes **real-time synchronization**, efficient **data storage**, and a highly interactive **UI** for a smooth user experience.

## **Key Features**

### **Course and Class Management**
- Users can **view** and **filter** courses by date and name.
- **Class instances** are validated to ensure correct scheduling and smooth booking.
- Users can **add** and **update** courses with validation checks for required fields.

### **Cart and Booking**
- Users can **add** classes to their cart and **place orders** using their email.
- **Cart synchronization** is handled dynamically with Firebase, ensuring smooth data flow between the app and the cloud.

### **Real-Time Synchronization**
- The app uses **WorkManager** to **sync data** periodically from local devices to the cloud.
- Synchronization tracks **add, update, and delete operations**, ensuring data consistency across platforms.

### **Responsive UI**
- The UI is **responsive** across various screen sizes, featuring **horizontal scrolling** and **animated carousels** (using Glide).
- **Tab-based navigation** enhances usability, allowing users to switch between **Home**, **Cart**, and **Profile** screens easily.

### **User Interaction**
- **Error handling** is integrated with clear messages for missing or incorrect input.
- **Dialogs** and **toast messages** provide feedback on operations like adding, updating, or deleting courses.

## **Tech Stack**

- **Android:** Java, **SQLite**, **WorkManager**, **Glide**.
- **React Native:** TypeScript, **Firebase**, **Firestore**.
- **Backend:** Firebase for cloud storage and syncing.

## **Security**
- Basic **input validation** ensures the integrity of user data.
- **Data synchronization** between local storage and Firebase uses **secure protocols**.
- **Encryption** is considered for sensitive data at rest.

## **Architecture**
- The app follows a **modular MVVM architecture**, making it scalable and maintainable.
- **Room Database** is used for local storage, while **Firestore** handles cloud synchronization.
- Background synchronization tasks are managed by **WorkManager**, ensuring data consistency.
