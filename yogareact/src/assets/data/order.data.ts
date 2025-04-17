import { Order } from "../../models/order.interface";


export const ordersData: Order[] = [
    {
      id: 1,
      name: 'John Doe',
      phone: '+1234567890',
      classes: [
        {
          id: 'class1',
          teacherName: 'Max Danish',
          date: '17th October 2023',
          comment: 'In publishing and graphic design, Lorem ipsum...',
          courseId: 1,
        },
        {
            id: 'class2',
            teacherName: 'Max Danish',
            date: '17th October 2023',
            comment: 'In publishing and graphic design, Lorem ipsum...',
            courseId: 1,
          },
          {
            id: 'class3',
            teacherName: 'Max Danish',
            date: '17th October 2023',
            comment: 'In publishing and graphic design, Lorem ipsum...',
             courseId: 1,
          },
          {
            id: 'class14',
            teacherName: 'Max Danish',
            date: '17th October 2023',
            comment: 'In publishing and graphic design, Lorem ipsum...',
             courseId: 1,
          },
      ],
      isVerified: true,
    },
    {
      id: 2,
      name: 'John Doe',
      phone: '+1234567890',
      classes: [
        {
          id: 'class2',
          teacherName: 'Max Danish',
          date: '17th October 2023',
          comment: 'In publishing and graphic design, Lorem ipsum...',
          courseId: 1,
        },
        {
          id: 'class3',
          teacherName: 'Jane Smith',
          date: '18th October 2023',
          comment: 'Another example course description...',
          courseId: 1,
        },
      ],
      isVerified: false,
    },
  ];