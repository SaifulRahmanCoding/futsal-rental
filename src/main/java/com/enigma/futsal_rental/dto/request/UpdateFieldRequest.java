package com.enigma.futsal_rental.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateFieldRequest {
    private String id;
    private String type;
    private Long price;
}
