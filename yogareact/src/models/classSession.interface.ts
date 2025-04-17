export interface ClassSession {
    id: string;
    teacherName: string;
    date: string;
    comment: string;
    courseId: number;
}

export type ClassDetailStackParams = {
    DetailScreen: { id: string };
  };