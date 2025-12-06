package service.impl;

import service.DashboardService;
import service.ItemService;
import service.OrderService;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DashboardServiceImpl implements DashboardService {

    private ItemService itemService = new ItemServiceImpl();
    private OrderService orderService = new OrderServiceImpl();

    private NumberFormat formatter = new DecimalFormat("#,###.00");

    @Override
    public int checkLowStockItems() {
        return itemService.findLowStockedItems();
    }

    @Override
    public String getTodayOrderCount() {

        return String.valueOf(orderService.getTodayOrderCount());
    }

    @Override
    public String getTodayRevenue() {
        return "Rs." + formatter.format(orderService.getTodayRevenue());
    }

    @Override
    public String getThisMonthRevenue() {
        return "Rs." + formatter.format(orderService.getThisMonthRevenue());
    }
}
