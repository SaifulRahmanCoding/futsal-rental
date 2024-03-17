package com.enigma.futsal_rental.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private String fieldId;
    private String teamName;
    private String customerName;
    private String phone;
    private String startTime;
    private Integer longTime;
}