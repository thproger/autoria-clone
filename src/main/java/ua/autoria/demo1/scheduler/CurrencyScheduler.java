package ua.autoria.demo1.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CurrencyScheduler {
    @Scheduled(fixedRate = 1000)
    public void checkToken() {}
}
