package group6.ecommerce.Scheduler;

import group6.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderScheduler {
    private final OrderService orderService;
    @Scheduled(fixedRate = 1  * 60 * 1000) // Chạy mỗi 5 phút
    public void checkAndCancelUnpaidOrders() {
        orderService.cancelUnpaidOrders();
    }
}
