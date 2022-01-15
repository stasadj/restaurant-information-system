export interface Notification {
  id: number;
  text: string;
  dateTime: Date;
  type: string;
  orderId: number;
  tableId: number;
  waiterId: number;
}
