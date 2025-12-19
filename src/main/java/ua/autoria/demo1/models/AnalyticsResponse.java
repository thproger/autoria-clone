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
    private double averageByDay;
    private double averageByMonth;
    private double averageByWeek;
    private double averageInRegion;
}
