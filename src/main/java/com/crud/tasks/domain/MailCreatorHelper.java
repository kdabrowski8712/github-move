package com.crud.tasks.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailCreatorHelper {

    private String tasks_url;
    private String button;
    private String goodbye_message;

    @Value("${admin.name}")
    private String admin_name;

    @Value("${info.company.name}")
    private String company_name;



    public MailCreatorHelper(MailType mailType) {

        tasks_url="http://localhost:8888/tasks_frontend";
        button="Visit webpage";
        goodbye_message="Have a niece day";



        if(mailType.equals("TRELLO")) {

        }

    }
}
