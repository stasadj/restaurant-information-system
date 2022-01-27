import { ChartDataset } from "./ChartDataset";

export interface ChartData {
    labels: string[],
    datasets: ChartDataset[]
}