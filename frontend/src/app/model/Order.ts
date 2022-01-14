import { OrderItem } from './OrderItem';

export interface Order {
  id?: number;
  createdAt?: Date;
  note?: string;
  tableId: number;
  orderItems: OrderItem[];
  waiterId: number;
}
