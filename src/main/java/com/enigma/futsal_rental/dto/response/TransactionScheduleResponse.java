package com.enigma.futsal_rental.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionScheduleResponse {
    private String teamName;
    private String fieldType;
    private String customerName;
    private String startTime;
    private String endTime;
    private String status;
}
