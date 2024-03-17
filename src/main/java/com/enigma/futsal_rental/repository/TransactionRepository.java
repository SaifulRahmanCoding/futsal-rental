package com.enigma.futsal_rental.repository;

import com.enigma.futsal_rental.dto.request.SaveTransactionRequest;
import com.enigma.futsal_rental.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO t_transaction (id_trx,customer_name,phone,team_name,start_time,end_time,total_price,status,field_id) VALUES (" +
            ":#{#trx.idTrx}," +
            ":#{#trx.customerName}," +
            ":#{#trx.phone}," +
            ":#{#trx.teamName}," +
            ":#{#trx.startTime}," +
            ":#{#trx.endTime}," +
            ":#{#trx.price}," +
            ":#{#trx.status}," +
            ":#{#trx.fieldId})", nativeQuery = true)
    void saveTrx(SaveTransactionRequest trx);

    @Modifying
    @Transactional
    @Query(value = "UPDATE t_transaction SET status = :status WHERE id_trx = :id", nativeQuery = true)
    void updateStatus(String id, String status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE t_transaction SET start_time = :startTime,end_time = :endTime WHERE id_trx = :id", nativeQuery = true)
    void updateTime(String id, Date startTime, Date endTime);

    @Query(value = "SELECT * FROM t_transaction t JOIN m_field f ON f.id = t.field_id", countQuery = "SELECT COUNT(*) FROM t_transaction", nativeQuery = true)
    Page<Transaction> getAll(Pageable pageable);

    @Query(value = "SELECT * FROM t_transaction WHERE status = 'reserved'", nativeQuery = true)
    List<Transaction> getAllReserved();

    @Query(value = "SELECT * FROM t_transaction WHERE status = 'reserved' AND end_time < :now", nativeQuery = true)
    List<Transaction> getAllReservedStatus(Date now);

    @Query(value = "SELECT * FROM t_transaction WHERE id_trx = :id", nativeQuery = true)
    Optional<Transaction> getTrxById(String id);

    @Query(value = "SELECT * FROM t_transaction t JOIN m_field f ON f.id = t.field_id WHERE start_time > :time", nativeQuery = true)
    Page<Transaction> getSchedule(Date time, Pageable pageable);
}
