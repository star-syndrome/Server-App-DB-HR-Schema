package org.metrodataacademy.TugasSpringBoot.models.dtos.responses;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class ResponseData {

    public static ResponseEntity<Object> statusResponse(Object data, HttpStatus code, String message) {
        Map<String, Object> bodyResponse = new HashMap<>();
        if (data != null) {
            bodyResponse.put("data", data);
        }
        bodyResponse.put("status", code.value());
        bodyResponse.put("message", message);
        return ResponseEntity.status(code).body(bodyResponse);
    }
}