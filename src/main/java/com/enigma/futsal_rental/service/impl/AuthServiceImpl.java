package com.enigma.futsal_rental.service.impl;

import com.enigma.futsal_rental.dto.request.AuthRequest;
import com.enigma.futsal_rental.dto.response.LoginResponse;
import com.enigma.futsal_rental.entity.UserAccount;
import com.enigma.futsal_rental.repository.UserAccountRepository;
import com.enigma.futsal_rental.service.AuthService;
import com.enigma.futsal_rental.service.JwtService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${futsal_rental.username.admin}")
    private String adminUsername;
    @Value("${futsal_rental.password.admin}")
    private String adminPassword;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initAdmin() {
        Optional<UserAccount> currentUser = userAccountRepository.findByUsername(adminUsername);
        if (currentUser.isPresent()) return;

        UUID id = UUID.randomUUID();
        UserAccount account = UserAccount.builder()
                .id(id.toString())
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .role("ROLE_ADMIN")
                .isEnable(true)
                .build();
        userAccountRepository.saveAccount(account);
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();
        String token = jwtService.generateToken(userAccount);
        return LoginResponse.builder()
                .username(userAccount.getUsername())
                .role(userAccount.getAuthorities().toString())
                .token(token)
                .build();
    }
}
