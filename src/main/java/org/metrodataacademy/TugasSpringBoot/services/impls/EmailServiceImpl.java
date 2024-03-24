package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.EmailRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.EmailRequestJMS;
import org.metrodataacademy.TugasSpringBoot.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;


@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendEmail(EmailRequest emailRequest) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailRequest.getRecipient());
            message.setSubject(emailRequest.getSubject());
            message.setText(emailRequest.getContent());

            javaMailSender.send(message);
            log.info("Message sent successfully to {}", emailRequest.getRecipient());
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void sendEmailWithAttachment(EmailRequest emailRequest) throws MessagingException {
        try {
            log.info("Trying sent an email to {}", emailRequest.getRecipient());
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            mimeMessageHelper.setTo(emailRequest.getRecipient());
            mimeMessageHelper.setSubject(emailRequest.getSubject());

            Context thymeleafContext = new Context();
            thymeleafContext.setVariable("recipient", emailRequest.getRecipient());
            thymeleafContext.setVariable("body", emailRequest.getContent());
            String htmlBody = templateEngine.process("email.html", thymeleafContext);

            mimeMessageHelper.setText(htmlBody, true);

            FileSystemResource fileSystemResource = new FileSystemResource(emailRequest.getAttachment());
            mimeMessageHelper
                    .addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()), fileSystemResource);

            javaMailSender.send(mimeMessage);
            log.info("Message with attachment sent successfully to {}", emailRequest.getRecipient());
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void sendEmailWithHTML(EmailRequestJMS emailRequest) throws MessagingException {
        try {
            log.info("Trying sent an email to {}", emailRequest.getRecipient());
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            mimeMessageHelper.setTo(emailRequest.getRecipient());
            mimeMessageHelper.setSubject(emailRequest.getSubject());

            Context thymeleafContext = new Context();
            thymeleafContext.setVariable("npm", emailRequest.getNpm());
            thymeleafContext.setVariable("nama", emailRequest.getNama());
            thymeleafContext.setVariable("kelas", emailRequest.getKelas());
            thymeleafContext.setVariable("semester", emailRequest.getSemester());
            thymeleafContext.setVariable("prodi", emailRequest.getProdi());
            String htmlBody = templateEngine.process("email.html", thymeleafContext);

            mimeMessageHelper.setText(htmlBody, true);

            javaMailSender.send(mimeMessage);
            log.info("Email sent successfully to {}", emailRequest.getRecipient());
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }
}