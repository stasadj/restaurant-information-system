export interface OrderItem {
  id?: number;
  amount: number;
  orderId?: number;
  orderStatus?: string;
  itemId: number;
  item: { name: string; type?: string; price?: number };
  cookId?: number;
  barmanId?: number;
}
