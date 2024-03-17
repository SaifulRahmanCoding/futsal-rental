package com.enigma.futsal_rental.service;

import com.enigma.futsal_rental.dto.request.FieldRequest;
import com.enigma.futsal_rental.dto.request.UpdateFieldRequest;
import com.enigma.futsal_rental.dto.response.FieldResponse;
import com.enigma.futsal_rental.entity.Field;

import java.util.List;

public interface FieldService {
    FieldResponse save(FieldRequest request);
    FieldResponse update(UpdateFieldRequest request);
    void delete(String id);
    List<FieldResponse> getAll();
    FieldResponse getByOneId(String id);
    Field getById(String id);
}
