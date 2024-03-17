package com.enigma.futsal_rental.dto.request;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveTransactionRequest {
    private String idTrx;
    private String fieldId;
    private String teamName;
    private String customerName;
    private String phone;
    private Date startTime;
    private Date endTime;
    private Long price;
    private String status;
}
