import { Component, Input, OnInit, AfterViewChecked, ViewChild } from '@angular/core';
import { ChartComponent as AngularChartComponent } from 'angular2-chartjs';
import { ChartData } from 'src/app/model/ChartData';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.less']
})
export class ChartComponent implements OnInit, AfterViewChecked {

  @Input() public type: string = 'line';
  @Input() public data: ChartData = {
    datasets: [],
    labels: []
  };
  @Input() public options = {
    responsive: true,
    maintainAspectRatio: false
  };
  @ViewChild(AngularChartComponent) chart!: AngularChartComponent;

  constructor() {
  }

  ngOnInit(): void {
  }

  ngAfterViewChecked(): void {
    this.chart.chart.update()
  }

}
