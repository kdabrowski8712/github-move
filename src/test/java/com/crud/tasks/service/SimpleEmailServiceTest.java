package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSentMail() {
        //Given
        Mail mail = new Mail("Test","Test Message","test@test.com","testcc@test.com");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail.getMailTo());
        simpleMailMessage.setText(mail.getMessage());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setCc(mail.getToCC());

        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(simpleMailMessage);


    }


}