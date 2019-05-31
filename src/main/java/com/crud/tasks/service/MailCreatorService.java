package com.crud.tasks.service;

import com.crud.tasks.domain.MailCreatorHelper;
import com.crud.tasks.trello.config.AdminConfig;
import com.crud.tasks.trello.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private MailCreatorHelper mailCreatorHelper;

    private Context prepareBasicMailcontext(String message) {
        Context context = new Context();
        context.setVariable("message",message);
        context.setVariable("tasks_url","http://localhost:8888/tasks_frontend");
        context.setVariable("button","Visit website");
        context.setVariable("admin_config",adminConfig);
        context.setVariable("admin_name",adminConfig.getAdminName());
        context.setVariable("company_name",companyConfig.getCompanyName());
        context.setVariable("preview_message","New Trello Card Created");
        context.setVariable("goodbye_message","Have a niece day");

        return context;
    }


    public String buildTrelloCardMail(String message) {

        List<String> functionalities = new ArrayList<>();
        functionalities.add("You can manage your tasks");
        functionalities.add("Provides connection with Trello Account");
        functionalities.add("Application allows sending tasks to Trello");


        Context context = prepareBasicMailcontext(message);
        context.setVariable("preview_message","New Trello Card Created");
        context.setVariable("show_button",true);
        context.setVariable("is_friend",false);
        context.setVariable("application_functionality",functionalities);
        context.setVariable("is_official",true);

        return templateEngine.process("mail/created-trello-card-mail",context);

    }

    public String buildQuantityTaskMail(String message) {

        Context context = prepareBasicMailcontext(message);
        context.setVariable("preview_message","Daily quantity of tasks");
        context.setVariable("show_button",false);
        context.setVariable("is_friend",true);
        context.setVariable("is_official",false);

        return templateEngine.process("mail/task-quantity-mail",context);
    }
}
