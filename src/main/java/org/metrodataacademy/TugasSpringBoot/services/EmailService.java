package org.metrodataacademy.TugasSpringBoot.services;

import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.EmailRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.EmailRequestJMS;

import javax.mail.MessagingException;

public interface EmailService {

    void sendEmail(EmailRequest emailRequest);

    void sendEmailWithAttachment(EmailRequest emailRequest) throws MessagingException;

    void sendEmailWithHTML(EmailRequestJMS emailRequest) throws MessagingException;
}