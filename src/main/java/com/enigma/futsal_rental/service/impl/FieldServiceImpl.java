package com.enigma.futsal_rental.service.impl;

import com.enigma.futsal_rental.constant.ResponseMessage;
import com.enigma.futsal_rental.dto.request.FieldRequest;
import com.enigma.futsal_rental.dto.request.UpdateFieldRequest;
import com.enigma.futsal_rental.dto.response.FieldResponse;
import com.enigma.futsal_rental.entity.Field;
import com.enigma.futsal_rental.repository.FieldRepository;
import com.enigma.futsal_rental.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FieldResponse save(FieldRequest request) {
        UUID id = UUID.randomUUID();
        fieldRepository.saveField(Field.builder().id(id.toString()).type(request.getType()).price(request.getPrice()).build());
        return FieldResponse.builder()
                .id(id.toString())
                .type(request.getType())
                .price(request.getPrice())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FieldResponse update(UpdateFieldRequest request) {
        fieldRepository.updateField(Field.builder().id(request.getId()).type(request.getType()).price(request.getPrice()).build());
        Field field = findByIdOrThrowNotFound(request.getId());
        return FieldResponse.builder()
                .id(field.getId())
                .type(field.getType())
                .price(field.getPrice())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        fieldRepository.deleteField(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FieldResponse> getAll() {
        List<Field> fields = fieldRepository.getAll();
        return fields.stream().map(field -> {
            return FieldResponse.builder()
                    .id(field.getId())
                    .type(field.getType())
                    .price(field.getPrice())
                    .build();
        }).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public FieldResponse getByOneId(String id) {
        Field field = findByIdOrThrowNotFound(id);
        return FieldResponse.builder()
                .id(field.getId())
                .type(field.getType())
                .price(field.getPrice())
                .build();
    }

    @Override
    public Field getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    private Field findByIdOrThrowNotFound(String id) {
        return fieldRepository.getFieldById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }
}
