package com.restful.templateRestful.controller;


import com.restful.templateRestful.dto.response.ResponseData;
import com.restful.templateRestful.dto.response.ResponseError;
import com.restful.templateRestful.dto.response.ResponseSuccess;
import com.restful.templateRestful.service.MailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;

@Slf4j
@RestController
@RequestMapping("/common")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommonController {

    MailService mailService;

    @PostMapping("/send-email")
    public ResponseData<String> sendEmail(
            @RequestParam String recipients,
            @RequestParam String subject,
            @RequestParam String content,
            @RequestParam(required = false) MultipartFile[] file) {
        try {
            return new ResponseData<>(HttpStatus.ACCEPTED.value(),
                    mailService.sendEmail(recipients, subject, content, file));
        }
        catch (Exception e) {
            log.error("Sending email was failure, error: {}", e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "");
        }

    }


}
