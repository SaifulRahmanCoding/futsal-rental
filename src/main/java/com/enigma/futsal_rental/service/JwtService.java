package com.enigma.futsal_rental.service;

import com.enigma.futsal_rental.dto.response.JwtClaims;
import com.enigma.futsal_rental.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount userAccount);

    boolean verifyJwtToken(String bearerToken);

    JwtClaims getClaimsByToken(String bearerToken);
}
