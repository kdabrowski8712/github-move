package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class    SimpleEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(mailCreatorService.buildTrelloCardMail(mail.getMessage()),true);

        };
    }

    public void send(final Mail mail) {
        LOGGER.info("Starting mail preapration...");
        try {
            //SimpleMailMessage simpleMailMessage = createMailMessagr(mail);
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("Email has been sent");
        }
        catch(MailException e) {
            LOGGER.error("Failed to send mail",e.getMessage(),e);
        }
    }

    private SimpleMailMessage createMailMessagr(final Mail mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail.getMailTo());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getMessage());

        if( !(mail.getToCC().isEmpty() || mail.getToCC()==null))
        {
            simpleMailMessage.setCc(mail.getToCC());
        }

        return simpleMailMessage;
    }
}
