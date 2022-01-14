export interface OrderItem {
  id?: number;
  amount: number;
  orderId?: number;
  orderStatus?: string;
  itemId: number;
  item: { name: string; type?: string };
  cookId?: number;
  barmanId?: number;
}
