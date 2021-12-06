package com.restaurant.backend.dto.reports;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResultsDTO {
    private List<AbstractDateReportResultItemDTO> datapoints;

    private List<ItemReportResultItemDTO> individualItems;
}
