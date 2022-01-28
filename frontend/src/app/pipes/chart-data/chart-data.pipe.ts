import { Pipe, PipeTransform } from '@angular/core';
import { ChartData } from 'src/app/model/ChartData';
import { ChartDataset } from 'src/app/model/ChartDataset';
import { Report } from 'src/app/model/Report';

@Pipe({
  name: 'TransformReportToChart'
})
export class ChartDataPipe implements PipeTransform {

  transform(report: Report): ChartData {
    let netIncomeDatapoints: ChartDataset = {
      data: [],
      label: 'Net Income',
      borderColor: '#27F207B3',
      backgroundColor: '#27F2079B',
    };
    let expenseDatapoints: ChartDataset = {
      data: [],
      label: 'Expenses',
      borderColor: '#E65338B3',
      backgroundColor: '#E653384D',
    };
    let grossIncomeDatapoints: ChartDataset = {
      data: [],
      label: 'Gross Income',
      borderColor: '#0F2FFCB3',
      backgroundColor: '#0F2FFC4D',
    };

    let chartData: ChartData = { datasets: [netIncomeDatapoints, expenseDatapoints, grossIncomeDatapoints], labels: [] }
    report.datapoints.forEach(datapoint => {
      chartData.labels.push(datapoint.label);
      netIncomeDatapoints.data.push(datapoint.grossIncome - datapoint.expenses);
      expenseDatapoints.data.push(datapoint.expenses);
      grossIncomeDatapoints.data.push(datapoint.grossIncome);
    })

    return chartData;
  }

}
