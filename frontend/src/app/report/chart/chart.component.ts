import { Component, Input, OnInit, ViewChild, OnChanges, SimpleChanges } from '@angular/core';
import { ChartComponent as AngularChartComponent } from 'angular2-chartjs';
import { ChartData } from 'src/app/model/ChartData';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.less']
})
export class ChartComponent implements OnInit, OnChanges {

  @Input() type: string = 'line';
  @Input() data: ChartData = {
    datasets: [],
    labels: []
  };
  @Input() options = {
    responsive: true,
    maintainAspectRatio: false
  };
  @ViewChild(AngularChartComponent) chart!: AngularChartComponent;

  constructor() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.chart) {
      this.chart.chart.update();
    }
  }

  ngOnInit(): void {
  }

}
