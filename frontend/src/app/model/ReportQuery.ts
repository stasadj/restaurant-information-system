import { ReportGranularity } from "./ReportGranularity";
import { ReportType } from "./ReportType";

export interface ReportQuery {
    fromDate: string,
    toDate: string,
    reportGranularity: ReportGranularity,
    reportType: ReportType,
    itemId?: number,
}