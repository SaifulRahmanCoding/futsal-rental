package com.enigma.futsal_rental.repository;

import com.enigma.futsal_rental.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    @Query(value = "SELECT * FROM m_user_account", nativeQuery = true)
    Optional<UserAccount> findAccountById(String id);

    @Query(value = "SELECT * FROM m_user_account WHERE username = :username", nativeQuery = true)
    Optional<UserAccount> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_user_account (id, username, password, role, is_enable) VALUES (:#{#userAccount.id},:#{#userAccount.username},:#{#userAccount.password},:#{#userAccount.role},:#{#userAccount.isEnable})", nativeQuery = true)
    void saveAccount(UserAccount userAccount);
}
