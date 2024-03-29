package com.enigma.futsal_rental.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private String fieldId;
    private String teamName;
    private String customerName;
    private String phone;
    private String startTime;
    private String endTime;
    private Long price;
    private String status;
}
