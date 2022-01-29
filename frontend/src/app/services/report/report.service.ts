import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ChartData } from 'src/app/model/ChartData';
import { ChartDataset } from 'src/app/model/ChartDataset';
import { Report } from 'src/app/model/Report';
import { ReportQuery } from 'src/app/model/ReportQuery';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private readonly path = '/api/report';

  constructor(private http: HttpClient) { }

  getReport(query: ReportQuery): Observable<Report> {
    let params = new HttpParams();
    Object.entries(query).forEach(([key, value]) => {
        params = params.set(key, value);
    });
    return this.http.get<Report>(this.path + "/query", { params: params });
  }
}
