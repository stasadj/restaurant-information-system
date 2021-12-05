package com.restaurant.backend.constants;

import java.time.LocalDate;

import com.restaurant.backend.dto.reports.ReportQueryDTO;

import static com.restaurant.backend.dto.reports.enums.ReportGranularity.*;
import static com.restaurant.backend.dto.reports.enums.ReportType.*;

public final class ReportServiceTestConstants {
    public static final LocalDate ORDER_DATE_ONE = LocalDate.of(2021, 1, 1);
    public static final LocalDate ORDER_DATE_TWO = LocalDate.of(2021, 3, 14);
    public static final LocalDate ORDER_DATE_THREE = LocalDate.of(2021, 6, 20);
    public static final LocalDate ORDER_DATE_FOUR = LocalDate.of(2021, 11, 15);

    public static final LocalDate DAILY_START_DATE = LocalDate.of(2021, 1, 1);
    public static final LocalDate DAILY_END_DATE = LocalDate.of(2021, 12, 31);

    public static final LocalDate WEEKLY_START_DATE = LocalDate.of(2021, 1, 1);
    public static final LocalDate WEEKLY_END_DATE = LocalDate.of(2021, 2, 1);

    public static final LocalDate MONTHLY_START_DATE = LocalDate.of(2021, 1, 1);
    public static final LocalDate MONTHLY_END_DATE = LocalDate.of(2021, 12, 31);

    public static final LocalDate QUARTERLY_START_DATE = LocalDate.of(2021, 1, 1);
    public static final LocalDate QUARTERLY_END_DATE = LocalDate.of(2021, 12, 31);

    public static final LocalDate YEARLY_START_DATE = LocalDate.of(2021, 1, 1);
    public static final LocalDate YEARLY_END_DATE = LocalDate.of(2022, 12, 31);

    public static final ReportQueryDTO DAILY_PROFIT_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.DAILY_START_DATE,
            ReportServiceTestConstants.DAILY_END_DATE, DAILY.toString(), PROFIT.toString(), null);

    public static final ReportQueryDTO DAILY_PROFIT_QUERY_FOR_ITEM = new ReportQueryDTO(
            ReportServiceTestConstants.DAILY_START_DATE,
            ReportServiceTestConstants.DAILY_END_DATE, DAILY.toString(), PROFIT.toString(), 1L);

    public static final ReportQueryDTO DAILY_PRICE_HISTORY_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.DAILY_START_DATE,
            ReportServiceTestConstants.DAILY_END_DATE, DAILY.toString(), PRICE_HISTORY.toString(), 1L);

    public static final ReportQueryDTO WEEKLY_PROFIT_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.WEEKLY_START_DATE,
            ReportServiceTestConstants.WEEKLY_END_DATE, WEEKLY.toString(), PROFIT.toString(), null);

    public static final ReportQueryDTO WEEKLY_PROFIT_QUERY_FOR_ITEM = new ReportQueryDTO(
            ReportServiceTestConstants.WEEKLY_START_DATE,
            ReportServiceTestConstants.WEEKLY_END_DATE, WEEKLY.toString(), PROFIT.toString(), 1L);

    public static final ReportQueryDTO WEEKLY_PRICE_HISTORY_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.WEEKLY_START_DATE,
            ReportServiceTestConstants.WEEKLY_END_DATE, WEEKLY.toString(), PRICE_HISTORY.toString(), 1L);

    public static final ReportQueryDTO MONTHLY_PROFIT_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.MONTHLY_START_DATE,
            ReportServiceTestConstants.MONTHLY_END_DATE, MONTHLY.toString(), PROFIT.toString(), null);

    public static final ReportQueryDTO MONTHLY_PROFIT_QUERY_FOR_ITEM = new ReportQueryDTO(
            ReportServiceTestConstants.MONTHLY_START_DATE,
            ReportServiceTestConstants.MONTHLY_END_DATE, MONTHLY.toString(), PROFIT.toString(), 1L);

    public static final ReportQueryDTO MONTHLY_PRICE_HISTORY_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.MONTHLY_START_DATE,
            ReportServiceTestConstants.MONTHLY_END_DATE, MONTHLY.toString(), PRICE_HISTORY.toString(), 1L);

    public static final ReportQueryDTO QUARTERLY_PROFIT_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.QUARTERLY_START_DATE,
            ReportServiceTestConstants.QUARTERLY_END_DATE, QUARTERLY.toString(), PROFIT.toString(), null);

    public static final ReportQueryDTO QUARTERLY_PROFIT_QUERY_FOR_ITEM = new ReportQueryDTO(
            ReportServiceTestConstants.QUARTERLY_START_DATE,
            ReportServiceTestConstants.QUARTERLY_END_DATE, QUARTERLY.toString(), PROFIT.toString(), 1L);

    public static final ReportQueryDTO QUARTERLY_PRICE_HISTORY_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.QUARTERLY_START_DATE,
            ReportServiceTestConstants.QUARTERLY_END_DATE, QUARTERLY.toString(), PRICE_HISTORY.toString(), 1L);

    public static final ReportQueryDTO YEARLY_PROFIT_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.YEARLY_START_DATE,
            ReportServiceTestConstants.YEARLY_END_DATE, YEARLY.toString(), PROFIT.toString(), null);

    public static final ReportQueryDTO YEARLY_PROFIT_QUERY_FOR_ITEM = new ReportQueryDTO(
            ReportServiceTestConstants.YEARLY_START_DATE,
            ReportServiceTestConstants.YEARLY_END_DATE, YEARLY.toString(), PROFIT.toString(), 1L);

    public static final ReportQueryDTO YEARLY_PRICE_HISTORY_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.YEARLY_START_DATE,
            ReportServiceTestConstants.YEARLY_END_DATE, YEARLY.toString(), PRICE_HISTORY.toString(), 1L);

    public static final ReportQueryDTO INVALID_REPORT_TYPE_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.YEARLY_START_DATE,
            ReportServiceTestConstants.YEARLY_END_DATE, YEARLY.toString(), "TEST", 1L);

    public static final ReportQueryDTO INVALID_GRANULARITY_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.YEARLY_START_DATE,
            ReportServiceTestConstants.YEARLY_END_DATE, "Annually", PRICE_HISTORY.toString(), 1L);

    public static final ReportQueryDTO MISSING_ITEM_ID_QUERY = new ReportQueryDTO(
            ReportServiceTestConstants.YEARLY_START_DATE,
            ReportServiceTestConstants.YEARLY_END_DATE, YEARLY.toString(), PRICE_HISTORY.toString(), null);
}
