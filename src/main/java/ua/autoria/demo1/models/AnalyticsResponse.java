package ua.autoria.demo1.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResponse {
    private int views;
    private double average;
    private int currentMonth;
    private int currentDay;
    private int currentWeek;
    private double averageInRegion;
}
