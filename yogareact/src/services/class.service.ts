// CourseService.js
import { db } from '../config/firebase';
import { collection, getDocs, doc, getDoc, setDoc, updateDoc, arrayUnion } from "firebase/firestore";
import { Course } from '../models/course.interface';
import { ClassSession } from '../models/classSession.interface';

export const fetchClasses = async (): Promise<ClassSession[]> =>  {
    try {
      const classesCollection = collection(db, "classes");
      const classSnapshot = await getDocs(classesCollection);
      const classList = classSnapshot.docs.map((doc) => {
        const data = doc.data();
        return {
          id: doc.id,
          teacherName: data.teacherName,
          date: data.date,
          comment: data.comment,
          courseId: data.courseId,
        } as ClassSession;
      });
      
      return classList;
    } catch (error) {
      return [];
    }
}

// Function to fetch class details by ID
export const fetchClassById = async (id: string): Promise<ClassSession | null> => {
  try {
    const classRef = doc(db, "classes", id);
    const classDoc = await getDoc(classRef);
    if (classDoc.exists()) {
      const data = classDoc.data();
      return {
        id: classDoc.id,
        teacherName: data.teacherName,
        date: data.date,
        comment: data.comment,
        courseId: data.courseId,
      } as ClassSession;
    } else {
      console.log("No such class found");
      return null;
    }
  } catch (error) {
    console.error("Error fetching class by ID:", error);
    return null;
  }
};

// Function to fetch course details by course ID
export const fetchCourseById = async (courseId: string): Promise<Course | null> => {
  try {
    const courseRef = doc(db, "courses", courseId); // Adjust collection name if needed
    const courseDoc = await getDoc(courseRef);
    if (courseDoc.exists()) {
      const data = courseDoc.data();
      return {
        id: Number(courseDoc.id),
        name: data.name,
        description: data.description,
        capacity: data.capacity,
        duration: data.duration,
        price: data.price,
        time: data.time,
        type: data.type,
        dayOfWeek: data.dayOfWeek,
      } as Course;
    } else {
      console.log("No such course found");
      return null;
    }
  } catch (error) {
    console.error("Error fetching course by ID:", error);
    return null;
  }
};