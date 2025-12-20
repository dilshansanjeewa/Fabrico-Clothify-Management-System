package service;

public interface DashboardService {
    int checkLowStockItems();
    String getTodayOrderCount();
    String getTodayRevenue();
    String getThisMonthRevenue();
}
