package com.crud.tasks.domain;

import org.springframework.stereotype.Component;

@Component
public class MailType {
    public enum Types {
        TRELLO, DAILYQUANTITY;
    }
}
