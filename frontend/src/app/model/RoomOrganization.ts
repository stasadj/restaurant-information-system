import { RestaurantTable } from './RestaurantTable';

export interface RoomOrganization {
  id: string;
  tables: RestaurantTable[];
}
