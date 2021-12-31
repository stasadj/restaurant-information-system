export interface RestaurantTable {
  id: string;
  rotateValue: number;
  size: {
    w: number;
    h: number;
  };
  radius: number;
  position: {
    x: number;
    y: number;
  };
  status?: string;
}
