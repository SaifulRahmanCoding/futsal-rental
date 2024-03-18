package com.enigma.futsal_rental.service;

import com.enigma.futsal_rental.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserAccount getByUserId(String id);
}
