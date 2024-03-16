package com.enigma.futsal_rental.entity;

import com.enigma.futsal_rental.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = ConstantTable.TRANSACTION)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "phone")
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Date start_time;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date end_time;

    @Column(name = "price")
    private Long price;

    @Column(name = "status")
    private String status;
}
