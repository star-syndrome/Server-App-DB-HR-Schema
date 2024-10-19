package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateHistoryRequest;
import org.metrodataacademy.TugasSpringBoot.services.impls.HistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@PreAuthorize(value = "hasRole('ADMIN')")
public class HistoryController {

    @Autowired
    private HistoryServiceImpl historyService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(historyService.getAll());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Object> create(@Validated @RequestBody CreateHistoryRequest request) {
        return ResponseEntity.ok().body(historyService.create(request));
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(historyService.delete(id));
    }
}