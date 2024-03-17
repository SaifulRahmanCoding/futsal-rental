package com.enigma.futsal_rental.scheduler;

import com.enigma.futsal_rental.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionStatusScheduler {
    private final TransactionService transactionService;

    @Scheduled(fixedRate = 60000 * 60)
    public void checkEndTimeReserve() {
        log.info("Start checkEndTimeReserve() : {}", new Date(System.currentTimeMillis()));
        transactionService.updateStatusReserve();
        log.info("End checkEndTimeReserve() : {}", new Date(System.currentTimeMillis()));
    }
}
