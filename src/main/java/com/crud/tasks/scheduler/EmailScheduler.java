package com.crud.tasks.scheduler;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.MailType;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    private static final String SUBJECT = "Tasks: Once a day email";

    @Scheduled(cron = "0 0 10 * * *")
    //@Scheduled(fixedDelay = 10000)
    public void sendInformationEmail() {

        long size = taskRepository.count();
        simpleEmailService.send(new Mail(SUBJECT,
                messageBuilder(size),
                adminConfig.getAdminMail(),""), MailType.Types.DAILYQUANTITY);



    }


    private String messageBuilder(long count) {
        String result = "Currently in database you got: " + count + " task";

        if(count>1){
            result+="s";
        }

        return result;
    }


}
