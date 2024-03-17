package com.enigma.futsal_rental.service;

import com.enigma.futsal_rental.dto.request.AuthRequest;
import com.enigma.futsal_rental.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(AuthRequest request);
}
