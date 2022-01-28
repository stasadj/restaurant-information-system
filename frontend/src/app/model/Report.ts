import { DateDatapoint } from "./DateDatapoint";
import { ItemDatapoint } from "./ItemDatapoint";

export interface Report {
    datapoints: DateDatapoint[]
    individualItems: ItemDatapoint[]
}