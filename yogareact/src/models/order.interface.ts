export interface Order {
    classes: orderClass[];
    createdAt: Date;
    email: string;
    userId: string;
}

interface orderClass {
    id: number;
}