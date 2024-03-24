package org.metrodataacademy.TugasSpringBoot.controllers;

import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.EmailRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.EmailRequestJMS;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface EmailController {

    ResponseEntity<Object> sendEmail(EmailRequest emailRequest);

    ResponseEntity<Object> sendEmailWithAttachment(EmailRequest emailRequest) throws MessagingException;

    ResponseEntity<Object> sendEmailWithHTML(EmailRequestJMS emailRequest) throws MessagingException;
}