package com.jiahe.app;

import com.jiahe.scraper.Scraper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // TODO: Use UTC +00
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime executionDate = LocalDateTime.of(currentTime.getYear(),
                currentTime.getMonth(),
                currentTime.getDayOfMonth(),
                12, 0);

        long initialDelay;
        if(currentTime.isAfter(executionDate)){
            // take the next day, if we passed the execution date
            initialDelay = currentTime.until(executionDate.plusDays(1), ChronoUnit.MILLIS);
        } else {
            initialDelay = currentTime.until(executionDate, ChronoUnit.MILLIS);
        }

        long delay = TimeUnit.HOURS.toMillis(6);
        initialDelay = initialDelay % delay;

        Scraper scraper = new Scraper();

        ScheduledFuture<?> x = scheduler.scheduleWithFixedDelay(scraper, initialDelay, delay , TimeUnit.MILLISECONDS);
    }
}
