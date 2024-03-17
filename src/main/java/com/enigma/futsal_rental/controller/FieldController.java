package com.enigma.futsal_rental.controller;

import com.enigma.futsal_rental.constant.APIUrl;
import com.enigma.futsal_rental.constant.ResponseMessage;
import com.enigma.futsal_rental.dto.request.FieldRequest;
import com.enigma.futsal_rental.dto.request.UpdateFieldRequest;
import com.enigma.futsal_rental.dto.response.CommonResponse;
import com.enigma.futsal_rental.dto.response.FieldResponse;
import com.enigma.futsal_rental.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.FIELD_API)
public class FieldController {
    private final FieldService fieldService;

    @PostMapping
    public ResponseEntity<CommonResponse<FieldResponse>> createField(@RequestBody FieldRequest request) {
        FieldResponse fieldResponse = fieldService.save(request);
        CommonResponse<FieldResponse> response = CommonResponse.<FieldResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(fieldResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<FieldResponse>> updateField(@RequestBody UpdateFieldRequest request) {
        FieldResponse fieldResponse = fieldService.update(request);
        CommonResponse<FieldResponse> response = CommonResponse.<FieldResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(fieldResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<String>> deleteField(@PathVariable String id) {
        fieldService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<FieldResponse>>> getAll() {
        List<FieldResponse> fieldResponseList = fieldService.getAll();
        CommonResponse<List<FieldResponse>> response = CommonResponse.<List<FieldResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(fieldResponseList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<FieldResponse>> getFieldById(@PathVariable String id) {
        FieldResponse fieldResponse = fieldService.getByOneId(id);
        CommonResponse<FieldResponse> response = CommonResponse.<FieldResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(fieldResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
