export interface RestaurantTable {
  id: number;
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
