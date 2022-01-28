import { Component, OnInit } from '@angular/core';
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

  query: ReportQuery = {
    fromDate: this.dateSerilize.transform(new Date()),
    toDate: this.dateSerilize.transform(new Date()),
    reportGranularity: ReportGranularity.DAILY,
    reportType: ReportType.PROFIT,
  };
  report: Report = {
    datapoints: [],
    individualItems: []
  };

  constructor(private reportService: ReportService, private dateSerilize: DateSerializePipe) {
  }

  updateReport() {
    if (this.query) {
      if (this.query.reportType === ReportType.PRICE_HISTORY && this.query.itemId === undefined) {
        return;
      }
      this.reportService.getReport(this.query).subscribe(report => {
        this.report = report;
      })
    }
  }

  ngOnInit(): void {
  }

}
