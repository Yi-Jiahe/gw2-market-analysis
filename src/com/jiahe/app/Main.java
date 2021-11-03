package com.jiahe.app;

import com.jiahe.scraper.Scraper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        switch (args[0]) {
            case "scrape":
                Scraper scraper = new Scraper();
                scraper.run();
            case "webserver":

        }
    }

    private static void initializeScraperService() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        ZonedDateTime currentTime = Instant.now().atZone(ZoneId.of("UTC"));
        ZonedDateTime executionTime = ZonedDateTime.of(
                currentTime.getYear(),
                currentTime.getMonth().getValue(),
                currentTime.getDayOfMonth(),
                0, 0, 0, 0, ZoneId.of("UTC")
        );

        long initialDelay;
        if(currentTime.isAfter(executionTime)){
            // take the next day, if we passed the execution date
            initialDelay = currentTime.until(executionTime.plusDays(1), ChronoUnit.MILLIS);
        } else {
            initialDelay = currentTime.until(executionTime, ChronoUnit.MILLIS);
        }

        long delay = TimeUnit.HOURS.toMillis(6);
        initialDelay = initialDelay % delay;

        Scraper scraper = new Scraper();

        ScheduledFuture<?> x = scheduler.scheduleWithFixedDelay(scraper, initialDelay, delay , TimeUnit.MILLISECONDS);
    }
}
