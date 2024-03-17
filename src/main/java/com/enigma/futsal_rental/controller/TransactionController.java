package com.enigma.futsal_rental.controller;

import com.enigma.futsal_rental.constant.APIUrl;
import com.enigma.futsal_rental.constant.ResponseMessage;
import com.enigma.futsal_rental.dto.request.PageRequest;
import com.enigma.futsal_rental.dto.request.TransactionRequest;
import com.enigma.futsal_rental.dto.request.UpdateTransactionStatusRequest;
import com.enigma.futsal_rental.dto.request.UpdateTransactionTimeRequest;
import com.enigma.futsal_rental.dto.response.CommonResponse;
import com.enigma.futsal_rental.dto.response.PagingResponse;
import com.enigma.futsal_rental.dto.response.TransactionResponse;
import com.enigma.futsal_rental.dto.response.TransactionScheduleResponse;
import com.enigma.futsal_rental.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.TRANSACTION_API)
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(@RequestBody TransactionRequest request) {
        TransactionResponse transactionResponse = transactionService.save(request);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(transactionResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "start_time") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {
        PageRequest request = PageRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<TransactionResponse> transactions = transactionService.getAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(transactions.getTotalPages())
                .totalElement(transactions.getTotalElements())
                .page(transactions.getPageable().getPageNumber() + 1)
                .size(transactions.getPageable().getPageSize())
                .hasNext(transactions.hasNext())
                .hasPrevious(transactions.hasPrevious())
                .build();
        CommonResponse<List<TransactionResponse>> response = CommonResponse.<List<TransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(transactions.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<TransactionResponse>> getById(@PathVariable String id) {
        TransactionResponse transactionResponse = transactionService.getOneById(id);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(transactionResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/schedules")
    public ResponseEntity<CommonResponse<List<TransactionScheduleResponse>>> getSchedule(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "start_time") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {
        PageRequest request = PageRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<TransactionScheduleResponse> schedule = transactionService.getSchedule(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(schedule.getTotalPages())
                .totalElement(schedule.getTotalElements())
                .page(schedule.getPageable().getPageNumber() + 1)
                .size(schedule.getPageable().getPageSize())
                .hasNext(schedule.hasNext())
                .hasPrevious(schedule.hasPrevious())
                .build();
        CommonResponse<List<TransactionScheduleResponse>> response = CommonResponse.<List<TransactionScheduleResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(schedule.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/time")
    public ResponseEntity<CommonResponse<String>> updateTime(@RequestBody UpdateTransactionTimeRequest request) {
        transactionService.updateTime(request);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/status")
    public ResponseEntity<CommonResponse<String>> updateStatus(@RequestBody UpdateTransactionStatusRequest request) {
        transactionService.updateStatus(request);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }
}
