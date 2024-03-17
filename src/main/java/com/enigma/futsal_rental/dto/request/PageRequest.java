package com.enigma.futsal_rental.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
    private String time;
}
