package com.restaurant.backend.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.Year;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.dto.enums.QuarterOfYear;
import com.restaurant.backend.dto.reports.AbstractDateReportResultItemDTO;
import com.restaurant.backend.dto.reports.DailyReportResultItemDTO;
import com.restaurant.backend.dto.reports.ItemReportResultItemDTO;
import com.restaurant.backend.dto.reports.MonthlyReportResultItemDTO;
import com.restaurant.backend.dto.reports.QuarterlyReportResultItemDTO;
import com.restaurant.backend.dto.reports.ReportQueryDTO;
import com.restaurant.backend.dto.reports.ReportResultsDTO;
import com.restaurant.backend.dto.reports.WeeklyReportResultItemDTO;
import com.restaurant.backend.dto.reports.YearlyReportResultItemDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.support.DateHelper;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {
    private ItemService itemService;
    private OrderItemService orderItemService;

    private ItemReportResultItemDTO getItemSales(Item item, LocalDate fromDate, LocalDate toDate) {
        ItemReportResultItemDTO result = new ItemReportResultItemDTO(item.getName());

        List<OrderItem> orders = orderItemService.getAllByItemId(item.getId());
        for (OrderItem orderItem : orders) {

            LocalDateTime dateTime = orderItem.getOrder().getCreatedAt();
            if (DateHelper.isDateBetween(dateTime, fromDate, toDate)) {
                ItemValue value = item.getItemValueAt(dateTime);
                if (value == null) {
                    continue;
                }
                Integer amount = orderItem.getAmount();
                BigDecimal quantity = new BigDecimal(amount);
                BigDecimal expense = value.getPurchasePrice().multiply(quantity);
                BigDecimal income = value.getSellingPrice().multiply(quantity);

                result.addQuantity(amount);
                result.addExpense(expense);
                result.addIncome(income);
            }
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

    private void insertItemIntoDataPoints(List<AbstractDateReportResultItemDTO> dataPoints, OrderItem item) {
        LocalDateTime createdAt = item.getOrder().getCreatedAt();
        ItemValue itemValue = item.getItem().getItemValueAt(createdAt);
        if (itemValue == null) {
            return;
        }

        for (int i = dataPoints.size() - 1; i >= 0; i--) {
            AbstractDateReportResultItemDTO datapoint = dataPoints.get(i);

            if (datapoint.getApproximateDate().atStartOfDay().isBefore(createdAt)) {
                insertItemValueIntoDatapoint(itemValue, datapoint);
                return;
            }
        }
    }

    public ReportResultsDTO getResults(ReportQueryDTO query) {
        List<AbstractDateReportResultItemDTO> dataPoints = generateDataPoints(query);
        ArrayList<ItemReportResultItemDTO> individualItems = new ArrayList<>();
        Long itemId = query.getItemId();

        switch (query.getReportType()) {
        case PROFIT:
            List<Item> allItems;
            if (itemId == null) {
                allItems = itemService.getAll();
            } else {
                allItems = new ArrayList<>();
                allItems.add(itemService.getById(itemId));
            }

            for (Item item : allItems) {
                ItemReportResultItemDTO result = getItemSales(item, query.getFromDate(), query.getToDate());
                if (result.getQuantity() > 0) {
                    individualItems.add(result);
                }
            }

            (itemId == null ? orderItemService.getAll() : orderItemService.getAllByItemId(itemId)).stream()
                    .filter(orderItem -> DateHelper.isDateBetween(orderItem.getOrder().getCreatedAt(),
                            query.getFromDate(), query.getToDate()))
                    .forEach(item -> insertItemIntoDataPoints(dataPoints, item));
            break;
        case PRICE_HISTORY:
            if (itemId == null) {
                throw new BadRequestException("Bad query.");
            }

            Item item = itemService.getById(itemId);
            individualItems.add(getItemSales(item, query.getFromDate(), query.getToDate()));

            for (AbstractDateReportResultItemDTO datapoint : dataPoints) {
                ItemValue itemValue = item.getItemValueAt(datapoint.getApproximateDate().atStartOfDay());
                if (itemValue != null) {
                    insertItemValueIntoDatapoint(itemValue, datapoint);
                }
            }
            break;
        default:
            throw new BadRequestException("Bad query.");
        }

        return new ReportResultsDTO(dataPoints, individualItems);
    }
}
