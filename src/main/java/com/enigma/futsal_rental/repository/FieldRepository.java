package com.enigma.futsal_rental.repository;

import com.enigma.futsal_rental.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_field (id, type,price) VALUES (:#{#field.id},:#{#field.type},:#{#field.price})", nativeQuery = true)
    void saveField(Field field);

    @Modifying
    @Transactional
    @Query(value = "UPDATE m_field SET type = :#{#field.type},price = :#{#field.price} WHERE id = :#{#field.id}", nativeQuery = true)
    void updateField(Field field);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM m_field WHERE id = :id", nativeQuery = true)
    void deleteField(String id);

    @Query(value = "SELECT * FROM m_field", nativeQuery = true)
    List<Field> getAll();

    @Query(value = "SELECT * FROM m_field WHERE id = :id", nativeQuery = true)
    Optional<Field> getFieldById(String id);

}
