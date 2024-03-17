package com.enigma.futsal_rental.dto.request;

import lombok.*;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTransactionTimeRequest {
    private String idTrx;
    private String startTime;
    private String endTime;
}
