1) Overview

  The application is a hybrid yoga booking platform built using both Android native and React Native. It provides functionalities for users to browse yoga classes, manage their cart, and place bookings. The app includes several features to enhance user experience, including real-time syncing, efficient data storage, and interactive UI components.

2) Key Features

  Course and Class Management:\
  
    Users can view courses, filter them by date and name, and explore detailed information for each class.
    Each class instance is validated for the correct date, ensuring a smooth booking process.
    Users can add new courses and update existing ones, with validation checks in place for required fields.

  Cart and Booking:

    Users can add classes to their cart and place orders by entering their email.
    The cart is managed dynamically in Firebase, ensuring synchronization between the app and the cloud.

  Real-Time Synchronization:

    The app uses WorkManager to periodically sync data from the local device to the cloud, ensuring up-to-date information across all platforms.
    Data synchronization includes add, update, and delete operations tracked by status.

  Responsive UI:
  
    The UI is designed to be responsive across different screen sizes, utilizing horizontal scrolling, animated carousels (using Glide), and dynamic filtering options for enhanced usability.
    The app features tab navigation to easily switch between Home, Cart, and Profile screens.

  User Interaction:

    Error handling is integrated throughout, displaying clear messages for required fields and successful or failed operations.
    Dialogs and toast messages guide users through adding and updating classes or courses.

3) Tech Stack

  Android: Built using Java, SQLite for local data storage, WorkManager for background tasks, and Glide for image loading.

  React Native: Uses TypeScript, Firebase for backend, and Firestore for storing and retrieving data.

4) Security
   
  Basic security measures are implemented such as input validation to prevent crashes and errors.

Data is synchronized securely with Firebase using Firestore, and encryption is considered for sensitive data storage.
