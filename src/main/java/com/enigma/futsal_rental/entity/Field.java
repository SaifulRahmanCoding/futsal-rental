package com.enigma.futsal_rental.entity;

import com.enigma.futsal_rental.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = ConstantTable.FIELD)
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "type",nullable = false)
    private String type;

    @Column(name = "price",nullable = false)
    private Long price;
}