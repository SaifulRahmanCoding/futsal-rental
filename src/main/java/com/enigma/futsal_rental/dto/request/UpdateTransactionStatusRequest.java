package com.enigma.futsal_rental.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTransactionStatusRequest {
    private String idTrx;
    private String status;
}
