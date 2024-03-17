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
    @Column(name = "id_trx")
    private String idTrx;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "phone")
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "total_price")
    private Long price;

    @Column(name = "status")
    private String status;
}
