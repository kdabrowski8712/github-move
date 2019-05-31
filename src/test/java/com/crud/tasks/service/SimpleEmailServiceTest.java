package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.MailType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;


import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @Test
    public void shouldSentMail() throws Exception {
        //Given
        Mail mail = new Mail("Test","Test Message","test@test.com","testcc@test.com");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail.getMailTo());
        simpleMailMessage.setText(mail.getMessage());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setCc(mail.getToCC());


        //When
        simpleEmailService.send(mail, MailType.Types.TRELLO);


        //Then
      //  assertEquals(mail.getMailTo(), mimeMessage.getRecipients(MimeMessage.RecipientType.TO)[0].toString());
      // verify(javaMailSender, times(1)).send();

    }


}