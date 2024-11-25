package com.restful.templateRestful.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MailService {

    JavaMailSender mailSender;

    @NonFinal
    @Value("${spring.mail.from}")
    String mailFrom;

    public String sendEmail(String recipients, String subject, String content, MultipartFile[] files) throws MessagingException, UnsupportedEncodingException {
        log.info("Sending ...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
        helper.setFrom(mailFrom, "Hieu Tran"); // gan ten

        if(recipients.contains(",")){
            helper.setTo(InternetAddress.parse(recipients));
        }else {
            helper.setTo(recipients);
        }

        if(files != null) {
            for (MultipartFile file : files) {
                helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            }
        }

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);

        log.info("Email has bean send successfully, {}", recipients);
        return "Sent";
    }
}
