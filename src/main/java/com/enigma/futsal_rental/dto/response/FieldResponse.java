package com.enigma.futsal_rental.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldResponse {
    private String id;
    private String type;
    private Long price;
}
