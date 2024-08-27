package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.EmailController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.EmailRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.EmailRequestJMS;
import org.metrodataacademy.TugasSpringBoot.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/email")
public class EmailControllerImpl implements EmailController {

    @Autowired
    private EmailService emailService;

    @Override
    @PostMapping(
            path = "/send",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> sendEmail(@Validated @RequestBody EmailRequest emailRequest) {
        emailService.sendEmail(emailRequest);
        return ResponseEntity.ok().body(null);
    }

    @Override
    @PostMapping(
            path = "/send-with-attachment",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> sendEmailWithAttachment(@Validated @RequestBody EmailRequest emailRequest) throws MessagingException {
        emailService.sendEmailWithAttachment(emailRequest);
        return ResponseEntity.ok().body(null);
    }

    @Override
    @PostMapping(
            path = "/send-with-HTML",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> sendEmailWithHTML(@Validated @RequestBody EmailRequestJMS emailRequest2) throws MessagingException {
        emailService.sendEmailWithHTML(emailRequest2);
        return ResponseEntity.ok().body(null);
    }
}