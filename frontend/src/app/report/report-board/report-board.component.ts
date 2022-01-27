import { Component, Input, OnInit } from '@angular/core';
import { ChartData } from 'src/app/model/ChartData';
import { ChartDataset } from 'src/app/model/ChartDataset';
import { Report } from 'src/app/model/Report';
import { ReportGranularity } from 'src/app/model/ReportGranularity';
import { ReportQuery } from 'src/app/model/ReportQuery';
import { ReportType } from 'src/app/model/ReportType';
import { DateSerializePipe } from 'src/app/pipes/date-serialize/date-serialize.pipe';
import { ReportService } from 'src/app/services/report/report.service';

@Component({
  selector: 'app-report-board',
  templateUrl: './report-board.component.html',
  styleUrls: ['./report-board.component.less']
})
export class ReportBoardComponent implements OnInit {

  // TODO: Make this be sent to the component via a new search component
  @Input() public query: ReportQuery = {
    fromDate: this.dateSerilize.transform(new Date("2021-11-11")),
    toDate: this.dateSerilize.transform(new Date("2021-12-01")),
    reportGranularity: ReportGranularity.DAILY,
    reportType: ReportType.PROFIT,
  };;
  report: Report = {
    datapoints: [],
    individualItems: []
  };

  constructor(private reportService: ReportService, private dateSerilize: DateSerializePipe) {
    if (this.query) {
      this.reportService.getReport(this.query).subscribe(report => {
        this.report = report;
      })
    }
  }

  ngOnInit(): void {
  }

}
