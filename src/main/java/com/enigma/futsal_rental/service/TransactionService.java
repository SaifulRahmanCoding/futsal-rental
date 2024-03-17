package com.enigma.futsal_rental.service;

import com.enigma.futsal_rental.dto.request.PageRequest;
import com.enigma.futsal_rental.dto.request.TransactionRequest;
import com.enigma.futsal_rental.dto.request.UpdateTransactionStatusRequest;
import com.enigma.futsal_rental.dto.request.UpdateTransactionTimeRequest;
import com.enigma.futsal_rental.dto.response.TransactionResponse;
import com.enigma.futsal_rental.dto.response.TransactionScheduleResponse;
import com.enigma.futsal_rental.entity.Transaction;
import org.springframework.data.domain.Page;

public interface TransactionService {
    Page<TransactionResponse> getAll(PageRequest request);
    Page<TransactionScheduleResponse> getSchedule(PageRequest request);
    TransactionResponse save(TransactionRequest request);
    void updateTime(UpdateTransactionTimeRequest request);
    void updateStatus(UpdateTransactionStatusRequest request);

    TransactionResponse getOneById(String id);
    Transaction getById(String id);
}
