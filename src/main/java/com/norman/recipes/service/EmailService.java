package com.norman.recipes.service;

import com.norman.recipes.security.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final TemplateEngine templateEngine;

    private final JavaMailSender sender;

    private String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("testEmail", context);
    }


    public void send(String recipient, String message) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(recipient);
            messageHelper.setSubject("test");
            messageHelper.setFrom(SecurityProperties.mail);
            String contenu = build(message);
            messageHelper.setText(contenu, true);
        };
        sender.send(messagePreparator);
    }

    private String buildActivateMail(Long id){
        Context context = new Context();
        context.setVariable("id", id);
        return templateEngine.process("emailConfirm", context);
    }

    public void sendActivationMail(String recipient, Long id) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(recipient);
            messageHelper.setSubject("Activate your account");
            messageHelper.setFrom(SecurityProperties.mail);
            String contenu = buildActivateMail(id);
            messageHelper.setText(contenu, true);
        };
        sender.send(messagePreparator);
    }
}
