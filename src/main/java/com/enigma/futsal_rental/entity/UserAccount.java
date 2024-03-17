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
@Table(name = ConstantTable.USER_ACCOUNT)
public class UserAccount {
    @Id
    private String id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role",nullable = false)
    private String role;
}
