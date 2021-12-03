package com.restaurant.backend.service;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.OrderRecord;
import com.restaurant.backend.dto.reports.enums.QuarterOfYear;
import com.restaurant.backend.dto.reports.*;
import com.restaurant.backend.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportService {
    private final ItemService itemService;
    private final OrderRecordService orderRecordService;

    private ItemReportResultItemDTO getItemSales(Item item, LocalDate fromDate, LocalDate toDate) {
        ItemReportResultItemDTO result = new ItemReportResultItemDTO(item.getName());
        List<OrderRecord> records = orderRecordService.getAllOrderRecordsBetweenDatesForItem(item.getId(), fromDate, toDate);

        for (OrderRecord record : records) {
            Integer amount = record.getAmount();
            ItemValue value = record.getItemValue();
            BigDecimal quantity = new BigDecimal(amount);
            BigDecimal expense = value.getPurchasePrice().multiply(quantity);
            BigDecimal income = value.getSellingPrice().multiply(quantity);

            result.addQuantity(amount);
            result.addExpense(expense);
            result.addIncome(income);
        }
        return result;
    }

    private List<AbstractDateReportResultItemDTO> generateDataPoints(ReportQueryDTO query) {
        switch (query.getReportGranularity()) {
        case DAILY:
            return query.getFromDate().datesUntil(query.getToDate().plusDays(1L)).map(DailyReportResultItemDTO::new)
                    .collect(Collectors.toList());
        case WEEKLY:
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            return query.getFromDate().datesUntil(query.getToDate(), Period.ofWeeks(1)).map(
                    date -> new WeeklyReportResultItemDTO(date.get(weekFields.weekOfWeekBasedYear()), Year.from(date)))
                    .collect(Collectors.toList());
        case MONTHLY:
            return query.getFromDate().datesUntil(query.getToDate(), Period.ofMonths(1))
                    .map(date -> new MonthlyReportResultItemDTO(date.getMonth(), Year.from(date)))
                    .collect(Collectors.toList());
        case QUARTERLY:
            return query.getFromDate().datesUntil(query.getToDate(), Period.ofWeeks(13))
                    .map(date -> new QuarterlyReportResultItemDTO(Year.from(date), QuarterOfYear.from(date)))
                    .collect(Collectors.toList());
        case YEARLY:
            return query.getFromDate().datesUntil(query.getToDate(), Period.ofYears(1))
                    .map(date -> new YearlyReportResultItemDTO(Year.from(date))).collect(Collectors.toList());
        default:
            return new ArrayList<>();
        }
    }

    private void insertItemValueIntoDatapoint(ItemValue itemValue, AbstractDateReportResultItemDTO datapoint) {
        datapoint.addExpense(itemValue.getPurchasePrice());
        datapoint.addIncome(itemValue.getSellingPrice());
    }

    private void insertItemIntoDataPoints(List<AbstractDateReportResultItemDTO> dataPoints, OrderRecord orderRecord) {
        for (int i = dataPoints.size() - 1; i >= 0; i--) {
            AbstractDateReportResultItemDTO datapoint = dataPoints.get(i);
            if (datapoint.getApproximateDate().atStartOfDay().isBefore(orderRecord.getCreatedAt())) {
                insertItemValueIntoDatapoint(orderRecord.getItemValue(), datapoint);
                return;
            }
        }
    }

    public ReportResultsDTO getResults(ReportQueryDTO query) throws BadRequestException {
        List<AbstractDateReportResultItemDTO> dataPoints = generateDataPoints(query);
        ArrayList<ItemReportResultItemDTO> individualItems = new ArrayList<>();
        Long itemId = query.getItemId();

        switch (query.getReportType()) {
            case PROFIT:
                List<Item> allItems = (itemId == null) ? itemService.getAll() : new ArrayList<>() {{
                    add(itemService.findOne(itemId));
                }};

                for (Item item : allItems) {
                    ItemReportResultItemDTO result = getItemSales(item, query.getFromDate(), query.getToDate());
                    if (result.getQuantity() > 0)
                        individualItems.add(result);
                }

                (itemId == null
                        ? orderRecordService.getAllOrderRecordsBetweenDates(query.getFromDate(), query.getToDate())
                        : orderRecordService.getAllOrderRecordsBetweenDatesForItem(itemId, query.getFromDate(), query.getToDate()))
                        .forEach(record -> insertItemIntoDataPoints(dataPoints, record));
                break;

            case PRICE_HISTORY:
                if (itemId == null)
                    throw new BadRequestException("Bad query.");

                Item item = itemService.findOne(itemId);
                individualItems.add(getItemSales(item, query.getFromDate(), query.getToDate()));

                for (AbstractDateReportResultItemDTO datapoint : dataPoints) {
                    ItemValue itemValue = item.getItemValueAt(datapoint.getApproximateDate().atStartOfDay());
                    if (itemValue != null)
                        insertItemValueIntoDatapoint(itemValue, datapoint);
                }
                break;
            default:
                throw new BadRequestException("Bad query.");
        }

        return new ReportResultsDTO(dataPoints, individualItems);
    }
}
