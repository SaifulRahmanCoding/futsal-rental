package com.enigma.futsal_rental.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldRequest {
    private String type;
    private Long price;
}
