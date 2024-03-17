package com.enigma.futsal_rental.service.impl;

import com.enigma.futsal_rental.constant.ResponseMessage;
import com.enigma.futsal_rental.dto.request.*;
import com.enigma.futsal_rental.dto.response.TransactionResponse;
import com.enigma.futsal_rental.dto.response.TransactionScheduleResponse;
import com.enigma.futsal_rental.entity.Field;
import com.enigma.futsal_rental.entity.Transaction;
import com.enigma.futsal_rental.repository.TransactionRepository;
import com.enigma.futsal_rental.service.FieldService;
import com.enigma.futsal_rental.service.TransactionService;
import com.enigma.futsal_rental.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final FieldService fieldService;

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionResponse> getAll(PageRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());

        Pageable pageable = org.springframework.data.domain.PageRequest.of((request.getPage() - 1), request.getSize(), sort);
        Page<Transaction> transactionPage = transactionRepository.getAll(pageable);
        List<TransactionResponse> transactionResponses = transactionPage.getContent().stream().map(
                trx -> {
                    return TransactionResponse.builder()
                            .id(trx.getIdTrx())
                            .fieldId(trx.getField().getId())
                            .teamName(trx.getTeamName())
                            .customerName(trx.getCustomerName())
                            .phone(trx.getPhone())
                            .startTime(trx.getStartTime().toString())
                            .endTime(trx.getEndTime().toString())
                            .price(trx.getPrice())
                            .status(trx.getStatus())
                            .build();
                }).toList();
        return new PageImpl<>(transactionResponses, pageable, transactionPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionScheduleResponse> getSchedule(PageRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());

        Pageable pageable = org.springframework.data.domain.PageRequest.of((request.getPage() - 1), request.getSize(), sort);
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        String[] date = timestamp.split(" ");
        Page<Transaction> schedule = transactionRepository.getSchedule(DateUtil.parseDate(date[0], "yyyy-MM-dd"), pageable);
        List<TransactionScheduleResponse> transactionScheduleResponses = schedule.getContent().stream().map(
                s -> {
                    return TransactionScheduleResponse.builder()
                            .teamName(s.getTeamName())
                            .fieldType(s.getField().getType())
                            .customerName(s.getCustomerName())
                            .startTime(s.getStartTime().toString())
                            .endTime(s.getEndTime().toString())
                            .status(s.getStatus())
                            .build();
                }).toList();
        return new PageImpl<>(transactionScheduleResponses, pageable, schedule.getTotalElements());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse save(TransactionRequest request) {
        UUID id = UUID.randomUUID();
        String pattern = "yyyy-MM-dd hh:mm:ss";
        Date startTime = DateUtil.parseDate(request.getStartTime(), pattern);
        Date endTime = new Timestamp(DateUtil.parseDate(request.getStartTime(), pattern).getTime() + (3600000L * request.getLongTime()));
        // validasi waktu main
        validationReserveTime(startTime, endTime);

        Field field = fieldService.getById(request.getFieldId());

        Long price = field.getPrice() * request.getLongTime();

        transactionRepository.saveTrx(SaveTransactionRequest.builder()
                .idTrx(id.toString())
                .customerName(request.getCustomerName())
                .teamName(request.getTeamName())
                .phone(request.getPhone())
                .fieldId(request.getFieldId())
                .startTime(startTime)
                .endTime(endTime)
                .price(price)
                .status("reserved")
                .build());

        Transaction trx = findByIdOrThrowNotFound(id.toString());
        return TransactionResponse.builder()
                .id(trx.getIdTrx())
                .fieldId(trx.getField().getId())
                .teamName(trx.getTeamName())
                .customerName(trx.getCustomerName())
                .phone(trx.getPhone())
                .startTime(trx.getStartTime().toString())
                .endTime(trx.getEndTime().toString())
                .price(trx.getPrice())
                .status(trx.getStatus())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTime(UpdateTransactionTimeRequest request) {
        String pattern = "yyyy-MM-dd hh:mm:ss";
        Transaction transaction = getById(request.getIdTrx());
        int playTime = (int) ((transaction.getEndTime().getTime() - transaction.getStartTime().getTime()) / 3600000);
        log.info(Integer.toString(playTime));

        Date startTime = DateUtil.parseDate(request.getStartTime(), pattern);
        Date endTime = new Timestamp(DateUtil.parseDate(request.getStartTime(), pattern).getTime() + (3600000L * playTime));

        validationReserveTime(startTime, endTime);
        transactionRepository.updateTime(request.getIdTrx(), startTime, endTime);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(UpdateTransactionStatusRequest request) {
        getById(request.getIdTrx());
        transactionRepository.updateStatus(request.getIdTrx(), request.getStatus());
    }

    @Transactional(readOnly = true)
    @Override
    public TransactionResponse getOneById(String id) {
        Transaction trx = getById(id);
        return TransactionResponse.builder()
                .id(trx.getIdTrx())
                .fieldId(trx.getField().getId())
                .teamName(trx.getTeamName())
                .customerName(trx.getCustomerName())
                .phone(trx.getPhone())
                .startTime(trx.getStartTime().toString())
                .endTime(trx.getEndTime().toString())
                .price(trx.getPrice())
                .status(trx.getStatus())
                .build();
    }

    @Override
    public Transaction getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    private Transaction findByIdOrThrowNotFound(String id) {
        return transactionRepository.getTrxById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    private void validationReserveTime(Date startTime, Date endTime) {
        transactionRepository.getAllReserved().forEach(
                trx -> {
                    if (startTime.getTime() >= trx.getStartTime().getTime() && startTime.getTime() <= trx.getEndTime().getTime()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "reserved time not available");
                    }
                    if (endTime.getTime() >= trx.getStartTime().getTime() && endTime.getTime() <= trx.getEndTime().getTime()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "reserved time not available");
                    }
                });
    }
}
